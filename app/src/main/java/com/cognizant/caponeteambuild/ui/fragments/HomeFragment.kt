package com.cognizant.caponeteambuild.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cognizant.caponeteambuild.R
import com.cognizant.caponeteambuild.data.Home
import com.cognizant.caponeteambuild.databinding.RecyclerViewLayoutBinding
import com.cognizant.caponeteambuild.ui.adapters.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        const val ARTICLE_TITLE = "ARTICLE_TITLE"
        const val KEY_ARTICLE_API = "KEY_ARTICLE_API"
    }

    private var _binding: RecyclerViewLayoutBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerViewLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.itemAnimator = DefaultItemAnimator()
            it.setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.devider)!!
            )
            it.addItemDecoration(itemDecoration)
            val adapter = HomeAdapter(requireContext(), getArticles())
            it.adapter = adapter
            adapter.onItemClick = { article ->
                val bundle = Bundle()
                bundle.putString(ARTICLE_TITLE, article.articleName)
                bundle.putString(KEY_ARTICLE_API, article.apiKey)
                findNavController().navigate(R.id.action_home_to_article, bundle)
            }
        }
    }

    private fun getArticles(): List<Home> {
        return arrayListOf(
            Home(
                getString(R.string.politics),
                R.drawable.ic_politics,
                getString(R.string.politics_api)
            ),
            Home(getString(R.string.sports), R.drawable.ic_sports, getString(R.string.sport_api)),
            Home(getString(R.string.travels), R.drawable.ic_travel, getString(R.string.travel_api))
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}