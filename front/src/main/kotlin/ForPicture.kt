import com.gnefedev.react.bridge.SelectItem
import com.gnefedev.react.bridge.dropdown
import react.RBuilder
import react.buildElement
import react.dom.div
import react.dom.span

fun RBuilder.question() {
  div {
    span { +"What to choose?" }
    dropdown(
      value = "Kotlin",
      onChange = {
        console.log("You are traitor!!!")
      },
      options = listOf(
        SelectItem(
          label = "Javascript",
          value = "js"
        ),
        SelectItem(
          label = "Kotlin",
          value = "kt"
        )
      )
    ) {}
  }
}



fun some() {
  buildElement { question() }
}
