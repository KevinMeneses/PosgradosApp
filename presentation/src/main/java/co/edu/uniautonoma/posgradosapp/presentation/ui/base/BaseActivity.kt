package co.edu.uniautonoma.posgradosapp.presentation.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = ProgressDialog(this)
        dialog!!.setMessage("Cargando...")
        dialog!!.setCancelable(true)
        dialog!!.isIndeterminate = true
    }

    protected fun mostrarDialog() {
        if (!dialog!!.isShowing) {
            dialog!!.show()
        }
    }

    protected fun ocultarDialog() {
        if (dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }
}