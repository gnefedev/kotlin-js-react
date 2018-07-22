package com.gnefedev.react

import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        render(document.getElementById("react")!!) {
            child(ApplicationComponent::class) {}
        }
    }
}
