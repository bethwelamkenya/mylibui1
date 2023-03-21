import libui.ktx.*

fun main() = appWindow(
    title = "Hello",
    width = 400,
    height = 300
) {
    vbox {
        lateinit var scroll: TextArea

        button("libui говорит: click me!") {
            action {
                scroll.append("""
                    |Hello, World!  Ciao, mondo!
                    |Привет, мир!  你好，世界！
                    |
                    |""".trimMargin())
            }
        }
        scroll = textarea {
            readonly = true
            stretchy = true
        }
    }
}
