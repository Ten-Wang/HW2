package tw.teng.hw2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.teng.hw2.R
import tw.teng.hw2.resource.repository.model.TitleItem
import tw.teng.hw2.resource.repository.model.HourPass
import tw.teng.hw2.resource.repository.model.ListItem
import java.text.DecimalFormat

class RecyclerViewAdapter internal constructor(
    private var itemList: MutableList<ListItem>, private val mListener: ListItemAdapterListener
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    companion object {
        const val TYPE_PASS = 0
        const val TYPE_TITLE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_PASS -> {
                PassViewHolder(
                    inflater.inflate(R.layout.pass_item, parent, false)
                )
            }
            TYPE_TITLE -> {
                TitleViewHolder(
                    inflater.inflate(R.layout.title_item, parent, false)
                )
            }
            else -> {
                PassViewHolder(
                    inflater.inflate(R.layout.pass_item, parent, false)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is HourPass -> {
                TYPE_PASS
            }
            is TitleItem -> {
                TYPE_TITLE
            }
            else -> {
                super.getItemViewType(position)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_PASS -> (viewHolder as PassViewHolder).bindData(itemList, position)
            TYPE_TITLE -> (viewHolder as TitleViewHolder).bindData(itemList, position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    open inner class PassViewHolder(itemView: View) : ViewHolder(itemView) {
        private val tvPass: TextView = itemView.findViewById(R.id.tv_pass)
        private val tvRp: TextView = itemView.findViewById(R.id.tv_rp)
        private val btn: Button = itemView.findViewById(R.id.btn)

        fun bindData(list: List<ListItem>, position: Int) {
            val item = list[position] as HourPass
            tvPass.text = item.name
            val mDecimalFormat = DecimalFormat("###,###,###,###")
            val rpDisplay = mDecimalFormat.format(item.rp).replace(",", ".")
            tvRp.text = ("Rp $rpDisplay")
            btn.setOnClickListener {
                btn.isEnabled = false
                btn.text = itemView.context.getString(R.string.activate)
                mListener.onItemClick(position)
            }
        }
    }

    inner class TitleViewHolder(itemView: View) : ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)

        fun bindData(list: List<ListItem>, position: Int) {
            val item = list[position]
            tvTitle.text = (item as TitleItem).title

        }
    }

    internal interface ListItemAdapterListener {
        fun onItemClick(position: Int)
    }
}
