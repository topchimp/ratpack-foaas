package app

import org.ratpackframework.handling.Context
import org.ratpackframework.render.ByTypeRenderer

import static groovy.json.JsonOutput.toJson
import static org.ratpackframework.groovy.Util.with
import static org.ratpackframework.groovy.templating.Template.groovyTemplate

class FuckOffRenderer extends ByTypeRenderer<FuckOff> {

    public FuckOffRenderer() {
        super(FuckOff)
    }

    @Override
    void render(Context context, FuckOff f) {
        with(context.accepts) {
            type("text/plain") {
                context.response.send "$f.message $f.subtitle"
            }
            type("text/html") {
                context.render groovyTemplate("fuckoff.html", f: f)
            }
            type("application/json") {
                context.response.send toJson(f)
            }
        }
    }
}
