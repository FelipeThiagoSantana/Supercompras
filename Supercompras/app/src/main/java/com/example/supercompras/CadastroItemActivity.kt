package com.example.supercompras

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.supercompras.model.Item
import com.example.supercompras.repository.ListaRepository

class CadastroItemActivity : AppCompatActivity() {

    private var listaId: Int = -1
    private lateinit var listaView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val nomesItens = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_item)

        listaId = intent.getIntExtra("listaId", -1)
        val lista = ListaRepository.getListaById(listaId)

        if (lista == null) {
            Toast.makeText(this, "Lista não encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        listaView = findViewById(R.id.listaDeItens)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nomesItens)
        listaView.adapter = adapter

        val etNomeItem = findViewById<EditText>(R.id.etNomeItem)
        val etQuantidadeItem = findViewById<EditText>(R.id.etQuantidadeItem)
        val etValorItem = findViewById<EditText>(R.id.etValorItem)
        val btnAdicionarItem = findViewById<Button>(R.id.btnAdicionarItem)

        btnAdicionarItem.setOnClickListener {

            val nome = etNomeItem.text.toString()
            val quantidade = etQuantidadeItem.text.toString().toIntOrNull() ?: 1
            val valor = etValorItem.text.toString().toDoubleOrNull() ?: 0.0

            if (nome.isBlank()) {
                Toast.makeText(this, "Digite o nome do item", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = Item(
                nome = nome,
                quantidade = quantidade,
                valor = valor
            )

            lista.itens.add(item)
            ListaRepository.notificarAlteracaoDeDados()

            nomesItens.add("${item.nome} (x${item.quantidade})")
            adapter.notifyDataSetChanged()

            etNomeItem.text.clear()
            etQuantidadeItem.text.clear()
            etValorItem.text.clear()
        }

        atualizarLista(lista)
    }

    private fun atualizarLista(lista: com.example.supercompras.model.ListaSupermercado) {
        nomesItens.clear()
        lista.itens.forEach {
            nomesItens.add("${it.nome} (x${it.quantidade})")
        }
        adapter.notifyDataSetChanged()
    }
}
