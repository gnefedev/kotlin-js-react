@file:JsModule("primereact/components/column/Column")

package com.gnefedev.react.primefaces

import react.Component
import react.RProps
import react.RState
import react.ReactElement

external class Column : Component<ColumnProps<*>, RState> {
    override fun render() = definedExternally
}

external interface ColumnProps<T: Any> : RProps {
    var columnKey: String? get() = definedExternally; set(value) = definedExternally
    var field: String? get() = definedExternally; set(value) = definedExternally
    var sortField: String? get() = definedExternally; set(value) = definedExternally
    var header: Any? get() = definedExternally; set(value) = definedExternally
    var body: ((T) -> ReactElement?)? get() = definedExternally; set(value) = definedExternally
    var footer: Any? get() = definedExternally; set(value) = definedExternally
    var sortable: Boolean? get() = definedExternally; set(value) = definedExternally
    val sortFunction: (() -> Unit)? get() = definedExternally
    var filter: Boolean? get() = definedExternally; set(value) = definedExternally
    var filterMatchMode: String? get() = definedExternally; set(value) = definedExternally
    var filterPlaceholder: String? get() = definedExternally; set(value) = definedExternally
    var filterType: String? get() = definedExternally; set(value) = definedExternally
    var filterMaxLength: Number? get() = definedExternally; set(value) = definedExternally
    var filterElement: Any? get() = definedExternally; set(value) = definedExternally
    var style: Any? get() = definedExternally; set(value) = definedExternally
    var className: String? get() = definedExternally; set(value) = definedExternally
    var expander: Boolean? get() = definedExternally; set(value) = definedExternally
    var frozen: Boolean? get() = definedExternally; set(value) = definedExternally
    var selectionMode: String? get() = definedExternally; set(value) = definedExternally
    var colSpan: Number? get() = definedExternally; set(value) = definedExternally
    var rowSpan: Number? get() = definedExternally; set(value) = definedExternally
    val editor: (() -> Unit)? get() = definedExternally
    val editorValidator: (() -> Unit)? get() = definedExternally
}
