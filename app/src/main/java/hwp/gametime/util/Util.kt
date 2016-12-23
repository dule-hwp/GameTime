package hwp.gametime.util

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.StreamEncoder
import com.bumptech.glide.load.resource.file.FileToStreamDecoder
import com.caverock.androidsvg.SVG
import hwp.gametime.R
import hwp.gametime.fragments.GamesFragment
import hwp.gametime.models.GameList
import hwp.gametime.network.svg.SvgDecoder
import hwp.gametime.network.svg.SvgDrawableTranscoder
import hwp.gametime.network.svg.SvgSoftwareLayerSetter
import java.io.InputStream
import java.util.*

/**
 * Created by dusan_cvetkovic on 11/24/16.
 */

class Util {

    companion object {
//        private val teamLogos = hashMapOf(
//                Pair("nba-atl", "http://vignette4.wikia.nocookie.net/logopedia/images/2/24/Atlanta_Hawks_logo.svg"),
//                Pair("nba-bos", "https://upload.wikimedia.org/wikipedia/en/8/8f/Boston_Celtics.svg"),
//                Pair("nba-bk", "https://upload.wikimedia.org/wikipedia/commons/4/44/Brooklyn_Nets_newlogo.svg"),
//                Pair("nba-cha", "https://upload.wikimedia.org/wikipedia/de/8/85/Charlotte_Bobcats.svg"),
//                Pair("nba-chi", "https://upload.wikimedia.org/wikipedia/en/6/67/Chicago_Bulls_logo.svg"),
//                Pair("nba-cle", "https://upload.wikimedia.org/wikipedia/en/f/f7/Cleveland_Cavaliers_2010.svg"),
//                Pair("nba-dal", "https://upload.wikimedia.org/wikipedia/en/9/97/Dallas_Mavericks_logo.svg"),
//                Pair("nba-den", "https://upload.wikimedia.org/wikipedia/en/7/76/Denver_Nuggets.svg"),
//                Pair("nba-det", "https://upload.wikimedia.org/wikipedia/en/1/1e/Detroit_Pistons_logo.svg"),
//                Pair("nba-gs", "https://upload.wikimedia.org/wikipedia/en/0/01/Golden_State_Warriors_logo.svg"),
//                Pair("nba-hou", "https://upload.wikimedia.org/wikipedia/en/2/28/Houston_Rockets.svg"),
//                Pair("nba-ind", "https://upload.wikimedia.org/wikipedia/en/1/1b/Indiana_Pacers.svg"),
//                Pair("nba-lac", "https://upload.wikimedia.org/wikipedia/en/6/66/Los_Angeles_Clippers_logo.svg"),
//                Pair("nba-lal", "https://upload.wikimedia.org/wikipedia/commons/c/c0/LosAngeles_Lakers_logo.svg"),
//                Pair("nba-mem", "https://upload.wikimedia.org/wikipedia/en/f/f1/Memphis_Grizzlies.svg"),
//                Pair("nba-mia", "https://upload.wikimedia.org/wikipedia/en/f/fb/Miami_Heat_logo.svg"),
//                Pair("nba-mil", "http://vignette4.wikia.nocookie.net/logopedia/images/4/4a/Milwaukee_Bucks_logo.svg"),
//                Pair("nba-min", "https://upload.wikimedia.org/wikipedia/en/7/78/Minnesota_Timberwolves.svg"),
//                Pair("nba-no", "http://stats.nba.com/media/img/teams/logos/NOP_logo.svg"),
//                Pair("nba-ny", "https://upload.wikimedia.org/wikipedia/de/e/e9/New-York-Knicks-Logo_%281995%29.svg"),
//                Pair("nba-okc", "https://upload.wikimedia.org/wikipedia/en/5/5d/Oklahoma_City_Thunder.svg"),
//                Pair("nba-orl", "https://upload.wikimedia.org/wikipedia/de/b/b3/Orlando_Magic.svg"),
//                Pair("nba-phi", "http://vignette1.wikia.nocookie.net/logopedia/images/3/36/Philadelphia_76ers_logo_%281997-2009%29.svg"),
//                Pair("nba-pho", "https://upload.wikimedia.org/wikipedia/de/d/dc/Phoenix_Suns_logo.svg"),
//                Pair("nba-por", "https://upload.wikimedia.org/wikipedia/en/7/74/Portland_Trail_Blazers.svg"),
//                Pair("nba-sac", "https://upload.wikimedia.org/wikipedia/en/c/c7/SacramentoKings.svg"),
//                Pair("nba-sa", "https://upload.wikimedia.org/wikipedia/en/a/a2/San_Antonio_Spurs.svg"),
//                Pair("nba-tor", "http://vignette1.wikia.nocookie.net/logopedia/images/3/36/Toronto_Raptors_logo.svg"),
//                Pair("nba-uta", "https://upload.wikimedia.org/wikipedia/en/d/d4/Utah_Jazz_script_logo%2C_%282010_%27new_look%27%29.svg"),
//                Pair("nba-was", "https://upload.wikimedia.org/wikipedia/de/9/98/Washington_Wizards.svg"))

        fun getTeamLogoUri(teamSlug:String) : Uri? {
            var threeLetterName = teamSlug.split("-")[1]
            when (threeLetterName){
                "bk" -> threeLetterName = "bkn"
                "gs" -> threeLetterName = "gsw"
                "no" -> threeLetterName = "nop"
                "ny" -> threeLetterName = "nyk"
                "sa" -> threeLetterName = "sas"
                "pho" -> threeLetterName = "phx"
            }
            val url = String.format("http://stats.nba.com/media/img/teams/logos/%s_logo.svg",
                    threeLetterName.toUpperCase())
            return Uri.parse(url)
//            if (teamLogos.containsKey(teamSlug))
//                return Uri.parse(teamLogos[teamSlug])
//            else
//                return null
        }

        fun getRequestBuilder(context: Context): GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable>? {
            return Glide.with(context)
                    .using(Glide.buildStreamModelLoader(Uri::class.java, context), InputStream::class.java)
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
        }
    }
}

fun Context.toast(message: String?) {
    message?.let {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

//fun Any.log(message: String) {
//    Log.d(message)
//}

fun FragmentManager.replaceFragment(fragmentContainerId: Int, newFragment: Fragment): Unit {
    val transaction = beginTransaction()
    transaction.replace(fragmentContainerId, newFragment, newFragment.tag)
    transaction.commit()
}

fun FragmentManager.addFragment(fragmentContainerId: Int, newFragment: Fragment): Unit {
    val transaction = beginTransaction()
    transaction.add(fragmentContainerId, newFragment, newFragment.tag)
    transaction.commit()
}


fun Date.addDays(numberOfDays: Int) {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DAY_OF_YEAR, numberOfDays)
    this.time = cal.timeInMillis
}

fun Int.ordinal(): String {
    val sufixes = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
    when (this % 100) {
        11, 12, 13 -> return this.toString() + "th"
        else -> return this.toString() + sufixes[this % 10]
    }
}

