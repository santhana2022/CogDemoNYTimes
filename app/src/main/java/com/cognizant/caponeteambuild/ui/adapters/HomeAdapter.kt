package com.cognizant.caponeteambuild.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cognizant.caponeteambuild.data.Home
import com.cognizant.caponeteambuild.databinding.HomeItemLayputBinding
import com.cognizant.caponeteambuild.utility.setTextOrHide
import com.cognizant.caponeteambuild.utility.toCircularImage

class HomeAdapter(private val context: Context, private var homeList: List<Home>) :
    RecyclerView.Adapter<HomeAdapter.ArticleListHolderView>() {
    var onItemClick: ((Home) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListHolderView {
        val itemBinding =
            HomeItemLayputBinding.inflate(LayoutInflater.from(context), parent, false)
        return ArticleListHolderView(itemBinding)
    }

    override fun onBindViewHolder(holder: ArticleListHolderView, position: Int) {
        val itemResponse = homeList[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(itemResponse)
        }
        holder.bind(itemResponse)
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    class ArticleListHolderView(private val itemBinding: HomeItemLayputBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(result: Home) {
            itemBinding.apply {
                setTextOrHide(result.articleName, articleName)
                toCircularImage(result.articleImage, articleImage)
            }
        }
    }
}