package saymobile.company.operationsmanagementapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import saymobile.company.operationsmanagementapp.R

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        login_button.setOnClickListener {
            loginButtonClicked()
        }
    }

    override fun onStart() {
        super.onStart()

        //Check if this person is already logged in. If so then it goes straight to the dashboard
        if (mAuth!!.currentUser != null) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        login_progressBar.visibility = View.GONE
    }


    fun loginButtonClicked() {
        if (email_entryField.text.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "No hay direccion de correo electronico",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (password_entryField.text.isEmpty()) {
            Toast.makeText(applicationContext, "No hay contrasena", Toast.LENGTH_SHORT).show()
            return
        }

        loginError_message.visibility = View.GONE
        login_progressBar.visibility = View.VISIBLE

        mAuth!!.signInWithEmailAndPassword(
            email_entryField.text.toString(),
            password_entryField.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                login_progressBar.visibility = View.GONE
                if (!task.isSuccessful) {
                    loginError_message.visibility = View.VISIBLE
                } else {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
    }
}


