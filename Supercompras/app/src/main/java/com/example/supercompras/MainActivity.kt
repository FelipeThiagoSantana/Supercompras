package com.example.supercompras

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supercompras.repository.ListaRepository

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val nomesListas = mutableListOf<String>()

    override fun onResume() {
        super.onResume()
        atualizarLista()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewListas)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nomesListas)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val lista = ListaRepository.listas[position]
            val intent = Intent(this, RealizarComprasActivity::class.java)
            intent.putExtra("listaId", lista.id)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnNovaLista).setOnClickListener {
            startActivity(Intent(this, CriarListaActivity::class.java))
        }
    }

    private fun atualizarLista() {
        nomesListas.clear()
        ListaRepository.listas.forEach {
            nomesListas.add("Data: ${it.data} | Limite: R$ %.2f".format(it.valorLimite))
        }
        adapter.notifyDataSetChanged()
    }
}

