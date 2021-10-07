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

    private var propertyAnim: ViewPropertyAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        val text = findViewById<TextView>(R.id.text_splash)

        val pref = SettingPreferences.getInstance(dataStore)
        val appViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            AppViewModel::class.java
        )

        text.alpha = 0f

        propertyAnim = text.animate().setDuration(2_000L).alpha(1f).withEndAction {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

        appViewModel.getThemeSettings().observe(this, { state ->
            Log.d("Splash", "$state")
            if (state == null) {
                return@observe
            }

            val mode = if (state) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO

            AppCompatDelegate.setDefaultNightMode(mode)

            propertyAnim?.start()
        })
    }

    override fun onDestroy() {
        propertyAnim?.cancel()
        super.onDestroy()
    }
}