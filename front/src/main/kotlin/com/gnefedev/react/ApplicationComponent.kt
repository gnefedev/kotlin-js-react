package com.gnefedev.react

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class ApplicationComponent : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        div(classes = "application") {
            +"Hello world!!!"
        }
    }
}
