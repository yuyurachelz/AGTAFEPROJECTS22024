package com.example.agtafeprojects22024

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import kotlin.contracts.contract

class DBHandler(
    context: Context?,
    factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(context, DBname, factory, version) {
    companion object{
        const val DBname = "HRManager.db"
        const val version = 2
    }

    //declare table name and fields
    private val TableName = "EMPLOYEE"
    private val KeyID = "ID"
    private val KeyName = "NAME"
    private val KeyMobile = "MOBILE"
    private val KeyEmail = "EMAIL"
    private val KeyAddress = "ADDRESS"
    private val KeyImage = "IMAGE"

    override fun onCreate(db: SQLiteDatabase) {
        //create the sql statement for table
        val createTable = "CREATE TABLE $TableName ($KeyID INTEGER PRIMARY KEY AUTOINCREMENT, $KeyName TEXT, $KeyMobile TEXT, $KeyEmail TEXT, $KeyAddress TEXT, $KeyImage TEXT)"
        //execute sql
        db.execSQL(createTable)
        //add some sample data using ContentValue
        val cv = ContentValues()
        //use put method to add field values
        cv.put(KeyName, "Rachel Zhang")
        cv.put(KeyMobile, "0123456789")
        cv.put(KeyEmail, "rachelyuyuyu@gmail.com")
        cv.put(KeyAddress, "Sydney")
        cv.put(KeyImage, "first")
        //use insert method to add to table
        db.insert(TableName,null,cv)

    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        //drop the exist table and recreate database
        db.execSQL("DROP TABLE IF EXISTS $TableName")
        onCreate(db)
    }

    //get all the records from database
    fun getAllEmployees():ArrayList<Employee>{
        //declare an arrayList to fill employee objects from db
        val employeeList = ArrayList<Employee>()
        //sql query to select all employees
        val selectQuery = "SELECT * FROM $TableName"
        //get readable database
        val db = this.readableDatabase
        //run query and get the result in cursor
        val cursor = db.rawQuery(selectQuery,null)
        //move through cursor to read all records
        if (cursor.moveToFirst()){
            //loop through all possible records
            do{
                //create an employee object to put all the values
                val employee = Employee()
                employee.id = cursor.getInt(0)
                employee.name = cursor.getString(1)
                employee.mobile = cursor.getString(2)
                employee.email = cursor.getString(3)
                employee.address = cursor.getString(4)
                employee.imageFile = cursor.getString(5)
                //add to arraylist
                employeeList.add(employee)
            }while (cursor.moveToNext())
        }
        //close cursor and database
        cursor.close()
        db.close()
        //return all the employee list from db
        return employeeList
    }

    fun addEmployee(employee: Employee) {
        //get writable database
        val db = this.writableDatabase
        //create contentvalues to insert employee
        val cv = ContentValues()
        cv.put(KeyName,employee.name)
        cv.put(KeyMobile,employee.mobile)
        cv.put(KeyAddress,employee.address)
        cv.put(KeyEmail,employee.email)
        cv.put(KeyImage,employee.imageFile)
        //db insert
        db.insert(TableName,null,cv)
        //close db
        db.close()
    }

    fun deletEmployee(id: Int) {
        //det writable database
        val db = this.writableDatabase
        //delete employee
        db.delete(TableName,
            "$KeyID=?",
            arrayOf(id.toString()),
            )
        //close db
        db.close()
    }

    fun getEmployees(id: Int): Employee {
        //get readable db
        val db = this.readableDatabase

        val employee = Employee()
        //create cursor to get employee from db
        val cursor = db.query(TableName,
            arrayOf(KeyID,KeyName,KeyMobile,KeyEmail,KeyAddress,KeyImage),
            "$KeyID=?",
            arrayOf(id.toString()),
            null,
            null,
            null)
        //check cursor and read value and put these in employee
        if (cursor.moveToFirst()){
            employee.id = cursor.getInt(0)
            employee.name = cursor.getString(1)
            employee.mobile = cursor.getString(2)
            employee.email = cursor.getString(3)
            employee.address = cursor.getString(4)
            employee.imageFile = cursor.getString(5)
        }
        //close cursor and db
        cursor.close()
        db.close()
        return employee
    }

    fun updateEmployee(employee: Employee) {
        //get writeable db
        val db = this.writableDatabase
        //create content value with revised data
        val cv = ContentValues()
        cv.put(KeyName,employee.name)
        cv.put(KeyMobile,employee.mobile)
        cv.put(KeyAddress,employee.address)
        cv.put(KeyEmail,employee.email)
        cv.put(KeyImage,employee.imageFile)
        db.update(TableName,
            cv,
            "$KeyID=?",
            arrayOf(employee.id.toString()))
        //close db
        db.close()
    }
}