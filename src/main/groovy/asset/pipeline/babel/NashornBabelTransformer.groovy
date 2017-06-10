package asset.pipeline.babel

import asset.pipeline.AssetFile
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.script.ScriptContext
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.SimpleBindings

/**
 * 使用 Java8 Nashorn脚本引擎
 *
 * @author Liang Yong Rui
 */
class NashornBabelTransformer implements BabelTransformer {

    private static Logger log = LoggerFactory.getLogger(BabelProcessor.class)

    private static ScriptEngine engine;

    NashornBabelTransformer() {
        if (!engine) {
            engine = initEngine()
        }
    }

    private ScriptEngine initEngine() {

        log.info('init nashorn script engine')

        ScriptEngineManager engineManager = new ScriptEngineManager()
        ScriptEngine engine = engineManager.getEngineByName('nashorn')

        ClassLoader classLoader = this.class.classLoader
        InputStream babelJsInputStream = classLoader.getResourceAsStream('asset/pipeline/babel/babel-standalone.js')
        try {
            Reader babelJsReader = new InputStreamReader(babelJsInputStream)
            engine.eval(babelJsReader)
        }
        finally {
            babelJsInputStream.close()
        }

        return engine
    }

    @Override
    String transform(String inputText, AssetFile file) {

        SimpleBindings bindings = engine.createBindings()
        bindings.put('code', inputText)
        engine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE)

        return engine.eval('Babel.transform(code, {minified: true,presets: ["es2015", "react"]}).code')
    }

}
