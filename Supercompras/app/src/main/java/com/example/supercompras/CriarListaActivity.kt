package com.example.supercompras

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.supercompras.model.ListaSupermercado
import com.example.supercompras.repository.ListaRepository

import java.util.*

class CriarListaActivity : AppCompatActivity() {

    private lateinit var etData: EditText
    private lateinit var etValorLimite: EditText
    private lateinit var btnSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_lista)

        etData = findViewById(R.id.etData)
        etValorLimite = findViewById(R.id.etValorLimite)
        btnSalvar = findViewById(R.id.btnSalvarLista)

        etData.setOnClickListener {
            mostrarDatePicker()
        }

        btnSalvar.setOnClickListener {
            salvarLista()
        }
    }

    private fun mostrarDatePicker() {
        val calendario = Calendar.getInstance()
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, y, m, d ->
            val dataFormatada = String.format("%02d/%02d/%04d", d, m + 1, y)
            etData.setText(dataFormatada)
        }, ano, mes, dia)

        datePicker.show()
    }

    private fun salvarLista() {
        val data = etData.text.toString()
        val valor = etValorLimite.text.toString().toDoubleOrNull()

        if (data.isBlank() || valor == null) {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
            return
        }

        val novaLista = ListaSupermercado(id = 0, data = data, valorLimite = valor)
        ListaRepository.adicionarLista(novaLista)

        Toast.makeText(this, "Lista criada com sucesso!", Toast.LENGTH_SHORT).show()
        finish() // volta para MainActivity
    }
}