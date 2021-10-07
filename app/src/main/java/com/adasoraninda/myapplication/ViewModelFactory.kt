package com.adasoraninda.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
     
       @Suppress("UNCHECKED_CAST")
       override fun <T : ViewModel?> create(modelClass: Class<T>): T {
           if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
               return AppViewModel(pref) as T
           }
           throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
       }
    }