package com.example.dtreeemployees.employeeadapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dtreeemployees.R
import com.example.dtreeemployees.data.Employee

class EmployeeAdapter(var activity: Activity):
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
    private var employeeList : List<Employee>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_layout,parent,false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employeeList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        if (employeeList==null) return 0
        else return employeeList?.size!!
    }

    class EmployeeViewHolder (view: View): RecyclerView.ViewHolder(view){
        var username: TextView = view.findViewById<TextView>(R.id.username)
        var age: TextView = view.findViewById<TextView>(R.id.age)
        var city: TextView = view.findViewById<TextView>(R.id.city)

        @SuppressLint("SetTextI18n")
        fun bind(data:Employee){
            username.text = data.NAME+ " " + data.SURNAME
            age.text = "Age : "+data.AGE.toString()
            city.text = "Location : "+data.CITY
        }
    }

    fun setFilteredEmployeesList(filteredEmployeeList: ArrayList<Employee>){
        this.employeeList= filteredEmployeeList
        notifyDataSetChanged()
    }

    fun setEmployeesList(userList: List<Employee>){
        this.employeeList= userList
    }
}