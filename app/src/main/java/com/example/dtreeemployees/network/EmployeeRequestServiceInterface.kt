package com.example.dtreeemployees.network

import com.example.dtreeemployees.data.Employee
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface EmployeeRequestServiceInterface {
    @Headers("Content-Type: application/json","x-apikey: 63722be4c890f30a8fd1f370","cache-control: no-cache")
    @GET("group-1")
    fun getEmployees(): Call<List<Employee>>

}