import app.ContentNegotiationCategory
import groovy.text.SimpleTemplateEngine
import org.ratpackframework.groovy.templating.TemplateRenderer

import static app.PropertiesHolder._ as props
import static org.ratpackframework.groovy.RatpackScript.ratpack

props.props = new Properties().with {
    load(getClass().getResourceAsStream("messages.properties"))
    it
}
props.engine = new SimpleTemplateEngine()

ratpack {
	handlers {

        get {
            get(TemplateRenderer).render "index.html"
        }

        get(":type/:from") {
            def map = [message: props.getRenderedTemplate(pathTokens).message as String, subtitle: pathTokens['from']]
            use (ContentNegotiationCategory) {
                makeFuck map, get(TemplateRenderer)
            }
        }

        get(":type/:to/:from") {
            def map = props.getRenderedTemplate(pathTokens)
            use (ContentNegotiationCategory) {
                makeFuck map, get(TemplateRenderer)
            }
        }

		assets "public"
	}
}
