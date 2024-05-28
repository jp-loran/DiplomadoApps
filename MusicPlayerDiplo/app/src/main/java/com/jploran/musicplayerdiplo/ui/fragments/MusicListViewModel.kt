package com.jploran.musicplayerdiplo.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jploran.musicplayerdiplo.data.local.model.MusicFile

class MusicListViewModel: ViewModel() {
    private val permissionsToRequestQueue = mutableListOf<String>()
    private val _permissionsToRequest = MutableLiveData<List<String>>()
    val permissionsToRequest: LiveData<List<String>> = _permissionsToRequest

    private val _musicFiles = MutableLiveData<List<MusicFile>>()
    val musicFiles: LiveData<List<MusicFile>> = _musicFiles

    fun dismissDialog(){
        if(permissionsToRequestQueue.isNotEmpty())
            permissionsToRequestQueue.removeFirst()
    }

    fun onPermissionResult(permission: String, isGranted: Boolean){
        if(!isGranted && !permissionsToRequestQueue.contains(permission)) {
            _permissionsToRequest.postValue(permissionsToRequestQueue)
        }
    }


}