package com.jploran.musicplayerdiplo.ui.fragments

import android.content.pm.PackageManager
import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.jploran.musicplayerdiplo.R
import com.jploran.musicplayerdiplo.databinding.FragmentMusicListBinding

class MusicList : Fragment() {
    private var _binding: FragmentMusicListBinding? = null
    private val binding get() = _binding!!
    private val musicListViewModel: MusicListViewModel by viewModels()
    private var readMediaAudioGranted = false
    private var readPermissionGranted = false
    private var writePermissionGranted = false

    private var permissionsToRequest = mutableListOf<String>()
    private val permissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){
        permissions: Map<String, Boolean> ->
        val allGranted = permissions.all{map ->
            map.value
        }
        if(allGranted){
            actionPermissionsGranted()
        }else{
            permissionsToRequest.forEach{permission ->
                musicListViewModel.onPermissionResult(
                    permission,
                    permissions[permission] == true
                )
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        updateOrRequestPermissions()
    }
    private fun updateOrRequestPermissions() {
        val hasReadPermission = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }else{
            true
        }

        val hasWritePermission = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }else{
            true
        }

        val hasMediaAudioPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        }else{
            true
        }

        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPermission
        writePermissionGranted = hasWritePermission || minSdk29
        readMediaAudioGranted = hasMediaAudioPermission

        if(!readPermissionGranted)
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)

        if(!writePermissionGranted)
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(!readMediaAudioGranted && (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU))
            permissionsToRequest.add(Manifest.permission.READ_MEDIA_AUDIO)

        if(permissionsToRequest.isNotEmpty()){
            Toast.makeText(requireContext(), "Faltan permisos", Toast.LENGTH_SHORT).show()
        }else{
            actionPermissionsGranted()
        }
    }

    private fun actionPermissionsGranted(){
        Toast.makeText(requireContext(), "Todos los permisos concedidos", Toast.LENGTH_SHORT)
    }
}