package com.dicoding.fundamentalfirstsubmission1.viewmodel


import androidx.lifecycle.*
import com.dicoding.fundamentalfirstsubmission1.theme.SettingPref
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPref) : ViewModel() {

    fun getTheme() = pref.getThemeSetting().asLiveData()

    fun saveTheme(isDark: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDark)
        }
    }

    class Setting(private val pref: SettingPref) : ViewModelProvider.NewInstanceFactory() {
        override fun <Theme : ViewModel> create(modelClass: Class<Theme>): Theme = SettingViewModel(pref) as Theme
    }
}