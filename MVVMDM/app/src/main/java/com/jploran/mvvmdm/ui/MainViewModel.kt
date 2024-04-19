package com.jploran.mvvmdm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jploran.mvvmdm.data.remote.AnimeProvider
import com.jploran.mvvmdm.data.remote.model.AnimeDto
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _anime = MutableLiveData<AnimeDto>()
    val anime: LiveData<AnimeDto> = _anime

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _numero = MutableLiveData<String>()
    val numero: LiveData<String> = _numero

    fun getAnime(){
        viewModelScope.launch {
            _anime.postValue(AnimeProvider.getAnimesApi())

        }
    }

}