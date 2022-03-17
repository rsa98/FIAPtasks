package br.com.fiap.loginfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import br.com.fiap.loginfirebase.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivitySignUpBinding

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
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configurar ActionBar / Ativar o botão de voltar
        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //Configurar progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor espere")
        progressDialog.setMessage("Criando uma nova conta...")
        progressDialog.setCanceledOnTouchOutside(false)

        //Iniciar autenticação Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        //Ao clicar em 'inscreva-se"
        binding.cadastroBtn.setOnClickListener{
            //validar dados
            validateData()
        }


    }

    private fun validateData() {
        //Obter dados
        email = binding.emailEt.text.toString().trim()
        senha = binding.senhaET.text.toString().trim()

        //Validar daddos
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //Email inválido
            binding.emailEt.error = "Formato de email inválido"
        }
        else if (TextUtils.isEmpty(senha)){
            binding.senhaET.error = "Por favor, digite a senha para entrar"
        }
        else if (senha.length <6){
            //A senha é menor do que 6
            binding.senhaET.error = "A senha deve ter pelo menos 6 caracteres"
        }
        else {
            //dados validados, prosseguir com o cadastro
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //Mostrar progresso
        progressDialog.show()

        //conta cadastrada
        firebaseAuth.createUserWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                //cadastrado com sucesso
                progressDialog.dismiss()
                //obter atual usuário
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "A conta foi criada com o e-mail $email", Toast.LENGTH_SHORT).show()

                //abrir perfil
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener { e->
                //falha no cadastro
                progressDialog.dismiss()
                Toast.makeText(this, "A criação da nova conta falhou devido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
//ast.makeText(this, "A criação da nova conta falhou devido a ${e.message}", Toast.LENGTH_SHORT).show()
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }



}