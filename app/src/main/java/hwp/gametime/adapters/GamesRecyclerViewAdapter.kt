package hwp.gametime.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.StreamEncoder
import com.bumptech.glide.load.resource.file.FileToStreamDecoder
import com.caverock.androidsvg.SVG
import hwp.gametime.R
import hwp.gametime.fragments.GamesFragment.OnGamesFragmentInteractionListener
import hwp.gametime.models.Game
import hwp.gametime.models.GameList
import hwp.gametime.models.Team
import hwp.gametime.network.svg.SvgDecoder
import hwp.gametime.network.svg.SvgDrawableTranscoder
import hwp.gametime.network.svg.SvgSoftwareLayerSetter
import hwp.gametime.util.*
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [Game] and makes a call to the
 * specified [OnGamesFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class GamesRecyclerViewAdapter(private val mValues: GameList,
                               private val mListener: OnGamesFragmentInteractionListener?)
    : RecyclerView.Adapter<GamesRecyclerViewAdapter.ViewHolder>() {
    private var requestBuilder: GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable>? = null

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_game, parent, false)

        context = view.context

        requestBuilder = Glide.with(parent.context)
                .using(Glide.buildStreamModelLoader(Uri::class.java, parent.context), InputStream::class.java)
                .from(Uri::class.java)
                .`as`(SVG::class.java)
                .transcode(SvgDrawableTranscoder(), PictureDrawable::class.java)
                .sourceEncoder(StreamEncoder())
                .cacheDecoder(FileToStreamDecoder(SvgDecoder()))
                .decoder(SvgDecoder())
                .placeholder(R.drawable.ic_nba_logo)
                .error(android.R.drawable.ic_delete)
                .animate(android.R.anim.fade_in)
                .listener(SvgSoftwareLayerSetter<Uri>())

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = mValues.games[position]
        holder.mItem = game
        holder.mTvScore.text =
                if (game.status!! == "upcoming") {
                    val sdf = SimpleDateFormat("h:mm a z", Locale.US)
                    sdf.format(game.startedAt)
                } else if (game.status!! == "in_progress") {
                    val ls = context?.getString(R.string.live_score)
                    Html.fromHtml(ls?.format(game.score,
                            if (game.period!!<5) game.period?.ordinal()
                            else "OT"+game.period?.mod(4)))
                } else{
                    val ls = context?.getString(R.string.final_score)
                    val final = if (game.period!!>4) "OT"+game.period?.mod(4) else "FINAL"
                    Html.fromHtml(ls?.format(game.score, final))
                }
        if (game.period!!>0 && game.status!! == "in_progress")
            holder.mTvScore.setTextColor(Color.RED)
        else
            holder.mTvScore.setTextColor(Color.BLACK)

        holder.mTvScore.setOnClickListener {
            if (game.status!! == "upcoming" || game.period!!<1){
                context?.toast("No data about the game yet!")
            }
            else {
                mListener?.onGameClick(mValues.games[position],
                        mValues.home_teams[position],
                        mValues.away_teams[position])
            }
        }

        holder.llAwayTeamContainer?.setOnClickListener {
            mListener?.onTeamClick(mValues.away_teams[position])
        }

        holder.llHomeTeamContainer?.setOnClickListener {
            mListener?.onTeamClick(mValues.home_teams[position])
        }

        setTeamData(mValues.home_teams[position], holder.mIvHome, holder.mTvHomeTeamName)
        setTeamData(mValues.away_teams[position], holder.mIvAway, holder.mTvAwayTeamName)
    }

    private fun setTeamData(team: Team, iv: ImageView, tvTeamName: TextView) {
        if (team.slug != null) {
//            if (team.slug == "nba-sac") {
//                iv.setImageResource(R.drawable.sacramento_kings)
//            } else {
            val teamLogo = Util.getTeamLogoUri(team.slug!!)
            requestBuilder!!
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so it's not worth to cache it
                    .load(teamLogo)
                    .into(iv)
//            }
        }
        tvTeamName.text = "#" + team.hashtag
    }

    override fun getItemCount(): Int {
        return mValues.games.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        //        public final TextView mIdView;
        val mTvScore: TextView
        val mIvHome: ImageView
        val mIvAway: ImageView
        val mTvHomeTeamName: TextView
        val mTvAwayTeamName: TextView
        var mItem: Game? = null

        val llHomeTeamContainer: View?
        val llAwayTeamContainer: View?

        init {
            mTvScore = mView.findViewById(R.id.tvScore) as TextView
            val llHome = mView.findViewById(R.id.llHomeTeam)
            llHomeTeamContainer = llHome.findViewById(R.id.containter)
            mIvHome = llHome.findViewById(R.id.ivTeamLogo) as ImageView
            mTvHomeTeamName = llHome.findViewById(R.id.tvTeamName) as TextView

            val llAway = mView.findViewById(R.id.llAwayTeam)
            llAwayTeamContainer = llAway.findViewById(R.id.containter)
            mIvAway = llAway.findViewById(R.id.ivTeamLogo) as ImageView
            mTvAwayTeamName = llAway.findViewById(R.id.tvTeamName) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mTvScore.text + "'"
        }
    }
}
