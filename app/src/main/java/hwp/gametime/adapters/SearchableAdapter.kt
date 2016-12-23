package hwp.gametime.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.caverock.androidsvg.SVG
import de.hdodenhof.circleimageview.CircleImageView
//import hwp.gametime.BusProvider
import hwp.gametime.R
import hwp.gametime.fragments.SearchableBaseFragment
import hwp.gametime.models.Player
import hwp.gametime.models.Team
import hwp.gametime.util.Util
import java.io.InputStream


class SearchableAdapter(var items: List<SearchableItem>, val mListener: OnItemTapInteraction)
    : RecyclerView.Adapter<SearchableAdapter.ViewHolder>(), Filterable {

    var filteredItems: List<SearchableItem>
    var selectedItems: MutableList<SearchableItem>? = null
    private var requestBuilder: GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable>? = null

    init {
        this.filteredItems = items
    }

    private var selectedColor: Int = Color.WHITE
    private var normalColor: Int = Color.WHITE

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_search, parent, false)
        requestBuilder = Util.getRequestBuilder(view.context)
        mContext = view.context
        selectedColor = ContextCompat.getColor(view.context, R.color.list_item_selected_state)
        normalColor = ContextCompat.getColor(view.context, R.color.list_item_normal_state)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchableItem = filteredItems[position]

        when (searchableItem) {
            is Team -> {
                holder.mTextView.text = searchableItem.name + " " + searchableItem.nickname

                searchableItem.slug?.let {
                    val teamLogo = Util.getTeamLogoUri(it)
                    requestBuilder?.let {
                        it.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                // SVG cannot be serialized so it's not worth to cache it
                                .load(teamLogo)
                                .into(holder.mImageView)
                    }

                }

            }
            is Player -> {
                holder.mTextView.text = searchableItem.name

                mContext?.let {
                    var url = it.getString(R.string.playerHeadShotUrl)
                    url = String.format(url, searchableItem.firstName, searchableItem.lastName)
                    Glide.with(it)
                            .load(url)
                            .asBitmap()
                            .placeholder(R.drawable.ic_nba_logo)
                            .centerCrop()
                            .into(holder.mImageView)
                }

            }
        }

        selectedItems?.let {
            if (it.contains(filteredItems[position]))
                holder.mView.setBackgroundColor(selectedColor)
            else
                holder.mView.setBackgroundColor(normalColor)
        }


        holder.mView.setOnClickListener {
            Log.d(this.javaClass.name + " " + "OnClickListener: ", position.toString())
            mListener.onClick(position)
        }

        holder.mView.setOnLongClickListener {
            Log.d(this.javaClass.name + " " + "OnLongClickListener: ", position.toString())
            mListener.onLongClick(position)
            true
        }
    }

    interface OnItemTapInteraction{
        fun onLongClick(position: Int)
        fun onClick(position: Int)
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTextView: TextView
        val mImageView: CircleImageView

        init {
            mTextView = mView.findViewById(R.id.text) as TextView
            mImageView = mView.findViewById(R.id.imageView) as CircleImageView
        }

        override fun toString(): String {
            return super.toString() + " '" + mTextView.text
        }
    }

    private var filter: Filter? = null

    override fun getFilter(): Filter? {
        if (filter == null) {
            filter = NameFilter()
        }
        return filter
    }

    private inner class NameFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (constraint != null && constraint.isNotEmpty()) {
                var tempList = items.filter { it.satisfiesFilter(constraint) }

                filterResults.count = tempList.size
                filterResults.values = tempList

            } else {
                filterResults.count = items.size
                filterResults.values = items
            }

            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            filteredItems = results.values as List<SearchableItem>
            notifyDataSetChanged()
        }
    }

    interface SearchableItem {
        fun satisfiesFilter(string: CharSequence): Boolean
    }



}