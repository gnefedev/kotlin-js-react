package com.gnefedev.react.version1

import com.gnefedev.common.Car
import com.gnefedev.react.bridge.SelectItem
import com.gnefedev.react.bridge.column
import com.gnefedev.react.bridge.datatable
import com.gnefedev.react.bridge.dropdown
import com.gnefedev.react.fetchJson
import com.gnefedev.react.updateState
import com.gnefedev.react.version1.Home.State
import kotlinext.js.js
import kotlinx.coroutines.experimental.launch
import kotlinx.html.style
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.list
import kotlinx.serialization.serializer
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.span

class Home(props: LocationProps) : RComponent<LocationProps, State>(props) {
    init {
        state = State(
            loaded = false,
            color = searchAsMap(props.location.search)["color"],
            brand = searchAsMap(props.location.search)["brand"]
        )
    }

    override fun RBuilder.render() {
        if (!state.loaded) return
        layout {
            header {
                homeHeader(
                    brands = state.brands,
                    brand = state.brand,
                    onBrandChange = { navigateToChanged(brand = it) },
                    colors = state.colors,
                    color = state.color,
                    onColorChange = { navigateToChanged(color = it) }
                )
            }
            content {
                homeContent(cars = state.cars)
            }
        }
    }

    private fun navigateToChanged(brand: String? = state.brand, color: String? = state.color) {
        props.history.push("?brand=" + (brand ?: "") + "&color=" + (color ?: ""))
    }

    override fun componentDidMount() {
        props.history.listen { location ->
            val query = searchAsMap(location.search)
            updateState {
                brand = query["brand"]
                color = query["color"]
            }
            launch {
                loadData(query["brand"], query["color"])
            }
        }
        launch {
            updateState {
                brands = fetchJson("/api/brands", StringSerializer.list)
                colors = fetchJson("/api/colors", StringSerializer.list)
            }

            loadData(state.brand, state.color)
        }
    }

    private suspend fun loadData(brand: String?, color: String?) {
        val url = "/api/cars?brand=" + (brand ?: "") + "&color=" + (color ?: "")
        updateState {
            cars = fetchJson(url, Car::class.serializer().list)
            loaded = true
        }
    }

    class State(
        var loaded: Boolean,
        var color: String?,
        var brand: String?
    ) : RState {
        lateinit var cars: List<Car>
        lateinit var brands: List<String>
        lateinit var colors: List<String>
    }
}

//render part
private fun RBuilder.homeHeader(
    brands: List<String>,
    brand: String?,
    onBrandChange: (String?) -> Unit,
    colors: List<String>,
    color: String?,
    onColorChange: (String?) -> Unit
) {
    +"Brand:"
    dropdown(
        value = brand,
        onChange = onBrandChange,
        options = brands.map { SelectItem(label = it, value = it) }.withDefault("all")
    ) {}
    +"Color:"
    dropdown(
        value = color,
        onChange = onColorChange,
        options = colors.map { SelectItem(label = it, value = it) }.withDefault("all")
    ) {}
}

private fun RBuilder.homeContent(cars: List<Car>) {
    datatable(cars) {
        column(header = "Brand") {
            +it.brand
        }
        column(header = "Color") {
            span {
                attrs.style = js { color = it.color }
                +it.color
            }
        }
        column(header = "Year") {
            +"${it.year}"
        }
    }
}

//Layout
private fun RBuilder.layout(children: RBuilder.() -> Unit) {
    div(classes = "wrapper") {
        children()
    }
}

private fun RBuilder.header(children: RBuilder.() -> Unit) {
    div(classes = "header") {
        children()
    }
}

private fun RBuilder.content(children: RBuilder.() -> Unit) {
    div(classes = "content") {
        children()
    }
}

//infrastructure
external interface LocationProps : RProps {
    var location: RLocation
}

external interface RLocation {
    var search: String?
}

val RProps.history: RHistory get() = this.asDynamic().history.unsafeCast<RHistory>()

external interface RHistory {
    fun push(path: String, state: Any? = definedExternally)
    fun listen(listener: (RLocation) -> Unit)
}

fun searchAsMap(search: String?): Map<String, String> =
    if (search != null && search.length > 1) {
        search.substring(1)
            .split("&")
            .map { it.split("=") }
            .map { it[0] to it[1] }
            .toMap()
    } else {
        emptyMap()
    }

infix fun <T: Any> List<SelectItem<T>>.withDefault(label: String) =
    listOf(SelectItem(label = label, value = null)) + this
