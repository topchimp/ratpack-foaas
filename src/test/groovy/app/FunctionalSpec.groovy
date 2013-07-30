package app

import org.ratpackframework.test.ScriptAppSpec

class FunctionalSpec extends ScriptAppSpec {

    def "content negotiation"() {
        when:
        request.header("Accept", "*/*")

        then:
        getText("off/to/from") == "Fuck off, to. - from"

        when:
        request.header("Accept", "application/json")

        then:
        with(get("off/to/from").jsonPath()) {
            get("message") == "Fuck off, to."
            get("subtitle") == "- from"
        }

        when:
        request.header("Accept", "text/html")

        then:
        with(get("off/to/from")) {
            contentType == "text/html;charset=UTF-8"
            body.asString().contains "Fuck off, to."
        }
    }

    def "handles unknowns"() {
        when:
        get("foo/bar/baasdfa")

        then:
        response.statusCode == 404
    }

    def "pathtokens are properly encoded"() {
        when:
        request.header("Accept", "text/html")

        then:
        with(get("blink182/generation/fucking%20heart")) {
            !body.asString().contains("%20")
        }

    }
}
