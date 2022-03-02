package br.com.empresa.fiapenterpriseconnection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val button: Button = findViewById(R.id.entrarButton)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener{

            irParaSegundaTela()

        }
    }

    private fun irParaSegundaTela(){

     val segundaTela = Intent(this, SegundaTela::class.java)
    startActivity(segundaTela)

    }
}