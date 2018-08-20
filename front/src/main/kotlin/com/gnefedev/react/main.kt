package com.gnefedev.react

import react.dom.render
import react.router.dom.browserRouter
import react.router.dom.route
import react.router.dom.switch
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        render(document.getElementById("react")!!) {
            browserRouter {
                switch {
                    route("/version1", component = com.gnefedev.react.version1.Home::class, exact = true)
//                    route("/version1a", render = { it: RouteResultProps<RProps> -> renderHome(it) }, exact = true)
                    route("/version2", component = com.gnefedev.react.version2.Home::class, exact = true)
                    route("/version2a", component = com.gnefedev.react.version2a.Home::class, exact = true)
                    route("/version3", component = com.gnefedev.react.version3.Home::class, exact = true)
                    route("/version3a", component = com.gnefedev.react.version3a.Home::class, exact = true)
                    route("/version4", component = com.gnefedev.react.version4.Home::class, exact = true)
                    route("/version5", component = com.gnefedev.react.version5.Home::class, exact = true)
                }
            }
        }
    }
}
