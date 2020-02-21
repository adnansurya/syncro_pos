package com.ambeso.syncro_pos.Models

class Product {

    var id: Int = 0
    var productType: String? = null
    var productCategory: String? = null
    var productUxid: String? = null
    var productCode: String? = null
    var productName: String? = null
    var productUnit: String? = null
    var productBasePrice: Int = 0
    var productSellPrice: Int = 0
    var productStockAmount: Int = 0


    constructor(id: Int, type: String, category: String, uxid: String, code: String, name: String,
                unit: String, base_price: Int, sell_price: Int, stock: Int) {
        this.id = id
        this.productType = type
        this.productCategory = category
        this.productUxid = uxid
        this.productCode = code
        this.productName = name
        this.productUnit = unit
        this.productBasePrice = base_price
        this.productSellPrice = sell_price
        this.productStockAmount = stock
    }

    constructor( type: String, category: String, uxid: String, code: String, name: String,
                unit: String, base_price: Int, sell_price: Int, stock: Int) {

        this.productType = type
        this.productCategory = category
        this.productUxid = uxid
        this.productCode = code
        this.productName = name
        this.productUnit = unit
        this.productBasePrice = base_price
        this.productSellPrice = sell_price
        this.productStockAmount = stock
    }
}