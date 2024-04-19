package com.jploran.p2_m6.data.remote.model

import com.google.gson.annotations.SerializedName

data class CarDto(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("image")
    var thumbnail: String? = null,

    @SerializedName("brand")
    var brand: String? = null,

    @SerializedName("model")
    var model: String? = null,

    @SerializedName("year")
    var year: String? = null
)
