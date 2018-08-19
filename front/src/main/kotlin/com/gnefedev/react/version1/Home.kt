package com.gnefedev.react.version1

import com.gnefedev.common.Car
import com.gnefedev.react.bridge.SelectItem
import com.gnefedev.react.bridge.column
import com.gnefedev.react.bridge.datatable
import com.gnefedev.react.bridge.dropdown
import kotlinext.js.clone
import kotlinext.js.js
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.launch
import kotlinx.html.style
import kotlinx.serialization.KSerializer
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import kotlinx.serialization.serializer
import react.*
import react.dom.div
import react.dom.span
import react.router.dom.RouteResultProps
import kotlin.browser.window

class Home(
  props: RouteResultProps<*>
) : RComponent
<RouteResultProps<*>, State>
(props) {
  init {
    state = State(

      color = searchAsMap(
        props.location.search
      )["color"],
      brand = searchAsMap(
        props.location.search
      )["brand"]



    )
  }

  override fun
    RBuilder.render() {
    if (!state.loaded) return


    layout {
      header {
        homeHeader(
          brands = state.brands,
          brand = state.brand,
          onBrandChange = {
navigateToChanged(brand = it) },
          colors = state.colors,
          color = state.color,
          onColorChange = {
navigateToChanged(color = it) }
        )
      }
      content {
        homeContent(
          cars = state.cars
        )
      }
    }

  }

  private fun navigateToChanged(
    brand: String? = state.brand,
    color: String? = state.color
  ) {
    props.history.push(
"?brand=${brand.orEmpty()}"
+ "&color=${color.orEmpty()}")
    updateState {
      this.brand = brand
      this.color = color
    }
    launch {
      loadCars()
    }
  }

  override fun componentDidMount()
  {
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

      loadCars()
    }
  }

  private suspend fun loadCars() {
    val url = "/api/cars?brand=${state.brand.orEmpty()}&color=${state.color.orEmpty()}"
    updateState {
      cars = fetchJson(url, Car::class.serializer().list)
      loaded = true
    }
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

infix fun <T : Any>
  List<SelectItem<T>>.withDefault(
  label: String
) = listOf(
  SelectItem(
    label = label, value = null
  )
) + this

private fun RBuilder.homeContent(
  cars: List<Car>
) {
  datatable(cars) {
    column(header = "Brand") {

      +it.brand
    }
    column(header = "Color") {

      span {
        attrs.style = js {
          color = it.color
        }
        +it.color
      }
    }
    column(header = "Year") {

      +"${it.year}"
    }
  }
}

//Layout
private fun RBuilder.layout(
  children: RBuilder.() -> Unit
) {
  div(classes = "wrapper") {
    children()
  }
}

private fun RBuilder.header(
  children: RBuilder.() -> Unit
) {
  div(classes = "header") {
    children()
  }
}

private fun RBuilder.content(
  children: RBuilder.() -> Unit
) {
  div(classes = "content") {
    children()
  }
}

//infrastructure
fun searchAsMap(search: String?) =
  if (search != null
    && search.length > 1) {

    search.substring(1)
      .split("&")
      .map { it.split("=") }

      .map { it[0] to it[1] }
      .toMap()


  } else {
    emptyMap()
  }

private val serializer: JSON
  = JSON()

suspend fun <T> fetchJson(
  url: String,
  kSerializer: KSerializer<T>
): T {
  val json = window.fetch(url)
    .await().text().await()
  return serializer.parse(
    kSerializer,
    json
  )
}

inline fun <S : RState>
  Component<*, S>.updateState(
  action: S.() -> Unit
) {
  setState(
    clone(state).apply(action)
  )
}

