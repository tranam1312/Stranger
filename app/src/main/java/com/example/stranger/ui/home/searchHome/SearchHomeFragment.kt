@file:Suppress("OverrideDeprecatedMigration", "OverrideDeprecatedMigration",
    "OverrideDeprecatedMigration", "OverrideDeprecatedMigration", "OverrideDeprecatedMigration",
    "OverrideDeprecatedMigration"
)

package com.example.stranger.ui.home.searchHome

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stranger.R

class SearchHomeFragment : Fragment() {

    companion object {
        fun newInstance() = SearchHomeFragment()
    }

    private lateinit var viewModel: SearchHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchHomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}