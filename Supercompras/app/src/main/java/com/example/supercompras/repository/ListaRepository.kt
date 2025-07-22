package com.example.supercompras.repository

import com.example.supercompras.model.ListaSupermercado
import com.example.supercompras.model.Item

object ListaRepository {
    val listas = mutableListOf<ListaSupermercado>()
    private var idCounter = 1

    fun adicionarLista(lista: ListaSupermercado) {
        listas.add(lista.copy(id = idCounter++))
    }

    fun getListaById(id: Int): ListaSupermercado? {
        return listas.find { it.id == id }
    }
}