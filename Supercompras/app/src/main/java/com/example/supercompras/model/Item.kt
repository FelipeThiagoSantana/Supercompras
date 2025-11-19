package com.example.supercompras.model

data class Item(
    val nome: String,
    var valor: Double,
    var quantidade: Int
){
    val valorTotal: Double
        get() = valor * quantidade
}
