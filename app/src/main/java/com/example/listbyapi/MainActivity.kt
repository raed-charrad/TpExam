package com.example.listbyapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var recycler : RecyclerView
    private lateinit var adapter: CustomAdapter
    lateinit var offerList: List<Offre>
    var selectedItem : Offre? = null
    lateinit var btnMarqFavoris : Button
    lateinit var btnAfficheFavoris : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnMarqFavoris = findViewById(R.id.fav)
        btnAfficheFavoris = findViewById(R.id.showFav)

        recycler = findViewById(R.id.my_recycler_view)
        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        offerList=ArrayList()
        var favoriteList = ArrayList<Offre>()
        adapter = CustomAdapter(offerList)
        recycler.adapter = adapter

        adapter.onItemClick = {
            selectedItem = it
        }
         val scope = CoroutineScope(Dispatchers.Main)
         scope.launch {
             try{
                 val response = Apiclient.apiService.getFilms()
                 if (response.isSuccessful && response.body() != null) {
                     Log.i("Success",response.body().toString())
                     (offerList as ArrayList<Offre>).addAll(response.body()!!)
                     adapter.notifyDataSetChanged()
                     println(response.body())
                 }else{
                     Log.e("Error",response.message())
                 }
             } catch (e: Exception) {
                 Log.e("Error",e.message.toString())
             }
         }
         if (selectedItem==null){
             btnMarqFavoris.isVisible=false
             btnAfficheFavoris.isVisible=false
         }
        btnMarqFavoris.setOnClickListener{
            val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("nomfilm", selectedItem?.nomfilm)
            editor.putString("genre", selectedItem?.genre)
            // editor selected at date and time
            editor.putString("duree", selectedItem?.duree)
            editor.apply()
            favoriteList.add(selectedItem!!)
            Toast.makeText(this,"Favoris sauvegardés",Toast.LENGTH_SHORT).show()
            println(it)
        }
        btnAfficheFavoris.setOnClickListener{
            val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
            val nomfilm = sharedPref.getString("nomfilm", "")
            val genre = sharedPref.getString("genre", "")
            val duree = sharedPref.getString("duree", "")
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("nomfilm", nomfilm)
            intent.putExtra("genre", genre)
            intent.putExtra("duree", duree)
            startActivity(intent)
            println("test")
        }


    }
}