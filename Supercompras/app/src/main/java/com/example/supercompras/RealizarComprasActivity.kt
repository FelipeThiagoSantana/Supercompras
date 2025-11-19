package com.example.supercompras

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.supercompras.model.ListaSupermercado
import com.example.supercompras.repository.ListaRepository

class RealizarComprasActivity : AppCompatActivity() {

    private lateinit var lista: ListaSupermercado
    private lateinit var layoutItens: LinearLayout
    private lateinit var tvTotalGasto: TextView
    private lateinit var tvSaldoRestante: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_compras)

        val listaId = intent.getIntExtra("listaId", -1)
        lista = ListaRepository.getListaById(listaId)
            ?: run {
                Toast.makeText(this, "Lista não encontrada", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

        layoutItens = findViewById(R.id.layoutItens)
        tvTotalGasto = findViewById(R.id.tvTotalGasto)
        tvSaldoRestante = findViewById(R.id.tvSaldoRestante)

        findViewById<Button>(R.id.btnAdicionarMaisItens).setOnClickListener {
            val intent = Intent(this, CadastroItemActivity::class.java)
            intent.putExtra("listaId", lista.id)
            startActivity(intent)
        }

        renderizarItens()
        atualizarResumo()
    }

    override fun onResume() {
        super.onResume()
        renderizarItens()
        atualizarResumo()
    }

    private fun renderizarItens() {
        layoutItens.removeAllViews()

        for (item in lista.itens) {
            val itemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 8, 0, 8)
            }

            val tvNome = TextView(this).apply {
                text = item.nome
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            // CAMPO VALOR UNITÁRIO
            val etValor = EditText(this).apply {
                hint = "R$"
                setText(item.valor.toString())
                inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        val novoValor = s.toString().toDoubleOrNull() ?: 0.0
                        item.valor = novoValor
                        atualizarResumo()
                        ListaRepository.notificarAlteracaoDeDados()
                    }
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }

            // CAMPO QUANTIDADE
            val etQuantidade = EditText(this).apply {
                hint = "Qtd"
                setText(item.quantidade.toString())
                inputType = android.text.InputType.TYPE_CLASS_NUMBER
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        val novaQtd = s.toString().toIntOrNull() ?: 1
                        item.quantidade = novaQtd
                        atualizarResumo()
                        ListaRepository.notificarAlteracaoDeDados()
                    }
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }

            itemLayout.addView(tvNome)
            itemLayout.addView(etValor)
            itemLayout.addView(etQuantidade)

            layoutItens.addView(itemLayout)
        }
    }

    private fun atualizarResumo() {
        tvTotalGasto.text = "Total Gasto: R$ %.2f".format(lista.totalGasto())
        tvSaldoRestante.text = "Saldo Restante: R$ %.2f".format(lista.saldoRestante())
    }
}
