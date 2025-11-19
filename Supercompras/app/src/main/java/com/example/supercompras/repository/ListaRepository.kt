package com.example.supercompras.repository

import android.content.Context
import com.example.supercompras.model.ListaSupermercado
import com.example.supercompras.persistence.ListaPersistence

object ListaRepository {

    var listas = mutableListOf<ListaSupermercado>()
        private set // Torna o 'set' privado para controlar as modificações

    private var idCounter = 1

    /**
     * Inicializa o repositório, carregando os dados salvos.
     * Deve ser chamado no início do ciclo de vida do aplicativo.
     */
    fun init(context: Context) {
        ListaPersistence.init(context.applicationContext)
        listas = ListaPersistence.carregarListas()
        // Atualiza o contador de ID para o próximo ID disponível
        idCounter = (listas.maxOfOrNull { it.id } ?: 0) + 1
    }

    /**
     * Salva o estado atual da lista de compras na persistência.
     */
    private fun salvarDados() {
        ListaPersistence.salvarListas(listas)
    }

    fun adicionarLista(lista: ListaSupermercado) {
        listas.add(lista.copy(id = idCounter++))
        salvarDados() // Salva os dados após adicionar uma nova lista
    }

    fun getListaById(id: Int): ListaSupermercado? {
        return listas.find { it.id == id }
    }

    /**
     * Função pública para permitir que as Activities notifiquem o repositório
     * que os dados foram alterados e precisam ser salvos.
     */
    fun notificarAlteracaoDeDados() {
        salvarDados()
    }
}