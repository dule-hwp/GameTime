package hwp.gametime.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter
import de.hdodenhof.circleimageview.CircleImageView
import hwp.gametime.R
import hwp.gametime.models.Players

class PlayersTableAdapter(context: Context, players: Players, color: String?) : BaseTableAdapter() {

    private val playersData: MutableList<PlayerDataToDisplay>
    private val headers = arrayOf("Name", "Position", "Salary", "Uniform", "Height", "Weight",
            "Birth Date")

    private val widths = intArrayOf(130, 100, 100, 60, 70, 60, 100)
    private val density: Float


    private var mContext: Context

    private var mPlayers: Players

    private var mColor: String?

    init {
        mContext = context
        mColor = color
        density = context.resources.displayMetrics.density

        mPlayers = players

        playersData = arrayListOf()
        mPlayers.players
                .map {
                    arrayOf(it.firstName + "*" + it.lastName,
                            it.positionName,
                            it.humanizedSalary,
                            "#" + it.uniformNumber,
                            getHeight(it.height),
                            it.weight.toString() + "lbs",
                            it.birthDate.toString()
                    )
                }
                .mapTo(playersData, ::PlayerDataToDisplay)
    }

    private fun getHeight(height: Any?): String? {
        try {
            Log.d("height", height.toString())
            val playerHeight = height.toString().toInt()
            return String.format("%d\' %d\'\'", playerHeight / 12, playerHeight % 12)
        }
        catch(exception: NumberFormatException) {
            return "N/A"
        }
    }

    override fun getRowCount(): Int {
        return playersData.size - 1
    }

    override fun getColumnCount(): Int {
        return headers.size - 1
    }

    override fun getView(row: Int, column: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val itemViewType = getItemViewType(row, column)
        Log.d("itemViewType: ", itemViewType.toString())
        when (itemViewType) {
            0 -> view = getFirstHeader(row, column, convertView, parent)
            1 -> view = getHeader(row, column, convertView, parent)
            2 -> view = getFirstBody(row, column, convertView, parent)
            3 -> view = getBody(row, column, convertView, parent)
            else -> throw RuntimeException("No item view type: " + itemViewType)
        }
        return view
    }

    private fun getFirstHeader(row: Int, column: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_table_header_first, parent, false)
        }
        (convertView?.findViewById(android.R.id.text1) as TextView).text = headers[0]
        return convertView
    }

    private fun getLayoutInflater(): LayoutInflater {
        return LayoutInflater.from(mContext)
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
        convertView?.setBackgroundColor(getRowBackground(row))
        val first_last = playersData[row].data[column + 1]?.split("*")
        (convertView?.findViewById(android.R.id.text1) as TextView).text = first_last?.get(0)
        (convertView?.findViewById(android.R.id.text2) as TextView).text = first_last?.get(1)
        val ivPlayer = convertView?.findViewById(R.id.ivPlayerImage) as CircleImageView

        var url = mContext.getString(R.string.playerHeadShotUrl)
        url = String.format(url, mPlayers.players[row].firstName, mPlayers.players[row].lastName)
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
        convertView?.setBackgroundColor(getRowBackground(row))
        (convertView?.findViewById(android.R.id.text1) as TextView).text = playersData[row].data[column + 1]
        return convertView
    }

    private fun getRowBackground(row: Int): Int {
        if (mColor == null) {
            return if (row % 2 == 0) R.color.holo_light_button_normal else R.color.holo_light_button_pressed
        } else {
            return if (row % 2 == 0) Color.parseColor("#E0" + mColor?.toUpperCase())
            else Color.parseColor("#44" + mColor?.toUpperCase())
        }
    }

    override fun getWidth(column: Int): Int {
        return Math.round(widths[column + 1] * density)
    }

    override fun getHeight(row: Int): Int {
        val height: Int
        if (row == -1) {
            height = 35
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
        } else if (column == -1) {
            itemViewType = 2
        } else {
            itemViewType = 3
        }
        return itemViewType
    }

    override fun getViewTypeCount(): Int {
        return 5
    }
}

private class PlayerDataToDisplay(playerData: Array<String?>) {
    val data: Array<String?>

    init {
        data = playerData
    }
}