package com.gnefedev.react.primefaces

import org.w3c.dom.events.Event

external interface OnChangeEvent {
    var originalEvent: Event
    var value: Any?
}