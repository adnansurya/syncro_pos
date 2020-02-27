package com.ambeso.syncro_pos.Models

class Product (

    var type: String,
    var category: String,
    var uxid: String?,
    var code: String,
    var name: String,
    var unit: String?,
    var basePrice: Int,
    var sellPrice: Int,
    var stockAmount: Int

)