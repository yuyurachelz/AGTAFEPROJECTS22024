package com.example.agtafeprojects22024

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agtafeprojects22024.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //create menus
    private val menuADD = Menu.FIRST+1
    private val menuEDIT = Menu.FIRST+2
    private val menuDELETE = Menu.FIRST+3

    //create a array for employees and ids
    private var employeeList:MutableList<Employee> = arrayListOf()
    private val idList:MutableList<Int> = arrayListOf()

    //create DBHandler class object
    private val dbh = DBHandler(this,null)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //register for context menu
        registerForContextMenu(binding.lstEmployees)
        //load the records in the adapter
        initAdapter()

    }

    private fun initAdapter() {
        try {
            //clear existing values
            employeeList.clear()
            idList.clear()
            //get all the employees
            for(employee:Employee in dbh.getAllEmployees()){
                employeeList.add(employee)
                idList.add(employee.id)
            }
            //create a custome adapter
            val adp = CustomAdapter(this, employeeList as ArrayList<Employee>)
            //assign adapter to listview
            binding.lstEmployees.adapter = adp
        }catch (ex:Exception){
            //show error message
            Toast.makeText(this,"Something has gone wrong: ${ex.message.toString()}",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(Menu.NONE,menuADD,Menu.NONE,"ADD")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //create intent to go to addedit activity
        val goAddEdit = Intent(this, AddEdit::class.java)
        startActivity(goAddEdit)
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.add(Menu.NONE,menuEDIT,Menu.NONE,"EDIT")
        menu.add(Menu.NONE,menuDELETE,Menu.NONE,"DELETE")
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        //adapter context menu info
        val info = item.menuInfo as AdapterContextMenuInfo
        // code for edit and delete
        when(item.itemId){
            menuEDIT->{
                //create an intent and start activity with data pass on
                val addEdit = Intent(this,AddEdit::class.java)
                addEdit.putExtra("ID",idList[info.position])
                startActivity(addEdit)
            }
            menuDELETE->{
                //call delete function of DBHandler
                dbh.deletEmployee(idList[info.position])
                //refresh the list view
                initAdapter()

            }
        }
        return super.onContextItemSelected(item)
    }

}