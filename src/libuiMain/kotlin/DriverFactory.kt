import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.bethwelamkenya.Church

//import app.cash.sqldelight.db.SqlDriver
//import app.cash.sqldelight.driver.native.NativeSqliteDriver
//
//expect class DriverFactory {
//    fun createDriver(): SqlDriver
//}
//
//actual class DriverFactory {
//    actual fun createDriver(): SqlDriver {
//        TODO()
//    }
//}
//


fun connectDatabase() {
    val driver = NativeSqliteDriver(Church.Schema, "church.db")
    val database = Church(driver)
}

//fun createDatabase(driverFactory: DriverFactory): Church {
//    val driver: SqlDriver = NativeSqliteDriver(Church.Schema, "test.db")
////    val driver = driverFactory.createDriver()
////    val database = Database(driver)
//    // Do more work with the database (see below).
//    // In reality the database and driver above should be created a single time
//// and passed around using your favourite dependency injection/service
//// locator/singleton pattern.
//    val database = Church(driver)
//
//    val playerQueries: Church = database.playerQueries
////    val playerQueries: PlayerQueries = database.playerQueries
//
//    println(playerQueries.selectAll().executeAsList())
//// Prints [HockeyPlayer(15, "Ryan Getzlaf")]
//
//    playerQueries.insert(player_number = 10, full_name = "Corey Perry")
//    println(playerQueries.selectAll().executeAsList())
//// Prints [HockeyPlayer(15, "Ryan Getzlaf"), HockeyPlayer(10, "Corey Perry")]
//
//    val player = Member(10, "Ronald McDonald")
//    playerQueries.insertFullPlayerObject(player)
//}