package br.com.empresa.fiapenterpriseconnection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     val button: Button = findViewById(R.id.entrarButton)
        button.setOnClickListener{

            IrParaSegundaTela()
        }
    }
    private fun IrParaSegundaTela(){

        val segundaTela = Intent(this,Tela2Activity::class.java)
        startActivity(segundaTela)
    }
}