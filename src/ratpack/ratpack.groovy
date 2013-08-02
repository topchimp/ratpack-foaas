import app.FoaasModule
import app.FuckOffService
import org.ratpackframework.groovy.templating.TemplateRenderer

import static groovy.json.JsonOutput.toJson
import static org.ratpackframework.groovy.RatpackScript.ratpack
import static org.ratpackframework.groovy.Util.with

ratpack {
    modules {
        register new FoaasModule(getClass().getResource("messages.properties"))
    }

    handlers {
        get(":type/:p1/:p2?") { TemplateRenderer renderer, FuckOffService service ->

            def to = (pathTokens.p2 ? pathTokens.p1 : null)?.decodeHtml()
            def from = (pathTokens.p2 ?: pathTokens.p1)?.decodeHtml()

            def f = service.get(pathTokens.type, from, to)
            if (f.values().every { it == null }) {
                clientError 404
                return
            }

            with(accepts) {
                type("text/plain") {
                    response.send "$f.message $f.subtitle"
                }
                type("text/html") {
                    renderer.render f, "fuckoff.html"
                }
                type("application/json") {
                    response.send toJson(f)
                }
            }
        }

        assets "public", "index.html"
    }
}
