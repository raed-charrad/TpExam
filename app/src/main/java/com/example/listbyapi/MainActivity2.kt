package com.example.listbyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {

        lateinit var nom : TextView
        lateinit var genre : TextView
        lateinit var duree : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        nom = findViewById(R.id.txtNom)
        genre = findViewById(R.id.txtGender)
        duree = findViewById(R.id.txtDuree)
        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        val nomfilm = sharedPref.getString("nomfilm", "null")
        val genre1 = sharedPref.getString("genre", "null")
        val duree1 = sharedPref.getString("duree", "null")
        nom.text = nomfilm
        genre.text = genre1
        duree.text = duree1

    }
}