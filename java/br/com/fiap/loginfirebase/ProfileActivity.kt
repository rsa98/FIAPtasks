package br.com.fiap.loginfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import br.com.fiap.loginfirebase.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configurar ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        //Iniciar Autenticação Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //Sair
        binding.sairBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

    }

    private fun checkUser() {
        //Verificar se o usuário está logado ou não
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            //usuário logado, obter informações
            val email = firebaseUser.email

            binding.emailTv.text = email
        }
        else {
            //não logado, ir para Login Activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}