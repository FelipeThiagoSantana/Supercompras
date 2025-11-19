package com.example.supercompras

import android.app.Application
import com.example.supercompras.repository.ListaRepository

class SuperComprasApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializa o repositório quando o aplicativo é criado
        ListaRepository.init(this)
    }
}