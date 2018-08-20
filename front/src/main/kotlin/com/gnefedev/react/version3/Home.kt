package com.gnefedev.react.version3

import com.gnefedev.common.Car
import com.gnefedev.react.bridge.SelectItem
import com.gnefedev.react.bridge.column
import com.gnefedev.react.bridge.datatable
import com.gnefedev.react.bridge.dropdown
import com.gnefedev.react.fetchJson
import com.gnefedev.react.updateState
import com.gnefedev.react.version1.withDefault
import com.gnefedev.react.version3.Home.Query
import com.gnefedev.react.version3.Home.State
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

class Home(
  props: ContextRouter<Query>
) : LayoutComponent
<ContextRouter<Query>, State>
(props) {
  init {
    state = State(
      color = props.location.query.color,
      brand = props.location.query.brand
    )
  }

  override fun RBuilder
    .renderHeader() {
    if (!state.loaded) return
    homeHeader(
      brands = state.brands,
      brand = state.brand,
      onBrandChange = {
  navigateToChanged(brand = it)
      },
      colors = state.colors,
      color = state.color,
      onColorChange = {
  navigateToChanged(color = it)
      }
    )
  }

  override fun RBuilder
    .renderContent() {
    if (!state.loaded) return
    homeContent(
      cars = state.cars
    )
  }

  private fun navigateToChanged(brand: String? = state.brand, color: String? = state.color) {
    props.history.push("?brand=" + (brand ?: "") + "&color=" + (color ?: ""))
  }

  override fun componentDidMount()
  {
    props.history.listen {
      location ->
      updateState {
    brand = location.query.brand
    color = location.query.color
      }
      launch {
        loadData(
          location.query.brand,
          location.query.color
        )
      }
    }
    launch {
      updateState {
        brands = fetchJson(
          "/api/brands",
          StringSerializer.list
        )
        colors = fetchJson(
          "/api/colors",
          StringSerializer.list
        )
      }

      loadData(
        state.brand,
        state.color
      )
    }
  }

  private suspend fun loadData(
    brand: String?,
    color: String?
  ) {
    val url = "/api/cars" +
      "?brand=" + (brand ?: "") +
      "&color=" + (color ?: "")
    updateState {
      cars = fetchJson(
  url,
  Car::class.serializer().list
      )
      loaded = true
    }
  }

  class State(
    var color: String?,
    var brand: String?
  ) : RState {
    var loaded: Boolean = false
    lateinit var cars: List<Car>
    lateinit var brands: List<String>
    lateinit var colors: List<String>
  }

  interface Query {
    var color: String?
    var brand: String?
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
    options = brands.map {
      SelectItem(
        label = it, value = it
      )
    } withDefault "all"
  ) {}
  +"Color:"
  dropdown(
    value = color,
    onChange = onColorChange,
    options = colors.map {
      SelectItem(
        label = it, value = it
      )
    } withDefault "all"
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
abstract class LayoutComponent<P : RProps, S : RState> : RComponent<P, S> {
  constructor() : super()
  constructor(props: P) : super(props)

  final override fun RBuilder.render() {
    div(classes = "wrapper") {
      div(classes = "header") {
        renderHeader()
      }
      div(classes = "content") {
        renderContent()
      }
    }
  }

  open fun RBuilder.renderHeader() {}

  abstract fun RBuilder.renderContent()
}

//infrastructure
external interface ContextRouter<T> : RProps {
  var location: RLocation<T>
}

external interface RLocation<T> {
  var search: String?
}

val <T> ContextRouter<T>.history: RHistory<T> get() = this.asDynamic().history.unsafeCast<RHistory<T>>()

external interface RHistory<T> {
  fun push(path: String, state: Any? = definedExternally)
  fun listen(listener: (RLocation<T>) -> Unit)
}

val <T> RLocation<T>.query: T
  get() {
    val result = js("{}")
    val queryString = search?.substring(1)
    if (queryString != null && !queryString.isBlank()) {
      queryString.split("&")
        .map { it.split("=") }
        .forEach { result[it[0]] = it[1] }
    }
    return result.unsafeCast<T>()
  }

infix fun <T : Any> List<SelectItem<T>>.withDefault(label: String) =
  listOf(SelectItem(label = label, value = null)) + this
