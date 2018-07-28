package com.gnefedev.react.bridge

import react.Component
import react.RBuilder
import react.RClass
import react.RHandler
import react.RProps
import kotlin.reflect.KClass

fun RBuilder.browserRouter(handler: RHandler<RProps>) = child(BrowserRouterComponent::class, handler)

fun RBuilder.switch(handler: RHandler<RProps>) = child(SwitchComponent::class, handler)

fun RBuilder.route(path: String, component: KClass<out Component<*, *>>, exact: Boolean = false) =
    child(RouteComponent::class) {
        attrs {
            this.path = path
            this.exact = exact
            this.component = component.js.unsafeCast<RClass<RProps>>()
        }
    }
