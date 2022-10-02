package com.example.stranger.ui.music

import android.content.Intent
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentMusicBinding
import com.example.stranger.model.Song
import com.example.stranger.service.MusicService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicFragment : BaseFragmentWithBinding<FragmentMusicBinding>() {
    private lateinit var songAdapter: SongAdapter
    private var listSong: ArrayList<Song> = arrayListOf()

    companion object {
        fun newInstance() = MusicFragment()
    }

    private val viewModel: MusicViewModel by activityViewModels()


    override fun getViewBinding(inflater: LayoutInflater): FragmentMusicBinding =
        FragmentMusicBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            songAdapter = SongAdapter(onClickSong)
            rvSong.adapter = songAdapter
        }

    override fun init() {
        viewModel.getDataMusicServer()
    }

    override fun initData() {
        viewModel.listSong.observe(viewLifecycleOwner) {
            songAdapter.submitList(it)
            listSong = it
        }
    }

    override fun initAction() {

    }


    private val onClickSong: (Song) -> Unit = {
        splashActivity.startServiceMusic(it, 1)
    }
}