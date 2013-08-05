import app.FoaasModule
import app.FuckOffService

import static org.ratpackframework.groovy.RatpackScript.ratpack

ratpack {
    modules {
        register new FoaasModule(getClass().getResource("messages.properties"))
    }

    handlers { FuckOffService service ->
        get(":type/:p1/:p2?") {

            def to = (pathTokens.p2 ? pathTokens.p1 : null)?.decodeHtml()
            def from = (pathTokens.p2 ?: pathTokens.p1)?.decodeHtml()

            def f = service.get(pathTokens.type, from, to)
            if (f) {
                render f
            } else {
                clientError 404
            }
        }

        assets "public", "index.html"
    }
}
