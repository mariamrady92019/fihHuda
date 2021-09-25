package tool.mariam.fihhuda.azkar.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tool.mariam.fihhuda.R

class AzkarViewHolder(itemView: View, onClickListener: AzkarFragmentAdapter.OnClickListener) :
    RecyclerView.ViewHolder(itemView) {
    private val zekrCategory: TextView = itemView.findViewById(R.id.zekr_category_name)

    private var data: String? = null

    init {
        itemView.setOnClickListener {
            data?.let {
                onClickListener.onItemClick(bindingAdapterPosition, it)
            }
        }
    }

    fun bind(item: String?) {
        data = item
        zekrCategory.text = item
    }

}