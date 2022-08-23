package com.example.stranger.ui.newProFile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.common.State
import com.example.stranger.databinding.FragmentNewProFileBinding
import com.example.stranger.ui.Dialog.OpenLibraryDialog
import com.example.stranger.ui.main.MainFragment
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewProFileFragment : BaseFragmentWithBinding<FragmentNewProFileBinding>() {

    companion object {
        fun newInstance() = NewProFileFragment()
        const val REQUEST_IMAGE_CAPTURE = 1
        const val CAMERA_PIC_REQUEST = 500
    }

    private val viewModel: NewProFileViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater): FragmentNewProFileBinding =
        FragmentNewProFileBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }

    override fun init() {
        proFile()
    }


    override fun initAction() {
        binding.buttonConfrim.setOnClickListener {
            if (binding.name.text.toString() != "") {
                viewModel.updateData(binding.name.text.toString())
            }
        }
        binding.avatar.setOnClickListener {
            checkPermission()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data?.data != null) {
                val uri: Uri = data?.data!!
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                binding.avatar.setImageBitmap(bitmap)

            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            if (data?.data != null) {
                val bitmap: Bitmap = data.extras!!.get("data") as Bitmap
                binding.avatar.setImageBitmap(bitmap)
            }
        }
    }


    fun proFile() {
        viewModel.proFile.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> binding.buttonConfrim.isEnabled = false
                is State.Success -> {
                    splashActivity.addFragment(MainFragment.newInstance(),MainFragment::class.java.name)
                }
                is State.Error -> {
                    binding.buttonConfrim.isEnabled = false
                }
                else -> {}
            }
        }
    }

    fun checkPermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    openDialog()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun openDialog() {
        fragmentManager?.let {
            val fragment = OpenLibraryDialog.newInstance(onClickOpen)
            it.beginTransaction().add(fragment, fragment.tag).commit()
        }
    }

    private val onClickOpen: (Int) -> Unit = {
        when (it) {
            REQUEST_IMAGE_CAPTURE -> {
                val photoPickerIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(photoPickerIntent, REQUEST_IMAGE_CAPTURE)
            }
            CAMERA_PIC_REQUEST -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST)
            }
        }
    }


}