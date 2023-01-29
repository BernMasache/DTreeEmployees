package com.example.dtreeemployees.data

data class Employee (val _id:String, val NAME:String, var SURNAME:String, var AGE:Int, var CITY:String)

data class EmployeeResponse(val code: Int, val meta:String,val data:Employee)