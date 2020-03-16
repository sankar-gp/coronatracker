package com.sankar.cvirus

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var jsonString = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doAsync {
            val client: OkHttpClient = OkHttpClient.Builder()
                    .connectTimeout(25, TimeUnit.SECONDS)
                    .readTimeout(25, TimeUnit.SECONDS)
                    .writeTimeout(25, TimeUnit.SECONDS)
                    .build()

            val request: Request = Request.Builder().url("https://www.bing.com/covid/data").get().build()
            val mResponse = client.newCall(request).execute()
            jsonString = mResponse.body!!.string()

            uiThread {
                if (jsonString.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, jsonString.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}
