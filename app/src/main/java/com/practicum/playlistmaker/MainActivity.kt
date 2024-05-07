package com.practicum.playlistmaker

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

        //Через анонимный класс
        val bt_search = findViewById<Button>(R.id.bt_search)
        val searchBtClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Нажата кнопка ПОИСК!", Toast.LENGTH_SHORT).show()
            }
        }
        bt_search.setOnClickListener(searchBtClickListener)



        val bt_media = findViewById<Button>(R.id.bt_media)
        val mediaBtClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Нажата кнопка МЕДИАТЕКА!", Toast.LENGTH_SHORT).show()
            }
        }
        bt_media.setOnClickListener(mediaBtClickListener)
        //



        //Через лямбда-выражение
        val bt_settings = findViewById<Button>(R.id.bt_settings)
        bt_settings.setOnClickListener {
            Toast.makeText(this@MainActivity, "Нажата кнопка НАСТРОЙКИ!", Toast.LENGTH_SHORT).show()
        }
        //
    }
}