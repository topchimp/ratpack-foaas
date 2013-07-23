package app

import groovy.json.JsonBuilder
import org.ratpackframework.groovy.templating.TemplateRenderer
import org.ratpackframework.handling.Exchange

/**
 * User: danielwoods
 * Date: 7/23/13
 */
@Category(Exchange)
class ContentNegotiationCategory {

    String _asJson(map) {
        def builder = new JsonBuilder(map)
        builder.toString()
    }

    String _asPlain(params) {
        "$params.message $params.subtitle"
    }

    String negotiate(params) {
        def ct = request.getHeader("Accept")
        switch (ct) {
            case "application/json":
                response.send _asJson(params)
                break

            case "text/plain":
                response.send _asPlain(params)
                break

            default:
                throw new ContentNegotiationException()

        }
        return null
    }

    void makeFuck(params, TemplateRenderer renderer) {
        try {
            negotiate params
        } catch (e) {
            if (e instanceof ContentNegotiationException) {
                renderer.render params, "fuckoff.html"
            }
        }
    }
}
