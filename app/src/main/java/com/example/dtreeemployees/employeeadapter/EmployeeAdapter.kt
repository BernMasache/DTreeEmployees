package com.example.dtreeemployees.employeeadapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dtreeemployees.MainActivity
import com.example.dtreeemployees.R
import com.example.dtreeemployees.data.Employee

class EmployeeAdapter(var activity: Activity, var onItemClickLister: EmployeeAdapter.OnItemClickLister, var onItemLongClickLister:OnItemLongClickLister):
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>(){
    private var employeeList : List<Employee>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_layout,parent,false)
        return EmployeeViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        var employeeData = employeeList?.get(position)
        holder.username.text = employeeData?.NAME+ " " + employeeData?.SURNAME
        holder.age.text = "Age : "+employeeData?.AGE.toString()
        holder.city.text = "Location : "+employeeData?.CITY
        holder?.bindView(employeeList?.get(position)!!,onItemClickLister,onItemLongClickLister)
    }

    override fun getItemCount(): Int {
        if (employeeList==null) return 0
        else return employeeList?.size!!
    }

    class EmployeeViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        var username: TextView = itemView.findViewById<TextView>(R.id.username)
        var age: TextView = itemView.findViewById<TextView>(R.id.age)
        var city: TextView = itemView.findViewById<TextView>(R.id.city)

        @SuppressLint("SetTextI18n")
        fun bindView(
            data: Employee,
            onItemClickLister: OnItemClickLister,
            onItemLongClickLister: OnItemLongClickLister
        ){
            itemView.setOnClickListener{
                onItemClickLister.onEmployeeClick(data)
            }

            itemView.setOnLongClickListener{
                onItemLongClickLister.onEmployeeLongClick(data)
            }

        }
    }

    interface OnItemClickLister {
        fun onEmployeeClick( employee: Employee)
    }
    interface OnItemLongClickLister {
        fun onEmployeeLongClick(employee: Employee):Boolean
    }

    fun setFilteredEmployeesList(filteredEmployeeList: ArrayList<Employee>){
        this.employeeList= filteredEmployeeList
        notifyDataSetChanged()
    }

    fun setEmployeesList(userList: List<Employee>){
        this.employeeList= userList
    }
}