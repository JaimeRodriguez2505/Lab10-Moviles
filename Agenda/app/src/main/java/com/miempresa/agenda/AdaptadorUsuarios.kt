import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable
import android.widget.ImageButton
import android.content.Intent
import android.widget.Toast
import com.miempresa.agenda.R
import com.miempresa.agenda.RegistroUsuarios
import com.miempresa.agenda.Usuario
import com.miempresa.agenda.UsuarioRepositorio

class AdaptadorUsuarios(private val listaUsuarios: ArrayList<Usuario>) :
    RecyclerView.Adapter<AdaptadorUsuarios.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.lblNombre)
        val correoTextView: TextView = itemView.findViewById(R.id.lblCorreo)
        val telefonoTextView: TextView = itemView.findViewById(R.id.lblTelefono)
        val observacionesTextView: TextView = itemView.findViewById(R.id.lblObservaciones)
        val eliminarButton: ImageButton = itemView.findViewById(R.id.btnEliminar)
        val editarButton: ImageButton = itemView.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.vista_usuario, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listaUsuarios[position]
        holder.nombreTextView.text = user.nombre
        holder.correoTextView.text = user.correo
        holder.telefonoTextView.text = user.telefono
        holder.observacionesTextView.text = user.observaciones

        holder.eliminarButton.setOnClickListener {
            val context = holder.itemView.context
            val confirmDialog = androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Eliminar Contacto")
                .setMessage("¿Deseas eliminar este usuario?")
                .setPositiveButton("Eliminar") { dialog, which ->
                    val userRepo = UsuarioRepositorio()
                    userRepo.borrar(user.id!!)
                    listaUsuarios.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount)
                    Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .create()
            confirmDialog.show()
        }

        holder.editarButton.setOnClickListener {
            val context = holder.itemView.context
            val userRepo = UsuarioRepositorio()
            val usuario = userRepo.leer(user.id!!)

            val editarUsuario = Intent(context, RegistroUsuarios::class.java)
            editarUsuario.putExtra("usuario", usuario as Serializable)
            context.startActivity(editarUsuario)
        }
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }
}
