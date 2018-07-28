@file:JsModule("react-router-dom")

package com.gnefedev.react.bridge

import react.Component
import react.RClass
import react.RProps
import react.RState
import react.ReactElement

@JsName("BrowserRouter")
external class BrowserRouterComponent : Component<RProps, RState> {
    override fun render(): ReactElement?
}

@JsName("Switch")
external class SwitchComponent : Component<RProps, RState> {
    override fun render(): ReactElement?
}

@JsName("Route")
external class RouteComponent : Component<RouteProps, RState> {
    override fun render(): ReactElement?
}

external interface RouteProps : RProps {
    var path: String
    var exact: Boolean
    var component: RClass<RProps>
}
