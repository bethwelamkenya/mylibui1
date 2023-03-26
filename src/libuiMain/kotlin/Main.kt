import kotlinx.cinterop.*
import libui.ktx.*
import platform.posix.getenv
import platform.posix.localtime
import platform.posix.time
import platform.posix.time_tVar

//fun getUserName(): String = Platform.osFamily.name
fun getUserName1() = getenv("USERNAME")?.toKString()
fun getDayTime(): String {
    val now = nativeHeap.alloc<time_tVar>()
    time(now.ptr)
    val tm = localtime(now.ptr)!!.pointed
    val time = when (tm.tm_hour) {
        in 0..11 -> "MORNING"
        in 12..17 -> "AFTERNOON"
        in 18..20 -> "EVENING"
        else -> "NIGHT"
    }
    nativeHeap.free(now)
    return time
}

fun main() = appWindow(
    title = "Hello",
    width = 500,
    height = 400
) {
    val welcomeText = "GOOD"
    val welcomeUser = getUserName1()
    val welcomeTime = getDayTime()
//    lateinit var welcomeConnectionStatus: Label
//    lateinit var userNameTextArea: Label
//    lateinit var passwordTextArea: Label
//    lateinit var accountTextArea: Label
    lateinit var userNameTextField: TextField
    lateinit var passwordTextField: PasswordField
    lateinit var accountCombobox: Combobox
    lateinit var logInButton: Button
//    lateinit var resetPasswordText: Label
//    lateinit var resetPasswordLink: Label
//    lateinit var scroll: TextArea
    vbox {
        hbox(padded = true) {
            label(text = "$welcomeText ")
            label(text = welcomeTime)
            label(text = "$welcomeUser")
        }
        separator()
        stretchy = true
        vbox.gridpane(padded = true) {
//            row()
            group("Log In") { stretchy = true }.form {
//            group("Log In") {}.form {
                userNameTextField = textfield {
                    label = "User Name"
//                value = "Password"
                    action {
                        logInButton.enabled =
                            userNameTextField.value.isNotEmpty() && passwordTextField.value.isNotEmpty()
                    }
                    /**
                     *               label("User Name") {
                     *                      FamilyAttribute("Courier New")
                     *                      SizeAttribute(28.0)
                     *                      WeightAttribute(uiTextWeightBold)
                     *                }
                     */
                }
                passwordTextField = passwordfield {
                    label = "Password"
//                value = "********"
                    action {
                        logInButton.enabled =
                            userNameTextField.value.isNotEmpty() && passwordTextField.value.isNotEmpty()
                    }
//                label("Password") {
//                    FamilyAttribute("Courier New")
//                    SizeAttribute(28.0)
//                    WeightAttribute(uiTextWeightBold)
//                }
                }
                accountCombobox = combobox {
                    label = "Account"
                    item("Admin")
                    item("Member")
                    item("Developer")
                    value = 0
                    action {
                        logInButton.enabled =
                            userNameTextField.value.isNotEmpty() && passwordTextField.value.isNotEmpty()
                    }
                    /**
                    label("Account") {
                    FamilyAttribute("Courier New")
                    SizeAttribute(28.0)
                    WeightAttribute(uiTextWeightBold)
                    }
                     */
                }
            }
//        separator()
            row()
            xspan = 1
            halign = libui.uiAlignCenter
            valign = libui.uiAlignStart
            gridpane {
                logInButton = button(text = "Log In") {
                    action {
                        logIn(
                            userName = userNameTextField.value,
                            password = passwordTextField.value,
                            account = accountCombobox.value
                        )
//                        createDemoTable()
                    }
                }
            }
            logInButton.enabled = userNameTextField.value.isNotEmpty() && passwordTextField.value.isNotEmpty()
            row()
            gridpane(padded = true) {
//            row()
//            xspan = 2
//            halign = libui.uiAlignCenter
//            valign = libui.uiAlignStart
                label("Forgot Password?")
                label("Reset Password") {

                }
            }
//        button("Hello There: click me!") {
//            action {
//                welcomeText = "Hello There"
//                scroll.append("""
//                    |Hello, World!
//                    |Habari Yako
//                    |U Salama?
//                    |Ni Vyema Kwamba U Salama
//                    |You Clicked me
//                    |
//                    |""".trimMargin())
//            }
//        }
//        scroll = textarea(wrap = true) {
//            readonly = true
//            stretchy = true
//        }
        }
    }
}

fun logIn(userName: String, password: String, account: Int) {
    println("$userName $password $account")
    MsgBox(
        text = "Log In Successful",
        details = "$userName $password $account"
    )
}