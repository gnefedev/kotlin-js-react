package com.gnefedev.react.bridge

import com.gnefedev.react.RProperty
import com.gnefedev.react.primefaces.Column
import com.gnefedev.react.primefaces.ColumnProps
import com.gnefedev.react.primefaces.DataTable
import com.gnefedev.react.primefaces.DataTableProps
import com.gnefedev.react.primefaces.Dropdown
import com.gnefedev.react.primefaces.DropdownProps
import react.RBuilder
import react.RElementBuilder
import react.buildElement

fun <T : Any> RBuilder.datatable(
    value: Collection<T>,
    handler: RElementBuilder<DataTableProps<T>>.() -> Unit
) = child(DataTable::class) {
    this.unsafeCast<RElementBuilder<DataTableProps<T>>>().run {
        attrs.value = value.toTypedArray()
        handler()
    }
}

fun <T : Any> RElementBuilder<DataTableProps<T>>.column(
    field: String? = null,
    header: String? = null,
    handler: RBuilder.(T) -> Unit
) = child(Column::class) {
    this.unsafeCast<RElementBuilder<ColumnProps<T>>>().run {
        attrs.field = field
        if (header != null) {
            attrs.header = header
        }
        attrs.body = {
            buildElement {
                handler(it)
            }
        }
    }
}

fun <T> RBuilder.dropdown(
    value: T,
    onChange: (T) -> Unit,
    options: List<SelectItem<T>>,
    handler: RElementBuilder<DropdownProps>.() -> Unit
) = child(Dropdown::class) {
    attrs.options = options.toTypedArray()
    attrs.onChange = {
        onChange.invoke(it.value.unsafeCast<T>())
    }
    attrs.value = value
    handler()
}

fun <T> RBuilder.dropdown(
  selected: RProperty<T>,
  options: List<SelectItem<T>>,
  handler: RElementBuilder<DropdownProps>.() -> Unit
) = child(Dropdown::class) {
    attrs.options = options.toTypedArray()
    attrs.onChange = {
        selected.onChange.invoke(it.value.unsafeCast<T>())
    }
    attrs.value = selected.value
    handler()
}

class SelectItem<out T>(
    val label: String,
    val value: T
)
