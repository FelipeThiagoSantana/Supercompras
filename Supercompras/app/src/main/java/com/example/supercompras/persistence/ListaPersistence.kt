package com.example.supercompras.persistence

import android.content.Context
import android.content.SharedPreferences
import com.example.supercompras.model.ListaSupermercado
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ListaPersistence {

    private const val PREFS_NAME = "SuperComprasPrefs"
    private const val KEY_LISTAS = "listas_supermercado"

    private lateinit var preferences: SharedPreferences
    private val gson = Gson()

    // Este método precisa ser chamado uma vez quando o app inicia
    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Salva a lista de compras no SharedPreferences.
     * A lista é convertida para uma String JSON antes de ser salva.
     */
    fun salvarListas(listas: List<ListaSupermercado>) {
        val editor = preferences.edit()
        val jsonString = gson.toJson(listas) // Converte a lista para JSON
        editor.putString(KEY_LISTAS, jsonString)
        editor.apply() // Salva de forma assíncrona
    }

    /**
     * Carrega a lista de compras do SharedPreferences.
     * A String JSON é lida e convertida de volta para uma lista de objetos.
     */
    fun carregarListas(): MutableList<ListaSupermercado> {
        val jsonString = preferences.getString(KEY_LISTAS, null)
        if (jsonString != null) {
            // Define o tipo de dado que o Gson deve esperar (uma lista de ListaSupermercado)
            val type = object : TypeToken<MutableList<ListaSupermercado>>() {}.type
            return gson.fromJson(jsonString, type) // Converte JSON para a lista de objetos
        }
        return mutableListOf() // Retorna uma lista vazia se não houver nada salvo
    }
}