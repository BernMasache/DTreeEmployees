package com.example.dtreeemployees

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isNotEmpty
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dtreeemployees.data.Employee
import com.example.dtreeemployees.employeeadapter.EmployeeAdapter
import com.example.dtreeemployees.employeviewmodel.EmployeeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    lateinit var employeeAdapter: EmployeeAdapter
    private var employee_recycler_view:RecyclerView?=null
    private var filteredEmployeeList:List<Employee>?=null

    var  searchEmployee:SearchView?=null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchEmployee = findViewById<SearchView>(R.id.search_employee)
        searchEmployee?.clearFocus()
        searchEmployee?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchEmployeeFunction(newText)
                return true
            }
        })
        employee_recycler_view = findViewById<RecyclerView>(R.id.employee_recycler_view)
        initRecyclerView()
        initViewModel()

    }
//searching for a march of an employ rom the already populated employees model view
    private fun searchEmployeeFunction(searchText: String?) {

        if (searchText!!.isNotEmpty()){
            val text = searchText!!.lowercase()

            val viewModel:EmployeeViewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
            viewModel.getLiveDataObserver().observe(this) {
                if (it != null) {
                    val users = ArrayList<Employee>()
                    for (item in it){

                        if (item.NAME.lowercase().contains(text.lowercase())||item.SURNAME.lowercase().contains(text.lowercase())){
                            users.add(item)
                        }else{

                        }
                    }
                    employeeAdapter.setFilteredEmployeesList(users)
                } else {
                    EmployeeViewModel().apiRequest()
                    initRecyclerView()
                    initViewModel()

                }
            }
        }else{
            EmployeeViewModel().apiRequest()
            initRecyclerView()
            initViewModel()
        }

    }

    //    initialising the recyclerview
    private fun  initRecyclerView(){
        employee_recycler_view?.layoutManager = LinearLayoutManager(this)
        employeeAdapter = EmployeeAdapter(this,listener(),  listenerEmployLongClickListener())
        employee_recycler_view?.adapter = employeeAdapter
    }

//    initialising the recyclerview
    private fun initViewModel(){

        val viewModel:EmployeeViewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
        viewModel.getLiveDataObserver().observe(this) {
            if (it != null) {
                employeeAdapter.setEmployeesList(it)
                employeeAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(this, "No employees found", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.apiRequest()
    }
//    implementing the onclick listener from the adapter class to detect a clicked employ from the
//    recyclerview and display detail in bottom dialogue box
    private fun listener():EmployeeAdapter.OnItemClickLister{
        return object : EmployeeAdapter.OnItemClickLister {
            override fun onEmployeeClick(employee: Employee) {
                openBottomDialogue(employee)
            }
        }
    }

    fun openBottomDialogue(employee: Employee){
        val dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.message, null)
        val textHeader = view.findViewById<TextView>(R.id.message_box_header)
        val textContent = view.findViewById<TextView>(R.id.message_box_content)
        textHeader.text = employee.NAME+" "+employee.SURNAME
        textContent.text = "A "+employee.AGE+" year old and lives in "+employee.CITY
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(view)
        dialog.show()
    }

    fun openAlertDialogue(employee: Employee){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete employee!")
        builder.setMessage("Are you sure want to delete "+employee.NAME+" "+employee.SURNAME+"?")

        builder.setPositiveButton("Yes") { dialog, which ->
            val viewModel:EmployeeViewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
            viewModel.getDeleteEmployeeDataObserver().observe(this) {

                if (it == null) {

                    Toast.makeText(this, "Failed to delete employee", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(applicationContext,
                        "Deleted successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

//            viewModel.deleteEmployeeRequest(employee._id)

        }

        builder.setNegativeButton("No") { dialog, which ->
            Toast.makeText(applicationContext,
                "Cancelled", Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }
//    on long click listener
    private fun listenerEmployLongClickListener():EmployeeAdapter.OnItemLongClickLister{
        return object : EmployeeAdapter.OnItemLongClickLister{
            override fun onEmployeeLongClick(employee: Employee) :Boolean{

                openAlertDialogue(employee)
                return true
            }

        }
    }
}