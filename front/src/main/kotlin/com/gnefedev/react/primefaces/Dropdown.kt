@file:JsModule("primereact/components/dropdown/Dropdown")

package com.gnefedev.react.primefaces

import react.Component
import react.RProps
import react.RState

external class Dropdown : Component<DropdownProps, RState> {
    override fun render() = definedExternally
}

external interface DropdownProps : RProps {
    var id: String? get() = definedExternally; set(value) = definedExternally
    var value: Any? get() = definedExternally; set(value) = definedExternally
    var options: Array<Any>? get() = definedExternally; set(value) = definedExternally
    val itemTemplate: (() -> Unit)? get() = definedExternally
    var style: Any? get() = definedExternally; set(value) = definedExternally
    var className: String? get() = definedExternally; set(value) = definedExternally
    var autoWidth: Boolean? get() = definedExternally; set(value) = definedExternally
    var scrollHeight: String? get() = definedExternally; set(value) = definedExternally
    var filter: Boolean? get() = definedExternally; set(value) = definedExternally
    var filterplaceholder: String? get() = definedExternally; set(value) = definedExternally
    var editable: Boolean? get() = definedExternally; set(value) = definedExternally
    var placeholder: String? get() = definedExternally; set(value) = definedExternally
    var required: Boolean? get() = definedExternally; set(value) = definedExternally
    var disabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var appendTo: Any? get() = definedExternally; set(value) = definedExternally
    var tabIndex: Number? get() = definedExternally; set(value) = definedExternally
    var autoFocus: Boolean? get() = definedExternally; set(value) = definedExternally
    var lazy: Boolean? get() = definedExternally; set(value) = definedExternally
    var panelClassName: String? get() = definedExternally; set(value) = definedExternally
    var panelstyle: Any? get() = definedExternally; set(value) = definedExternally
    var dataKey: String? get() = definedExternally; set(value) = definedExternally
    var inputId: String? get() = definedExternally; set(value) = definedExternally
    var onChange: ((e: OnChangeEvent) -> Unit)? get() = definedExternally; set(value) = definedExternally
    val onMouseDown: (() -> Unit)? get() = definedExternally
    val onContextMenu: (() -> Unit)? get() = definedExternally
}
