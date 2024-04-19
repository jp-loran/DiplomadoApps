package com.jploran.uploaddm.data.remote

import com.jploran.uploaddm.data.remote.model.UploadResponseDto
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Streaming

interface FileApi {
    @Multipart
    @POST("cm/games/upload.php")
    @Streaming
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<UploadResponseDto>
}