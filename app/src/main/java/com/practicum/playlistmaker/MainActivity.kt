package com.practicum.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        //Через анонимный класс
        val bt_search = findViewById<Button>(R.id.bt_search)
        val searchBtClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Нажата кнопка ПОИСК!", Toast.LENGTH_SHORT).show()
            }
        }
        bt_search.setOnClickListener(searchBtClickListener)
        */

        val bt_search = findViewById<Button>(R.id.bt_search)
        bt_search.setOnClickListener {
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }


        val bt_media = findViewById<Button>(R.id.bt_media)
        bt_media.setOnClickListener {
            val displayIntent = Intent(this, MediaActivity::class.java)
            startActivity(displayIntent)
        }


        val bt_settings = findViewById<Button>(R.id.bt_settings)
        bt_settings.setOnClickListener {
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)
        }
    }
}