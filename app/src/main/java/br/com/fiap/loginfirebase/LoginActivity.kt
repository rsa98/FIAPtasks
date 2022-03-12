package br.com.fiap.loginfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import br.com.fiap.loginfirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding:ActivityLoginBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //Autenticação Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var senha = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuração actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        //Configuração progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor espere")
        progressDialog.setMessage("Entrando...")
        progressDialog.setCanceledOnTouchOutside(false)

        //Iniciar firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //Abrir a Activity de cadastro
        binding.semConta.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        //Iniciar o Login
        binding.loginBtn.setOnClickListener{

            validateData()

        }
    }

    private fun validateData() {
        //Obter dados
        email = binding.emailEt.text.toString().trim()
        senha = binding.senhaEt.text.toString().trim()

        //Validar dados
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //Email inválido
            binding.emailEt.error = "Formato de email inválido"
        }
        else if (TextUtils.isEmpty(senha)){
            "Senha não informada"
            binding.senhaEt.error = "Por favor, digite a senha para entrar"
        }
        else {
            //Iniciando o login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                //Logado com sucesso
                progressDialog.dismiss()
                //Obter informações do usuário
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Você entrou como $email", Toast.LENGTH_SHORT).show()

                //Abrir perfil
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                //Erro no login
                progressDialog.dismiss()
                Toast.makeText(this, "Falha de login devido a ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    private fun checkUser() {
       val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}