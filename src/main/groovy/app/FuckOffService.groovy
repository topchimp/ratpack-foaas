package app

import groovy.text.SimpleTemplateEngine

class FuckOffService {

    private final Properties messages
    private final SimpleTemplateEngine templateEngine

    FuckOffService(Properties messages, SimpleTemplateEngine templateEngine) {
        this.messages = messages
        this.templateEngine = templateEngine
    }

    Map<String, String> get(String key, String from, String to) {
        def params = [from: from, to: to]
        ["message", "subtitle"].collectEntries {
            [it, render("${key}.${it}", params)]
        }
    }

    private String render(String key, Map<?, ?> params) {
        def template = messages.getProperty("fuckOff.$key")
        template ? templateEngine.createTemplate(template).make(params) : null
    }

}
