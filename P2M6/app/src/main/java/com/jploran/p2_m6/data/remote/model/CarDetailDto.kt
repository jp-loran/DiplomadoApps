package com.jploran.p2_m6.data.remote.model

import com.google.gson.annotations.SerializedName

data class CarDetailDto(
    @SerializedName("id")
    var id: String?=null,

    @SerializedName("brand")
    var brand: String?=null,

    @SerializedName("model")
    var model: String?=null,

    @SerializedName("image")
    var image: String?=null,

    @SerializedName("hp")
    var hp: String?=null,

    @SerializedName("price")
    var price: String?=null,

    @SerializedName("performance")
    var performance: String?=null,

    @SerializedName("year")
    var year: String?=null,

    @SerializedName("video")
    var video: String?=null,

    @SerializedName("latitude")
    var latitude: Double=0.0,

    @SerializedName("longitude")
    var longitude: Double=0.0,
    )
