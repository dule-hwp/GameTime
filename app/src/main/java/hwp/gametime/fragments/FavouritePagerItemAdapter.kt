package hwp.gametime.fragments

import android.content.Context
import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.caverock.androidsvg.SVG
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import hwp.gametime.R
import hwp.gametime.activities.TeamActivity
import hwp.gametime.models.Player
import hwp.gametime.models.Team
import hwp.gametime.util.Util
import hwp.gametime.util.toast
import java.io.InputStream

class FavouritePagerItemAdapter
//    private final OnListFragmentInteractionListener mListener;

(private val mContext: Context, private val mValues: List<String>, private val mClazz: Class<*>) : RecyclerView.Adapter<FavouritePagerItemAdapter.ViewHolder>() {

    private var mGson: Gson = Gson()
    private var requestBuilder: GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable>?

    init {
        requestBuilder = Util.getRequestBuilder(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_favouritepageritem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mGson.fromJson(mValues[position], mClazz)

        when (item) {
            is Team -> {
                holder.mTextView.text = item.name + " " + item.nickname
                item.slug?.let {
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
                holder.mTextView.text = item.name
                var url = mContext.getString(R.string.playerHeadShotUrl)
                url = String.format(url, item.firstName, item.lastName)
                Glide.with(mContext)
                        .load(url)
                        .asBitmap()
                        .placeholder(R.drawable.ic_nba_logo)
                        .centerCrop()
                        .into(holder.mImageView)
            }
        }

        holder.mView.setOnClickListener {
            Log.d(this.javaClass.name + " " + "OnClickListener: ", position.toString())
            when (item){
                is Team -> {
                    val intent = Intent(mContext, TeamActivity::class.java)
                    intent.putExtra(TeamActivity.EXTRA_TEAM, item)
                    mContext.startActivity(intent)
                }
            }
//            mContext.toast("favourite ")
        }
    }


    override fun getItemCount(): Int {
        return mValues.size
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
}
