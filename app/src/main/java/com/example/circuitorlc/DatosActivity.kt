package com.example.circuitorlc

import android.R.id.message
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


lateinit var TipoCircuito: TextView

lateinit var Resistencia : EditText
lateinit var Inductor: EditText
lateinit var Capacitor: EditText
lateinit var Corriente: EditText
lateinit var Voltaje: EditText
lateinit var BotonCalcular: Button
lateinit var rbVolt: RadioButton
lateinit var rbCorriente: RadioButton
lateinit var Foto: ImageView
lateinit var radioGroup: RadioGroup
class DatosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos)
        val datoRecibido = intent.getStringExtra("TipoCircuito")
        if(datoRecibido == "Serie"){
            TipoCircuito = findViewById(R.id.LblCircuito)
            TipoCircuito.setText(datoRecibido)
            Foto = findViewById(R.id.FotoCircuito)
            Foto.setImageResource(R.drawable.serie)

        }
        else if(datoRecibido == "Paralelo"){
            TipoCircuito = findViewById(R.id.LblCircuito)
            TipoCircuito.setText(datoRecibido)
            Foto = findViewById(R.id.FotoCircuito)
            Foto.setImageResource(R.drawable.paralelo)

        }

        Resistencia = findViewById(R.id.TxtResistencia)
        Inductor = findViewById(R.id.TxtInductor)
        Capacitor = findViewById(R.id.TxtCapacitor)
        Corriente = findViewById(R.id.TxtCorriente)
        Voltaje = findViewById(R.id.TxtVoltaje)
        radioGroup = findViewById(R.id.rgroup)
        rbVolt= findViewById(R.id.rbCapactorV)
        rbCorriente = findViewById(R.id.rbInductorI)


        var seleccion = if (radioGroup.checkedRadioButtonId == rbCorriente.id) 1 else 0

        // Listener para actualizar 'seleccion' según el RadioButton seleccionado
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            seleccion = if (checkedId == rbCorriente.id) 1 else 0
        }
        val intentGraf = Intent(this,Graficar::class.java)
        BotonCalcular = findViewById(R.id.btnGraficar)
        BotonCalcular.setOnClickListener {
            val r = Resistencia.text.toString()
            val inductancia = Inductor.text.toString()
            val capacitancia = Capacitor.text.toString()
            val voltI = Voltaje.text.toString()
            val corrienteI = Corriente.text.toString()

            if(r == ""||inductancia==""|| voltI==""||corrienteI==""){
                showAlertDialog("Campos incompletos", "Por favor, complete todos los campos.")
            }
            else{
                intentGraf.putExtra("R",r)
                intentGraf.putExtra("L", inductancia)
                intentGraf.putExtra("C", capacitancia)
                intentGraf.putExtra("v0",voltI)
                intentGraf.putExtra("i0",corrienteI)
                intentGraf.putExtra("rb",seleccion)
                intentGraf.putExtra("seleccion",datoRecibido)
                startActivity(intentGraf)

            }

        }
    }
    private fun isFieldEmpty(editText: EditText): Boolean {
        return editText.text.toString().trim().isEmpty()
    }

    // Método para mostrar un AlertDialog
    private fun showAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                // Acción al hacer clic en "OK" (por ejemplo, cerrar el diálogo)
                dialog.dismiss()
            }

        // Crear y mostrar el diálogo
        val alertDialog = builder.create()
        alertDialog.show()
    }

}