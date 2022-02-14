package com.cognizant.caponeteambuild.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cognizant.caponeteambuild.data.Result
import com.cognizant.caponeteambuild.utility.loadImage
import com.cognizant.caponeteambuild.utility.setTextOrHide
import android.content.Intent
import android.net.Uri
import com.cognizant.caponeteambuild.R
import com.cognizant.caponeteambuild.databinding.ArticleItemLayoutBinding
import com.cognizant.caponeteambuild.utility.hide

class ArticleAdapter(private val context: Context, private var resultList: List<Result>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleHolderView>() {
    var onItemClick: ((Result) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolderView {
        val itemBinding =
            ArticleItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ArticleHolderView(itemBinding)
    }

    override fun onBindViewHolder(holder: ArticleHolderView, position: Int) {
        val itemResponse = resultList[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(itemResponse)
        }
        holder.bind(itemResponse, context)
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    class ArticleHolderView(private val itemBinding: ArticleItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(result: Result, context: Context) {
            itemBinding.apply {
                setTextOrHide(result.title, title)
                setTextOrHide(result.section, section)
                setTextOrHide(result.byline, byline)
                setTextOrHide(result.desFacet.joinToString(), desFacet)

                // Details view
                if (result.abstractString.isEmpty())
                    details.hide()
                else
                    details.setText(context.getString(R.string.show_details), result.abstractString)

                // load image
                if (result.multimedia != null)
                    loadImage(result.multimedia[0].url, itemImage)

                // more info button
                if (result.url.isBlank())
                    moreInfo.hide()
                moreInfo.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(result.url))
                    context.startActivity(browserIntent)
                }
            }
        }
    }
}