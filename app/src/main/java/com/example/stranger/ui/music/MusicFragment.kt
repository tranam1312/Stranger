package com.example.stranger.ui.music

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentMusicBinding
import com.example.stranger.model.response.Song
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


    private val onClickSong: (Int) -> Unit = {
        viewModel.listSong.value?.let { it1 -> splashActivity.startServiceMusic(it1,it ) }
    }
}