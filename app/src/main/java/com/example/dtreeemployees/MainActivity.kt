package com.example.dtreeemployees

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dtreeemployees.data.Employee
import com.example.dtreeemployees.employeeadapter.EmployeeAdapter
import com.example.dtreeemployees.employeviewmodel.EmployeeViewModel

class MainActivity : AppCompatActivity() {
    lateinit var employeeAdapter: EmployeeAdapter
    private var employee_recycler_view:RecyclerView?=null
    private var filteredEmployeeList:List<Employee>?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val  searchEmployee = findViewById<SearchView>(R.id.search_employee)
        searchEmployee.clearFocus()
        searchEmployee.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    private fun searchEmployeeFunction(text: String?) {

        if (text!!.isNotEmpty()){
            var text = text!!.lowercase()

            val viewModel:EmployeeViewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
            viewModel.getLiveDataObserver().observe(this) {
                if (it != null) {
                    var users = ArrayList<Employee>()
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
        employeeAdapter = EmployeeAdapter(this)
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

}