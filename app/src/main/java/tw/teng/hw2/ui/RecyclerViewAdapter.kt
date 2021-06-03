package tw.teng.hw2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.teng.hw2.R
import tw.teng.hw2.databinding.PassItemBinding
import tw.teng.hw2.databinding.TitleItemBinding
import tw.teng.hw2.resource.repository.model.HourPass
import tw.teng.hw2.resource.repository.model.ListItem
import tw.teng.hw2.resource.repository.model.TitleItem
import tw.teng.hw2.resource.utils.FormatUtils

class RecyclerViewAdapter internal constructor(
    private var itemList: MutableList<ListItem>, private val mListener: ListItemAdapterListener
) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    companion object {
        const val TYPE_PASS = 0
        const val TYPE_TITLE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_PASS -> {
                val passBinding = PassItemBinding.inflate(inflater, parent, false)
                PassViewHolder(passBinding)
            }
            TYPE_TITLE -> {
                val titleBinding = TitleItemBinding.inflate(inflater, parent, false)
                TitleViewHolder(
                    titleBinding
                )
            }
            else -> {
                val passBinding = PassItemBinding.inflate(inflater, parent, false)
                PassViewHolder(
                    passBinding
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

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_PASS -> (viewHolder as PassViewHolder).bindData(itemList, position)
            TYPE_TITLE -> (viewHolder as TitleViewHolder).bindData(itemList, position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItems(listItems: MutableList<ListItem>) {
        itemList = listItems
    }

    open inner class MyViewHolder : RecyclerView.ViewHolder {
        private var passItemBinding: PassItemBinding? = null
        var titleItemBinding: TitleItemBinding? = null

        constructor(binding: PassItemBinding) : super(binding.root) {
            passItemBinding = binding
        }

        constructor(binding: TitleItemBinding) : super(binding.root) {
            titleItemBinding = binding
        }
    }

    open inner class PassViewHolder(itemView: PassItemBinding) : MyViewHolder(itemView) {

        private val tvPass: TextView = itemView.tvPass
        private val tvRp: TextView = itemView.tvRp
        private val btn: Button = itemView.btn

        fun bindData(list: List<ListItem>, position: Int) {
            val item = list[position] as HourPass
            tvPass.text = item.name
            val rpDisplay = FormatUtils.getRpDecimalFormat(item.rp)
            tvRp.text = ("Rp $rpDisplay")
            btn.isEnabled = item.enable
            if (!btn.isEnabled) {
                btn.text = itemView.context.getString(R.string.activate)
            }
            btn.setOnClickListener {
                (list[position] as HourPass).enable = false
                notifyItemChanged(position)
                mListener.onItemClick(position)
            }
        }
    }

    inner class TitleViewHolder(itemView: TitleItemBinding) : MyViewHolder(itemView) {
        private val tvTitle: TextView = itemView.tvTitle

        fun bindData(list: List<ListItem>, position: Int) {
            val item = list[position]
            tvTitle.text = (item as TitleItem).title
        }
    }

    internal interface ListItemAdapterListener {
        fun onItemClick(position: Int)
    }
}