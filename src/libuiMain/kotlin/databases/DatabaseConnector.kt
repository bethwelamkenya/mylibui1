package databases

//import com.squareup.sqldelight.db.SqlDriver
//import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import co.touchlab.sqliter.createDatabaseManager
import co.touchlab.sqliter.withStatement
import co.touchlab.sqliter.withTransaction
import kotlinx.cinterop.toKString
import platform.posix.getenv

//import com.bethwelamkenya.Database

//import sqlite3.*

fun createDemoTable() {
    val userHome = getenv("USERPROFILE")?.toKString() ?: ""
    val dbPath = "$userHome\\AppData\\Roaming\\mylibui1\\Databases"
    val dbConfig = DatabaseConfiguration(
        name = "demo.db",
        version = 1,
        create = {
            it.withStatement("CREATE TABLE demo (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)") { execute() }
        },
        upgrade = { _, _, _ -> },
        basePath = dbPath
    )

    val dbManager = createDatabaseManager(dbConfig)
    println("Hello Created DB")
    val db = dbManager.createMultiThreadedConnection()
    try {
        // Use the database connection here
    } finally {
        db.close()
    }
}

fun tabular() {
//    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
//    val database = Database(driver)

    // Create a table
//    val createTableQuery = """
//        CREATE TABLE IF NOT EXISTS myTable (
//            id INTEGER PRIMARY KEY,
//            name TEXT NOT NULL
//        );
//    """.trimIndent()

//    database.getConnection().execute(null, createTableQuery, 0)
}

fun moDatabase() {
    val userHome = getenv("USERPROFILE")?.toKString() ?: ""
    val dbPath = "$userHome\\AppData\\Roaming\\mylibui1\\Databases"
    val dbName = "mydatabase.db"
    val dbConfig = DatabaseConfiguration(
        name = dbName,
        version = 1,
        basePath = dbPath,
        create = {
            it.createStatement("CREATE TABLE demo (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)").execute()
//            it.withStatement("CREATE TABLE test (id INTEGER PRIMARY KEY AUTOINCREMENT, value TEXT NOT NULL)") {
//                execute()
//            }
        },
        upgrade = { _, _, _ -> }
    )
    val dbManager = createDatabaseManager(dbConfig)

    val connection = dbManager.createMultiThreadedConnection()
    connection.withTransaction {
        it.withStatement("INSERT INTO demo (name) VALUES (?)") {
            bindString(1, "Hello, World!")
            executeInsert()
        }
    }
//    var myArray = ArrayList<String>()
    connection.withStatement("SELECT * FROM demo") {
        query().columnCount
        while (query().next()) {

        }
//        query().forEachRow {
//            val id = it.longForColumnIndex(0)
//            val value = it.stringForColumnIndex(1)
//            println("Row: id=$id, value=$value")
//        }
    }

    connection.close()
}