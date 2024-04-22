

package com.raywenderlich.podplay.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raywenderlich.podplay.databinding.SearchItemBinding
import com.raywenderlich.podplay.viewmodel.SearchViewModel.PodcastSummaryViewData

class PodcastListAdapter(
    private var podcastSummaryViewList: List<PodcastSummaryViewData>?,
    private val podcastListAdapterListener: PodcastListAdapterListener,
    private val parentActivity: Activity
) : RecyclerView.Adapter<PodcastListAdapter.ViewHolder>() {

  interface PodcastListAdapterListener {
    fun onShowDetails(podcastSummaryViewData: PodcastSummaryViewData)
  }

  inner class ViewHolder(
      databinding: SearchItemBinding,
      private val podcastListAdapterListener: PodcastListAdapterListener
  ) : RecyclerView.ViewHolder(databinding.root) {
    var podcastSummaryViewData: PodcastSummaryViewData? = null
    val nameTextView: TextView = databinding.podcastNameTextView
    val lastUpdatedTextView: TextView = databinding.podcastLastUpdatedTextView
    val podcastImageView: ImageView = databinding.podcastImage

    init {
      databinding.searchItem.setOnClickListener {
        podcastSummaryViewData?.let {
          podcastListAdapterListener.onShowDetails(it)
        }
      }
    }
  }

  fun setSearchData(podcastSummaryViewData: List<PodcastSummaryViewData>) {
    podcastSummaryViewList = podcastSummaryViewData
    this.notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastListAdapter.ViewHolder {
    return ViewHolder(
        SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        podcastListAdapterListener
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val searchViewList = podcastSummaryViewList ?: return
    val searchView = searchViewList[position]
    holder.podcastSummaryViewData = searchView
    holder.nameTextView.text = searchView.name
    holder.lastUpdatedTextView.text = searchView.lastUpdated
    Glide.with(parentActivity).load(searchView.imageUrl).into(holder.podcastImageView)
  }

  override fun getItemCount(): Int {
    return podcastSummaryViewList?.size ?: 0
  }
}
