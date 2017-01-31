package asset.pipeline.babel

import asset.pipeline.AbstractProcessor
import asset.pipeline.AssetCompiler
import asset.pipeline.AssetFile

import javax.script.ScriptContext
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.SimpleBindings

/**
 * @author Liang Yong Rui
 */
class BabelProcessor extends AbstractProcessor {

    BabelProcessor(AssetCompiler precompiler) {
        super(precompiler)
    }

    @Override
    public String process(String inputText, AssetFile assetFile) {

        ScriptEngineManager engineManager = new ScriptEngineManager()
        ScriptEngine engine = engineManager.getEngineByName("nashorn")

        SimpleBindings bindings = new SimpleBindings();
        bindings.put('code', inputText)
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE)

        ClassLoader classLoader = this.class.classLoader
        InputStream babelJsInputStream = classLoader.getResourceAsStream('asset/pipeline/babel/babel-standalone.js')
        try {
            Reader babelJsReader = new InputStreamReader(babelJsInputStream)
            engine.eval(babelJsReader)
        }
        finally {
            babelJsInputStream.close()
        }

        return engine.eval('Babel.transform(code, {minified: true,presets: ["es2015", "react"]}).code')
    }
}
