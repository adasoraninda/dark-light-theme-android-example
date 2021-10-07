package com.adasoraninda.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val appViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            AppViewModel::class.java
        )

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            appViewModel.saveThemeSettings(isChecked)
        }

        appViewModel.themeState.observe(this, { state ->
            Log.d("Settings", "$state")
            if (state == null) {
                return@observe
            }

            val mode = if (state) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO

            AppCompatDelegate.setDefaultNightMode(mode)

            switchTheme.isChecked = state
            appViewModel.doneSetTheme()
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}