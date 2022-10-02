package com.example.stranger.ui.Dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.stranger.R
import com.example.stranger.databinding.OpenLibraryDialogBinding
import com.example.stranger.ui.newProFile.NewProFileFragment

class OpenLibraryDialog : DialogFragment() {
    private lateinit var binding: OpenLibraryDialogBinding
    private var onClickOpen: ((Int) -> Unit)? = null

    companion object {
        fun newInstance(onClickOpen: ((Int) -> Unit)?): OpenLibraryDialog {
            val fragment = OpenLibraryDialog()
            fragment.onClickOpen = onClickOpen
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.open_library_dialog, container, false)
        initAction()
        return binding.root
    }

    fun initAction() {
        binding.openLibrary.setOnClickListener {
            onClickOpen?.invoke(NewProFileFragment.REQUEST_IMAGE_CAPTURE)
            dismiss()
        }
        binding.openCamera.setOnClickListener {
            onClickOpen?.invoke(NewProFileFragment.CAMERA_PIC_REQUEST)
            dismiss()
        }
        binding.cancelAction.setOnClickListener {
            dismiss()
        }
    }
}