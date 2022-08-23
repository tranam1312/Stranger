package com.example.stranger.ui.home.post

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startForegroundService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentPostBinding
import com.example.stranger.service.PostService
import com.example.stranger.ui.Dialog.OpenLibraryDialog
import com.example.stranger.ui.SplashActivity
import com.example.stranger.ui.newProFile.NewProFileFragment
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.util.ArrayList

@AndroidEntryPoint
class PostFragment : BaseFragmentWithBinding<FragmentPostBinding>() {

    companion object {
        fun newInstance() = PostFragment()
    }

    private lateinit var viewModel: PostViewModel

    override fun getViewBinding(inflater: LayoutInflater): FragmentPostBinding =
        FragmentPostBinding.inflate(inflater).apply {
            viewModel = ViewModelProvider(this@PostFragment)[PostViewModel::class.java]
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }

    override fun setToolBar(activity: AppCompatActivity) {
        binding.toolbar.apply {
            setTitle("Tạo bài viết")
            setIconLeft(R.drawable.ic_arrow_left)
            onBackPressed(activity)
            setTextRight("Đăng")
        }
    }

    override fun init() {

    }

    override fun initAction() {
        binding.openLibrary.setOnClickListener {
            checkPermission()
        }
        binding.closeImg.setOnClickListener {
            binding.imageView.visibility = View.GONE
            binding.closeImg.visibility = View.VISIBLE
        }
        binding.toolbar.setOnClickButtonRight {
            // Get the data from an ImageView as bytes
            binding.imageView.isDrawingCacheEnabled = true
            binding.imageView.buildDrawingCache()
            val bitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
            val data = baos.toByteArray()

            var intent = Intent(context,PostService::class.java)
            intent.putExtra("image",data)
            intent.putExtra("content",binding.appCompatEditText.text.toString())
            requireActivity().applicationContext.startForegroundService(intent)
            onBackPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == NewProFileFragment.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (data?.data != null) {
                val uri: Uri = data.data!!
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                binding.imageView.setImageBitmap(bitmap)

            }
        } else if (requestCode == NewProFileFragment.CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data?.data != null) {
                val bitmap: Bitmap = data.extras!!.get("data") as Bitmap
                binding.imageView.setImageBitmap(bitmap)
            }
        }
    }

    private fun checkPermission() {
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
            NewProFileFragment.REQUEST_IMAGE_CAPTURE -> {
                val photoPickerIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(photoPickerIntent, NewProFileFragment.REQUEST_IMAGE_CAPTURE)
            }

            NewProFileFragment.CAMERA_PIC_REQUEST -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, NewProFileFragment.CAMERA_PIC_REQUEST)
            }
        }
    }
    
}