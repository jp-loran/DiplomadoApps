package com.jploran.uploaddm.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jploran.uploaddm.data.FileRepository
import com.jploran.uploaddm.data.remote.ProgressRequestBody
import com.jploran.uploaddm.data.remote.model.UploadResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainViewModel(private val repository: FileRepository= FileRepository()): ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress
    fun uploadImage(file: File){
        viewModelScope.launch (Dispatchers.IO) {
            val requestBody = file.asRequestBody()
            val progressRequestBody = ProgressRequestBody(requestBody){progress->
                _progress.postValue(progress)
            }
            val imagePart = MultipartBody.Part.createFormData("image", file.name, progressRequestBody)

            val call: Call<UploadResponseDto> = repository.uploadImage(imagePart)

            call.enqueue(object: Callback<UploadResponseDto>{
                override fun onResponse(
                    call: Call<UploadResponseDto>,
                    response: Response<UploadResponseDto>
                ) {
                    Log.d("LOGSAPP", "Respuesta: ${response.body()?.message}")
                    _message.postValue(response.body()?.message)
                }

                override fun onFailure(call: Call<UploadResponseDto>, t: Throwable) {
                    //
                }

            })
        }
    }


}