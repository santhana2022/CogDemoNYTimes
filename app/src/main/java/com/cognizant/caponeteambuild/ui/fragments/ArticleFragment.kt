package com.cognizant.caponeteambuild.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cognizant.caponeteambuild.Constant
import com.cognizant.caponeteambuild.R
import com.cognizant.caponeteambuild.data.Result
import com.cognizant.caponeteambuild.databinding.ErrorLayoutBinding
import com.cognizant.caponeteambuild.databinding.RecyclerViewLayoutBinding
import com.cognizant.caponeteambuild.ui.MainActivity
import com.cognizant.caponeteambuild.ui.adapters.ArticleAdapter
import com.cognizant.caponeteambuild.util.MainEvent
import com.cognizant.caponeteambuild.util.MainState
import com.cognizant.caponeteambuild.util.Utility
import com.cognizant.caponeteambuild.utility.hide
import com.cognizant.caponeteambuild.utility.show
import com.cognizant.caponeteambuild.viewmodel.ArticleViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ArticleFragment : Fragment() {

    private var _binding: RecyclerViewLayoutBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerViewLayoutBinding.inflate(inflater, container, false)

        arguments?.getString(HomeFragment.KEY_ARTICLE_API)?.let { articleKey ->
            articleViewModel.processEvent(MainEvent.ArticleSelectedStateEvent(articleKey))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel.stateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is MainState.Initial -> {
                    //set title
                    (activity as MainActivity).supportActionBar?.title =
                        arguments?.getString(HomeFragment.ARTICLE_TITLE)
                }
                is MainState.Loading -> {
                    //show loading indicator
                    if (it.forceToRefresh) {
                        binding.spinner.hide()
                    } else {
                        binding.spinner.show()
                    }
                }
                is MainState.Ready -> {
                    //show articles information
                    binding.spinner.hide()
                    initRecycler(it.data)
                }
                is MainState.Error -> {
                    //show error
                    binding.spinner.hide()
                    setUpErrorScreenBottomSheet(it.message)
                }
            }
        }
    }

    private fun initRecycler(resultList: List<Result>) {
        binding.swipeContainer.setOnRefreshListener {
            if (!Utility.NETWORK.checkInternet(requireContext())) {
                articleViewModel.processEvent(MainEvent.ErrorEvent(Constant.appText.NO_INTERNET))
            } else {
                arguments?.getString(HomeFragment.KEY_ARTICLE_API)?.let { articleKey ->
                    articleViewModel.stateLiveData.value =
                        MainState.Loading(articleApiKey = articleKey, true)
                }
            }
        }
        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.itemAnimator = DefaultItemAnimator()
            it.setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.devider)!!
            )
            it.addItemDecoration(itemDecoration)
            val adapter = ArticleAdapter(requireContext(), resultList)
            it.adapter = adapter
            binding.swipeContainer.isRefreshing = false
            adapter.onItemClick = {
                // TODO- item click action
            }
        }
    }

    // Error bottom sheet
    private fun setUpErrorScreenBottomSheet(message: String) {
        val errorLayoutBinding = ErrorLayoutBinding.inflate(layoutInflater)
        // custom style
        val bottomSheetDialog = BottomSheetDialog(
            ContextThemeWrapper(requireActivity(), R.style.BasicBottomSheetDialogTheme)
        )
        bottomSheetDialog.setContentView(errorLayoutBinding.root)
        // Canceling not allowed
        bottomSheetDialog.setCancelable(false)
        val standardBottomSheetBehavior =
            BottomSheetBehavior.from(errorLayoutBinding.root.parent as View)
        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Set Dragging off
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        }
        standardBottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
        bottomSheetDialog.show()

        // Apply click events
        errorLayoutBinding.apply {
            close.setOnClickListener {
                bottomSheetDialog.dismiss()
                findNavController().navigateUp()
            }
            // set error text
            errorMessage.text = message
            okButton.setOnClickListener {
                bottomSheetDialog.dismiss()
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}