package com.adasoraninda.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    private val _themeState = MutableLiveData<Boolean?>()
    val themeState: LiveData<Boolean?> get() = _themeState

    init {
        getThemeSettings()
    }

    private fun getThemeSettings() {
        viewModelScope.launch {
            pref.getThemeSetting().collect { value ->
                _themeState.value = value
            }
        }
    }

    fun saveThemeSettings(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun doneSetTheme() {
        _themeState.value = null
    }
}