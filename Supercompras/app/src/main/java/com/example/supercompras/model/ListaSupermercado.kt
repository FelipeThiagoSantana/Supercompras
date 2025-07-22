package com.example.supercompras.model

data class ListaSupermercado(
    val id: Int,
    val data: String,
    val valorLimite: Double,
    val itens: MutableList<Item> = mutableListOf()
) {
    fun totalGasto(): Double = itens.sumOf { it.valor }
    fun saldoRestante(): Double = valorLimite - totalGasto()
}
