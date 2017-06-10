package asset.pipeline.babel

import asset.pipeline.AbstractProcessor
import asset.pipeline.AssetCompiler
import asset.pipeline.AssetFile
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Liang Yong Rui
 */
class BabelProcessor extends AbstractProcessor {

    private static Logger log = LoggerFactory.getLogger(BabelProcessor.class)

    private static BabelTransformer transformer = new NashornBabelTransformer();

    BabelProcessor(AssetCompiler precompiler) {
        super(precompiler)
    }

    @Override
    public String process(String inputText, AssetFile assetFile) {
        transformer.transform(inputText, assetFile)
    }
}
