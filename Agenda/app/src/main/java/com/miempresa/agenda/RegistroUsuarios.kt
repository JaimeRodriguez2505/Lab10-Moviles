package com.miempresa.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registro_usuarios.*

class RegistroUsuarios : AppCompatActivity() {

    private var edita: Boolean = false
    private var id: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuarios)

        btnRegistrar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val correo = txtCorreo.text.toString()
            val telefono = txtTelefono.text.toString()
            val observaciones = txtObservaciones.text.toString()

            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || observaciones.isEmpty()) {
                Toast.makeText(this, "Falta completar campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val usuariorepo = UsuarioRepositorio()

            if (edita) {
                usuariorepo.actualizar(id, nombre, correo, telefono, observaciones)
            } else {
                usuariorepo.crear(nombre, correo, telefono, observaciones)
            }

            finish()
        }

        val recibidos: Bundle? = intent.extras
        if (recibidos != null) {
            val usuario = recibidos.getSerializable("usuario") as? Usuario
            if (usuario != null) {
                edita = true
                id = usuario.id ?: -1
                txtNombre.setText(usuario.nombre)
                txtCorreo.setText(usuario.correo)
                txtTelefono.setText(usuario.telefono)
                txtObservaciones.setText(usuario.observaciones)
            }
        }
    }
}
