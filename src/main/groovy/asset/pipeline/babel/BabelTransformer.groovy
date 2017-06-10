package asset.pipeline.babel

import asset.pipeline.AssetFile

/**
 * @author Liang Yong Rui
 */
interface BabelTransformer {

    String transform(String inputText, AssetFile file)

}