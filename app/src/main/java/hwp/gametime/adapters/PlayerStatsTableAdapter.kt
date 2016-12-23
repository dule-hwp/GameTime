package hwp.gametime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter
import de.hdodenhof.circleimageview.CircleImageView
import hwp.gametime.R
import hwp.gametime.models.GameLogs
import hwp.gametime.models.Team
import java.util.*


class PlayerStatsTableAdapter(context: Context, gameLogs: GameLogs) : BaseTableAdapter() {

    private val teams: Array<TeamRoster>
    private val headers = arrayOf("Name", "Min","PTS", "ASS", "OR", "DR", "TR", "BL",
            "FGM", "FGA", "FGP")

    private val widths = intArrayOf(130, 40, 40, 40, 40, 40, 40 , 40, 40, 40, 40)
    private val density: Float

    private var mGameLogs: GameLogs

    private var mContext: Context

    init {
        mContext = context
        mGameLogs = gameLogs
        teams = arrayOf(TeamRoster(mGameLogs.homeTeams[0]), TeamRoster(mGameLogs.awayTeams[0]))

        density = context.resources.displayMetrics.density

        for ((index, playerStats) in mGameLogs.gameLogs.withIndex()) {
            val player = mGameLogs.players[index]
            val playerName = player.firstName + "*" + player.lastName
            val arrPlayerStatsToDisplay = arrayOf(
                    playerStats.timePlayedTotal!!/60,
                    playerStats.points,
                    playerStats.assists,
                    playerStats.reboundsOffensive,
                    playerStats.reboundsDefensive,
                    playerStats.reboundsTotal,
                    playerStats.blocks,
                    playerStats.fieldGoalsMade,
                    playerStats.fieldGoalsAttempted,
                    (playerStats.fieldGoalsPct!! * 100).toInt()
                    )
            if (playerStats.teamId == teams[0].team.id)
                teams[0].list.add(PlayerStatToDisplay(playerName, arrPlayerStatsToDisplay))
            else
                teams[1].list.add(PlayerStatToDisplay(playerName, arrPlayerStatsToDisplay))
        }
    }

    override fun getRowCount(): Int {
        return mGameLogs.players.size + teams.size
    }

    override fun getColumnCount(): Int {
        return headers.size-1
    }

    override fun getView(row: Int, column: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        when (getItemViewType(row, column)) {
            0 -> view = getFirstHeader(row, column, convertView, parent)
            1 -> view = getHeader(row, column, convertView, parent)
            2 -> view = getFirstBody(row, column, convertView, parent)
            3 -> view = getBody(row, column, convertView, parent)
            4 -> view = getRowView(row, column, convertView, parent)
            else -> throw RuntimeException("wtf?")
        }
        return view
    }

    private fun getLayoutInflater(): LayoutInflater {
        return LayoutInflater.from(mContext)
    }

    private fun getFirstHeader(row: Int, column: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_table_header_first, parent, false)
        }
        (convertView?.findViewById(android.R.id.text1) as TextView).text = headers[0]
        return convertView
    }

    private fun getHeader(row: Int, column: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_table_header, parent, false)
        }
        (convertView?.findViewById(android.R.id.text1) as TextView).text = headers[column + 1]
        return convertView
    }

    private fun getFirstBody(row: Int, column: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_table_first, parent, false)
        }
        convertView?.setBackgroundResource(if (row % 2 == 0) R.drawable.bg_table_color1 else R.drawable.bg_table_color2)
        val first_last = getDevice(row).playerName.split("*")
        val first :String = first_last[0].split("(?=\\p{Upper})", "-").last()
        val last :String = first_last[1].split("(?=\\p{Upper})", "-").last()
        (convertView?.findViewById(android.R.id.text1) as TextView).text = first
        (convertView?.findViewById(android.R.id.text2) as TextView).text = last

        val ivPlayer = convertView?.findViewById(R.id.ivPlayerImage) as CircleImageView
        var url = mContext.getString(R.string.playerHeadShotUrl)
        url = String.format(url, first, last)

        Glide.with(mContext)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.ic_nba_logo)
                .centerCrop()
                .into(ivPlayer)
        return convertView
    }

    private fun getBody(row: Int, column: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false)
        }
        convertView?.setBackgroundResource(if (row % 2 == 0) R.drawable.bg_table_color1 else R.drawable.bg_table_color2)
        (convertView?.findViewById(android.R.id.text1) as TextView).text = getDevice(row).data[column].toString()
        return convertView
    }

    private fun getRowView(row: Int, column: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_table_family, parent, false)
        }
        val string: String
        if (column == -1) {
            string = getFamily(row).team.name!!
        } else {
            string = ""
        }
        (convertView?.findViewById(android.R.id.text1) as TextView).text = string
        return convertView
    }

    override fun getWidth(column: Int): Int {
        return Math.round(widths[column+1] * density)
    }

    override fun getHeight(row: Int): Int {
        val height: Int
        if (row == -1) {
            height = 35
        } else if (isFamily(row)) {
            height = 25
        } else {
            height = 60
        }
        return Math.round(height * density)
    }

    override fun getItemViewType(row: Int, column: Int): Int {
        val itemViewType: Int
        if (row == -1 && column == -1) {
            itemViewType = 0
        } else if (row == -1) {
            itemViewType = 1
        } else if (isFamily(row)) {
            itemViewType = 4
        } else if (column == -1) {
            itemViewType = 2
        } else {
            itemViewType = 3
        }
        return itemViewType
    }

    private fun isFamily(row: Int): Boolean {
        var row = row
        var family = 0
        while (row > 0) {
            row -= teams[family].list.size + 1
            family++
        }
        return row == 0
    }

    private fun getFamily(row: Int): TeamRoster {
        var row = row
        var family = 0
        while (row >= 0) {
            row -= teams[family].list.size + 1
            family++
        }
        return teams[family - 1]
    }

    private fun getDevice(row: Int): PlayerStatToDisplay {
        var row = row
        var family = 0
        while (row >= 0) {
            row -= teams[family].list.size + 1
            family++
        }
        family--
        return teams[family].list[row + teams[family].list.size]
    }

    override fun getViewTypeCount(): Int {
        return 5
    }
}

private class TeamRoster(pTeam: Team) {
    var list: MutableList<PlayerStatToDisplay>
    val team: Team

    init {
        list = mutableListOf<PlayerStatToDisplay>()
        team = pTeam
    }
}

private class PlayerStatToDisplay(name: String,playerData: Array<Int?>) {
    val data: Array<Int?>
    val playerName:String

    init {
        data = playerData
        playerName = name
    }
}
