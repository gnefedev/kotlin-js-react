package com.gnefedev.react

import com.gnefedev.react.bridge.browserRouter
import com.gnefedev.react.bridge.route
import com.gnefedev.react.bridge.switch
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        render(document.getElementById("react")!!) {
            browserRouter {
                switch {
                    route("/version1", component = com.gnefedev.react.version1.Home::class, exact = true)
                    route("/version2", component = com.gnefedev.react.version2.Home::class, exact = true)
                    route("/version3", component = com.gnefedev.react.version3.Home::class, exact = true)
                    route("/version3a", component = com.gnefedev.react.version3a.Home::class, exact = true)
                    route("/version4", component = com.gnefedev.react.version4.Home::class, exact = true)
                    route("/version5", component = com.gnefedev.react.version5.Home::class, exact = true)
                }
            }
        }
    }
}
