package com.example.dtreeemployees.employeviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dtreeemployees.data.Employee
import com.example.dtreeemployees.network.EmployeeRequestServiceInterface
import com.example.dtreeemployees.network.RetrofitApiInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeViewModel: ViewModel() {
    lateinit var liveDataList: MutableLiveData<List<Employee>>

    init {
        liveDataList = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<List<Employee>> {
        return liveDataList
    }

    fun apiRequest(){
        val retrofit= RetrofitApiInstance.getRetrofitApiInstance()
        var apiService = retrofit.create(EmployeeRequestServiceInterface::class.java)
        val call = apiService.getEmployees()
        call.enqueue(object : Callback<List<Employee>> {
            override fun onResponse(
                call: Call<List<Employee>>,
                response: Response<List<Employee>>
            ) {
                liveDataList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                liveDataList.postValue(null)
            }

        })
    }
}