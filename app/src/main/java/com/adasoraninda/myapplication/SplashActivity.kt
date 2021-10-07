package com.adasoraninda.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewPropertyAnimator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val text = findViewById<TextView>(R.id.text_splash)

        val pref = SettingPreferences.getInstance(dataStore)
        val appViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            AppViewModel::class.java
        )

        text.alpha = 0f

        appViewModel.themeState.observe(this, { state ->
            Log.d("Main", "$state")
            if (state == null) {
                return@observe
            }

            if (state) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            text.animate().setDuration(2_000L).alpha(1f).withEndAction {
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }

                appViewModel.doneSetTheme()
            }
        })
    }
}