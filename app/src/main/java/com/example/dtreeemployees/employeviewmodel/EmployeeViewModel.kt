package com.example.dtreeemployees.employeviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dtreeemployees.data.Employee
import com.example.dtreeemployees.data.EmployeeResponse
import com.example.dtreeemployees.network.EmployeeRequestServiceInterface
import com.example.dtreeemployees.network.RetrofitApiInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeViewModel: ViewModel() {
    var liveDataList: MutableLiveData<List<Employee>> = MutableLiveData()
    var deleteEmployeeLiveDataList: MutableLiveData<EmployeeResponse> =MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<List<Employee>> {
        return liveDataList
    }

    fun getDeleteEmployeeDataObserver(): MutableLiveData<EmployeeResponse> {
        return deleteEmployeeLiveDataList
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

    fun searchEmployeeApiRequest(searchText:String){
        val retrofit= RetrofitApiInstance.getRetrofitApiInstance()
        var apiService = retrofit.create(EmployeeRequestServiceInterface::class.java)
        val call = apiService.searchEmployee(searchText)
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

    fun deleteEmployeeRequest(employee_id:String?){
        val retrofit= RetrofitApiInstance.getRetrofitApiInstance()
        var apiService = retrofit.create(EmployeeRequestServiceInterface::class.java)
        val call = apiService.deleteEmployee(employee_id!!)
        call.enqueue(object : Callback<EmployeeResponse> {
            override fun onResponse(
                call: Call<EmployeeResponse>,
                response: Response<EmployeeResponse>
            ) {
                if (response.isSuccessful){
                    deleteEmployeeLiveDataList.postValue(response.body())
                }else{
                    deleteEmployeeLiveDataList.postValue(null)
                }
            }
            override fun onFailure(call: Call<EmployeeResponse>, t: Throwable) {
                deleteEmployeeLiveDataList.postValue(null)
            }


        })
    }
}