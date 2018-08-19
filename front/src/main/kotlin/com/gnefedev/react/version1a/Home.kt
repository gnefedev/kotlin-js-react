package com.gnefedev.react.version1a

import com.gnefedev.common.Car
import com.gnefedev.react.bridge.SelectItem
import com.gnefedev.react.bridge.column
import com.gnefedev.react.bridge.datatable
import com.gnefedev.react.bridge.dropdown
import com.gnefedev.react.jsObjectAsMap
import com.gnefedev.react.version1a.Wrapper.WrapperProps
import com.gnefedev.react.version1a.Wrapper.WrapperState
import kotlinext.js.clone
import kotlinext.js.js
import kotlinext.js.jsObject
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
import kotlin.reflect.KClass

fun RBuilder.renderHome(props: RouteResultProps<*>) = wrap(Home::class, {
  println("fetching")
  println(jsObjectAsMap(props))
  println(jsObjectAsMap(props.location))
  attrs.brands = fetchJson(
    "/api/brands",
    StringSerializer.list
  )
  attrs.colors = fetchJson(
    "/api/colors",
    StringSerializer.list
  )
  attrs.cars = fetchCars(null, null)
})

fun <P: RProps, C: RComponent<P, *>> RBuilder.wrap(component: KClass<C>, loader: suspend RElementBuilder<P>.() -> Unit): ReactElement? =
  child(Wrapper::class) {
    attrs.loader = {
      buildElement {
        val props = jsObject<P> {}
        val children = with(RElementBuilder(props)) {
          loader()
          childList
        }
        child(component.js, props, children)
      }!!
    }
  }

class Wrapper2: RComponent<Wrapper2Props, RState>() {
  override fun RBuilder.render() {
    child(props.child(this, props.location))
  }

  override fun componentDidMount() {
    props.history.listen {

    }
  }
}

external interface Wrapper2Props: LocationProps {
  var child: (RBuilder, RLocation) -> ReactElement
}

class Wrapper: RComponent<WrapperProps, WrapperState>() {
  override fun RBuilder.render() {
    if (state.loaded) {
      child(state.child)
    }
  }

  override fun componentDidMount() {
    launch {
      updateState {
        child = props.loader()
        loaded = true
      }
    }
  }

  class WrapperState : RState {
    var loaded: Boolean = false
    lateinit var child: ReactElement
  }

  interface WrapperProps: RProps {
    var loader: suspend () -> ReactElement
  }
}

class Home(
  props: Props
) : RComponent
<Props, State>
(props) {
  init {
    println(jsObjectAsMap(props))
    println(jsObjectAsMap(props.location))
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
  navigateToChanged(brand = it)
          },

          colors = state.colors,
          color = state.color,
          onColorChange = {
  navigateToChanged(color = it)
          }

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
"?brand=" + (brand ?: "")
+ "&color=" + (color ?: ""))
  }

  override fun componentDidMount()
  {
    props.history.listen {
      location ->
      val query = searchAsMap(
        location.search
      )
      updateState {
        brand = query["brand"]
        color = query["color"]
      }
      launch {
        loadData(
          query["brand"],
          query["color"]
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
    updateState {
      cars = fetchCars(brand, color)
      loaded = true
    }
  }

}

private suspend fun fetchCars(brand: String?, color: String?): List<Car> {
  val url = "/api/cars?" +
    "brand=" + (brand ?: "") +
    "&color=" + (color ?: "")
  val cars = fetchJson(
    url,
    Car::class.serializer().list
  )
  return cars
}

external interface Props : LocationProps {
  var cars: List<Car>
  var brands: List<String>
  var colors: List<String>
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

infix fun <T : Any>
  List<SelectItem<T>>.withDefault(
  label: String
) = listOf(
  SelectItem(
    label = label, value = null
  )
) + this

external interface LocationProps
  : RProps {
  var location: RLocation
}

external interface RLocation {
  var search: String?
}

val RProps.history: RHistory get()
= this.asDynamic().history
    .unsafeCast<RHistory>()

external interface RHistory {
  fun push(
    path: String,
    state: Any? = definedExternally
  )
  fun listen(
    listener: (RLocation) -> Unit
  )
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

