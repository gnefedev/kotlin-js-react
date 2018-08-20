package com.gnefedev.react.version0onlyFilters

import kotlinext.js.clone
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import react.*
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

  override fun componentDidMount()
  {
    launch {
      updateState { //(3)
        brands = fetchJson( //(4)
          "/api/brands",
          StringSerializer.list
        )
        colors = fetchJson( //(4)
          "/api/colors",
          StringSerializer.list
        )
      }
    }
  }

  override fun
    RBuilder.render() {
  }
}

class State(
  var color: String?,
  var brand: String?
) : RState {
var loaded: Boolean = false //(1)
lateinit var brands: List<String> //(2)
lateinit var colors: List<String> //(2)
}

private val serializer: JSON = JSON()

suspend fun <T> fetchJson( //(4)
  url: String,
  kSerializer: KSerializer<T>
): T {
  val json = window.fetch(url)
    .await().text().await()
  return serializer.parse(
    kSerializer, json
  )
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

inline fun <S : RState>
  Component<*, S>.updateState(
  action: S.() -> Unit
) {
  setState(
    clone(state).apply(action)
  )
}

