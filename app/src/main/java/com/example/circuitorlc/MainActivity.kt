package com.example.circuitorlc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

lateinit var Btn_Serie: Button
lateinit var Btn_Paralelo: Button



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Se declara la variable intent para acceder al Activity de los datos
        val intent = Intent(this,DatosActivity::class.java)

        Btn_Serie = findViewById(R.id.BtnSerie)
        Btn_Paralelo = findViewById(R.id.BtnParalelo)

        //Boton para pasar al activity de datos con el valor de variable "Serie"
        Btn_Serie.setOnClickListener {
            var Serie: String = "Serie"
            intent.putExtra("TipoCircuito",Serie)
            startActivity(intent)
        }

        //Boton para pasar al activity de datos con el valor de variable "Paralelo"
        Btn_Paralelo.setOnClickListener {
            var Paralelo: String = "Paralelo"
            intent.putExtra("TipoCircuito",Paralelo)
            startActivity(intent)
        }
    }
}