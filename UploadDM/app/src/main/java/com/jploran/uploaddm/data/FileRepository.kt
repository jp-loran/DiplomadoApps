package com.jploran.uploaddm.data

import com.jploran.uploaddm.data.remote.FileApi
import com.jploran.uploaddm.data.remote.RetrofitHelper
import com.jploran.uploaddm.data.remote.model.UploadResponseDto
import okhttp3.MultipartBody
import retrofit2.Call

class FileRepository {

    private val fileApi: FileApi = RetrofitHelper.getRetrofit().create(FileApi::class.java)
    fun uploadImage(imagePart: MultipartBody.Part): Call<UploadResponseDto> = fileApi.uploadImage(imagePart)
}