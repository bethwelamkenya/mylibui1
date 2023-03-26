package databases

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.bethwelamkenya.*
import kotlinx.cinterop.toKString
import platform.posix.getenv
import sha256.sha256

class DatabaseAdapter {
    private val userHome = getenv("USERPROFILE")?.toKString() ?: ""
    private val dbPath = "$userHome\\AppData\\Roaming\\mylibui1\\Databases\\church.db"
    private val driver = NativeSqliteDriver(Church.Schema, dbPath)
    private val database = Church(driver)

    fun insertMember(member: Member) {
        database.churchQueries.insertMember(
            name = member.name,
            email = member.email,
            reg_no = member.reg_no,
            number = member.number,
            school = member.school,
            year = member.year,
            department = member.department,
            residence = member.residence
        )
    }

    fun insertAdmin(admin: Admin) {
        database.churchQueries.insertAdmin(
            name = admin.name,
            email = admin.email,
            number = admin.number,
            user_name = admin.user_name,
            password = sha256(admin.password!!.encodeToByteArray()).toString(),
            security = admin.security,
            answer = admin.answer
        )
    }

    fun insertAttendance(attendance: Attendance) {
        database.churchQueries.insertAttendance(
            id = attendance.id,
            name = attendance.name,
            number = attendance.number,
//            residence = attendance.residence,
            date = attendance.date,
            status = attendance.status
        )
    }

    fun insertDate(date: Date) {
        database.churchQueries.insertDate(
            date = date.date
        )
    }

    fun getAllMembers(): List<Member> {
        return database.churchQueries.selectAllMembers().executeAsList()
    }

    fun getMemberByName(name: String): List<Member> {
        return database.churchQueries.selectMemberByName(name).executeAsList()
    }

    fun getMemberByID(id: Long): List<Member> {
        return database.churchQueries.selectMemberByID(id).executeAsList()
    }

    fun getAllAdmins(): List<Admin> {
        return database.churchQueries.selectAllAdmins().executeAsList()
    }

    fun getAdminByName(name: String): List<Admin> {
        return database.churchQueries.selectAdminByName(name).executeAsList()
    }

    fun getAdminByUserName(userName: String): List<Admin> {
        return database.churchQueries.selectAdminByUserName(userName).executeAsList()
    }

    fun getAdminByID(id: Long): List<Admin> {
        return database.churchQueries.selectAdminByID(id).executeAsList()
    }

    fun getAdminByUSerNameAndPassword(userName: String, password: String): List<Admin> {
        return database.churchQueries.selectAdminByUserNameAndPassword(userName, password).executeAsList()
    }

    fun getAllAttendances(): List<Attendance> {
        return database.churchQueries.selectAllAttendances().executeAsList()
    }

    fun getAttendanceByName(name: String): List<Attendance> {
        return database.churchQueries.selectAttendanceByName(name).executeAsList()
    }

    fun getAttendanceByID(id: Long): List<Attendance> {
        return database.churchQueries.selectAttendanceByID(id).executeAsList()
    }

    fun getAttendanceByDate(date: String): List<Attendance> {
        return database.churchQueries.selectAttendanceByDate(date).executeAsList()
    }

    fun getAllDates(): List<Date> {
        return database.churchQueries.selectAllDates().executeAsList()
    }

    fun getDatesByDate(date: String): List<Date> {
        return database.churchQueries.selectDateByDate(date).executeAsList()
    }

    fun updateMember(member: Member) {
        database.churchQueries.updateMember(
            id = member.id,
            name = member.name,
            email = member.email,
            reg_no = member.reg_no,
            number = member.number,
            school = member.school,
            year = member.year,
            department = member.department,
            residence = member.residence
        )
    }

    fun updateAdmin(admin: Admin) {
        database.churchQueries.updateAdmin(
            id = admin.id,
            name = admin.name,
            email = admin.email,
            number = admin.number,
            user_name = admin.user_name,
            password = sha256(admin.password!!.encodeToByteArray()).toString(),
            security = admin.security,
            answer = admin.answer
        )
    }

    fun updateResidence(attendance: Attendance) {
        database.churchQueries.updateAttendance(
            id = attendance.id,
            name = attendance.name,
            number = attendance.number,
            residence = attendance.residence,
            date = attendance.date,
            status = attendance.status,
            attendance_id = attendance.attendance_id
        )
    }

    fun updateDate(date: Date) {
        database.churchQueries.updateDate(
            id = date.id,
            date = date.date,
        )
    }

    fun deleteMemberByID(id: Long) {
        database.churchQueries.deleteMemberByID(id)
    }

    fun deleteMemberByName(name: String) {
        database.churchQueries.deleteMemberByName(name)
    }

    fun deleteAdminByID(id: Long) {
        database.churchQueries.deleteAdminByID(id)
    }

    fun deleteAdminByName(name: String) {
        database.churchQueries.deleteAdminByName(name)
    }

    fun deleteAttendanceByID(id: Long) {
        database.churchQueries.deleteAttendanceByID(id)
    }

    fun deleteAttendanceByName(name: String) {
        database.churchQueries.deleteAttendanceByName(name)
    }

    fun deleteAttendanceByDate(date: String) {
        database.churchQueries.deleteAttendanceByDate(date)
    }

    fun deleteAttendanceByAttendanceID(id: Long) {
        database.churchQueries.deleteAttendanceByAttendanceID(id)
    }

    fun deleteDateByID(id: Long) {
        database.churchQueries.deleteDateByID(id)
    }

    fun deleteDateByDate(date: String) {
        database.churchQueries.deleteDateByDate(date)
    }

//    @OptIn(ExperimentalUnsignedTypes::class)
//    fun sha256(input: String): String {
//        val data = input.encodeToByteArray()
//        val result = UByteArray(SHA256)
////        val result = UByteArray(CC_SHA256_DIGEST_LENGTH)
//        data.usePinned {
//            result.usePinned { resultPinned ->
//                CC_SHA256(it.addressOf(0), data.size.toULong(), resultPinned.addressOf(0))
//            }
//        }
//        return result.joinToString("") { it.toString(16).padStart(2, '0') }
//    }
}