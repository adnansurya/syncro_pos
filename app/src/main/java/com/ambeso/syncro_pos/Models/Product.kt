package com.ambeso.syncro_pos.Models

class Product {

//    var id: Int = 0
    var type: String? = null
    var category: String? = null
    var uxid: String? = null
    var code: String? = null
    var name: String? = null
    var unit: String? = null
    var basePrice: Int = 0
    var sellPrice: Int = 0
    var stockAmount: Int = 0


//    constructor(id: Int, type: String, category: String, uxid: String, code: String, name: String,
//                unit: String, base_price: Int, sell_price: Int, stock: Int) {
//        this.id = id
//        this.type = type
//        this.category = category
//        this.uxid = uxid
//        this.code = code
//        this.name = name
//        this.unit = unit
//        this.basePrice = base_price
//        this.sellPrice = sell_price
//        this.stockAmount = stock
//    }

    constructor( type: String, category: String, uxid: String, code: String, name: String,
                unit: String, base_price: Int, sell_price: Int, stock: Int) {

        this.type = type
        this.category = category
        this.uxid = uxid
        this.code = code
        this.name = name
        this.unit = unit
        this.basePrice = base_price
        this.sellPrice = sell_price
        this.stockAmount = stock
    }
}