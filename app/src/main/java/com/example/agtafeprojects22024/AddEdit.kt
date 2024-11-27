package com.example.agtafeprojects22024

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agtafeprojects22024.databinding.AddeditBinding

class AddEdit : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: AddeditBinding
    //create DBHandler object
    private val dbh = DBHandler(this,null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddeditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)

        //read data from MainActivity
        val extras = intent.extras
        if (extras !=null){
            val id:Int = extras.getInt("ID")
            //get employee for this id
            val employee = dbh.getEmployees(id)
            //fill edit boxes
            binding.etID.setText(employee.id.toString())
            binding.etName.setText(employee.name)
            binding.etEmail.setText(employee.email)
            binding.etMobile.setText(employee.mobile)
            binding.etAddress.setText(employee.address)
            binding.etImage.setText(employee.imageFile)
            binding.ivImage.setImageResource(this.resources.getIdentifier(employee.imageFile,"drawable","com.example.agtafeprojects22024"))

        }
    }

    override fun onClick(btn: View) {
        when(btn.id){
            R.id.btnSave->{
                //will return zero if no value in edit text
                val eid = binding.etID.text.toString().toIntOrNull()?:0
                if (eid == 0){
                    addEmployee()
                }else{
                    updateEmployee(eid)
                }
            }
            R.id.btnCancel-> {
                goMain()
            }
        }
    }

    private fun updateEmployee(eid: Int) {
        val employee = dbh.getEmployees(eid)
        //update employee object with revised values
        employee.name = binding.etName.text.toString()
        employee.mobile = binding.etMobile.text.toString()
        employee.address = binding.etAddress.text.toString()
        employee.email = binding.etEmail.text.toString()
        employee.imageFile = binding.etImage.text.toString()
        //call dbhandler update function
        dbh.updateEmployee(employee)
        //display confirmation
        Toast.makeText(this,"Employee updated", Toast.LENGTH_LONG).show()
        goMain()

    }

    private fun addEmployee() {
        //read values from edit boxes and assign to employee object
        val employee = Employee()
        employee.name = binding.etName.text.toString()
        employee.mobile = binding.etMobile.text.toString()
        employee.address = binding.etAddress.text.toString()
        employee.imageFile = binding.etImage.text.toString()
        employee.email = binding.etEmail.text.toString()
        //dbhandler function to add this employee
        dbh.addEmployee(employee)
        //display confirmation
        Toast.makeText(this ,"New Employee saved",Toast.LENGTH_LONG).show()
        goMain()
    }

    private fun goMain() {
        //create intent to go to Main activity
        val goMain = Intent(this, MainActivity::class.java)
        startActivity(goMain)
    }

}