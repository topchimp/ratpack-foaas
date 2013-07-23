package app
/**
 * User: danielwoods
 * Date: 7/23/13
 */
enum PropertiesHolder {
    _

    Properties props
    def engine

    String _buildString(String key, params) {
        engine.createTemplate(props[key]).make(params)
    }

    def getRenderedTemplate(pathTokens) {
        def params = _fixPathTokenMap(pathTokens)
        def (message, subtitle) = ['message', 'subtitle'].collect {
            _buildString(
                    "fuckOff.${pathTokens['type']}.$it",
                    params
            )
        }
        [message: message, subtitle: subtitle]
    }

    static def _fixPathTokenMap(pathTokens) {
        pathTokens.inject([:]) { m, k, v -> m[k] = v; m }
    }
}
