@file:JsModule("primereact/components/datatable/DataTable")

package com.gnefedev.react.primefaces

import org.w3c.dom.events.Event
import react.Component
import react.RProps
import react.RState
import react.ReactElement

external class DataTable : Component<DataTableProps<*>, RState> {
    override fun render() = definedExternally
}

external interface DataTableLazyLoadEvent {
    var filters: dynamic
    var first: Int
    var rows: Int
    var sortField: String
    var sortOrder: Int
    var multiSortMeta: Array<Any>
}

external interface FilterEvent {
    var filters: dynamic
}

external interface FilterItem {
    var value: String
    var matchMode: String
}

external interface `T$0` {
    var originalEvent: Event
    var data: Any
}

external interface `T$1` {
    var element: Any
    var delta: Number
}

external interface `T$2` {
    var sortField: String
    var sortOrder: Number
    var multiSortMeta: Any
}

external interface `T$3` {
    var originalEvent: Event
    var data: Any
    var index: Number
}

external interface `T$4` {
    var dragIndex: Number
    var dropIndex: Number
    var columns: Any
}

external interface DataTableProps<T: Any> : RProps {
    var id: String? get() = definedExternally; set(value) = definedExternally
    var value: Array<Any>? get() = definedExternally; set(value) = definedExternally
    var header: Any? get() = definedExternally; set(value) = definedExternally
    var footer: Any? get() = definedExternally; set(value) = definedExternally
    var style: Any? get() = definedExternally; set(value) = definedExternally
    var className: String? get() = definedExternally; set(value) = definedExternally
    var tableStyle: Any? get() = definedExternally; set(value) = definedExternally
    var tableClassName: String? get() = definedExternally; set(value) = definedExternally
    var paginator: Boolean? get() = definedExternally; set(value) = definedExternally
    var paginatorPosition: String? get() = definedExternally; set(value) = definedExternally
    var paginatorRight: ReactElement? get() = definedExternally; set(value) = definedExternally
    var paginatorLeft: ReactElement? get() = definedExternally; set(value) = definedExternally
    var alwaysShowPaginator: Boolean? get() = definedExternally; set(value) = definedExternally
    var paginatorTemplate: String? get() = definedExternally; set(value) = definedExternally
    var first: Number? get() = definedExternally; set(value) = definedExternally
    var rows: Number? get() = definedExternally; set(value) = definedExternally
    var totalRecords: Number? get() = definedExternally; set(value) = definedExternally
    var lazy: Boolean? get() = definedExternally; set(value) = definedExternally
    var sortField: String? get() = definedExternally; set(value) = definedExternally
    var sortOrder: Number? get() = definedExternally; set(value) = definedExternally
    var multiSortMeta: Array<Any>? get() = definedExternally; set(value) = definedExternally
    var sortMode: String? get() = definedExternally; set(value) = definedExternally
    var emptyMessage: String? get() = definedExternally; set(value) = definedExternally
    var selectionMode: String? get() = definedExternally; set(value) = definedExternally
    var selection: Any? get() = definedExternally; set(value) = definedExternally
    val onSelectionChange: ((e: `T$0`) -> Unit)? get() = definedExternally
    var compareSelectionBy: String? get() = definedExternally; set(value) = definedExternally
    var dataKey: String? get() = definedExternally; set(value) = definedExternally
    var metaKeySelection: Boolean? get() = definedExternally; set(value) = definedExternally
    var headerColumnGroup: Any? get() = definedExternally; set(value) = definedExternally
    var footerColumnGroup: Any? get() = definedExternally; set(value) = definedExternally
    val rowExpansionTemplate: (() -> Unit)? get() = definedExternally
    var expandedRows: Array<Any>? get() = definedExternally; set(value) = definedExternally
    val onRowToggle: (() -> Unit)? get() = definedExternally
    var responsive: Boolean? get() = definedExternally; set(value) = definedExternally
    var resizableColumns: Boolean? get() = definedExternally; set(value) = definedExternally
    var columnResizeMode: String? get() = definedExternally; set(value) = definedExternally
    var reorderableColumns: Boolean? get() = definedExternally; set(value) = definedExternally
    var filters: Any? get() = definedExternally; set(value) = definedExternally
    var globalFilter: Any? get() = definedExternally; set(value) = definedExternally
    var scrollable: Boolean? get() = definedExternally; set(value) = definedExternally
    var scrollHeight: String? get() = definedExternally; set(value) = definedExternally
    var virtualScroll: Boolean? get() = definedExternally; set(value) = definedExternally
    var virtualScrollDelay: Number? get() = definedExternally; set(value) = definedExternally
    var frozenWidth: String? get() = definedExternally; set(value) = definedExternally
    var unfrozenWidth: String? get() = definedExternally; set(value) = definedExternally
    var frozenValue: Array<Any>? get() = definedExternally; set(value) = definedExternally
    var csvSeparator: String? get() = definedExternally; set(value) = definedExternally
    var exportFilename: String? get() = definedExternally; set(value) = definedExternally
    var contextMenu: Any? get() = definedExternally; set(value) = definedExternally
    var rowGroupMode: String? get() = definedExternally; set(value) = definedExternally
    val rowClassName: ((rowData: Any) -> Any)? get() = definedExternally
    val rowGroupHeaderTemplate: (() -> Unit)? get() = definedExternally
    val rowGroupFooterTemplate: (() -> Unit)? get() = definedExternally
    val onColumnResizeEnd: ((e: `T$1`) -> Unit)? get() = definedExternally
    val onSort: ((e: `T$2`) -> Unit)? get() = definedExternally
    val onPage: ((event: Event) -> Unit)? get() = definedExternally
    var onFilter: ((filters: FilterEvent) -> Unit)? get() = definedExternally; set(value) = definedExternally
    var onLazyLoad: ((event: DataTableLazyLoadEvent) -> Unit)? get() = definedExternally; set(value) = definedExternally
    val onRowClick: ((e: `T$3`) -> Unit)? get() = definedExternally
    val onRowSelect: ((e: `T$3`) -> Unit)? get() = definedExternally
    val onRowUnselect: ((e: `T$3`) -> Unit)? get() = definedExternally
    val onRowExpand: ((e: `T$0`) -> Unit)? get() = definedExternally
    val onRowCollapse: ((e: `T$0`) -> Unit)? get() = definedExternally
    val onContextMenuSelect: ((e: `T$0`) -> Unit)? get() = definedExternally
    val onColReorder: ((e: `T$4`) -> Unit)? get() = definedExternally
}
