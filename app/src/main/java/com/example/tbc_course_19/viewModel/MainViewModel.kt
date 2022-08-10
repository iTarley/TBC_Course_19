package com.example.tbc_course_19.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_course_19.models.ModelClass
import com.example.tbc_course_19.models.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _apiResponse = MutableStateFlow<Resource>(Resource.Loading)
    val apiResponse: StateFlow<Resource> = _apiResponse


    fun getContent(){
        viewModelScope.launch {
            val response = RetrofitClient.getInformation().getInfo()
            val recyclerList = mutableListOf<ModelClass>()
            if (response.isSuccessful){
                val body: List<List<ModelClass>>? = response.body()
                body?.forEach{
                    recyclerList.addAll(it)
                }
                _apiResponse.emit(Resource.Success(recyclerList))
                Log.d("response", "getContent: $body")
            }else{
                val error = response.errorBody()
                _apiResponse.emit(Resource.Error(error?.toString() ?: "Unknown Error"))

            }
        }
    }

    sealed class Resource{
        data class Success(val data: MutableList<ModelClass>) : Resource()
        data class Error(val message:String):Resource()
        object Loading: Resource()

    }
}