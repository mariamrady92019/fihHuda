package tool.mariam.fihhuda.azkar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tool.mariam.fihhuda.R

class AzkarFragmentAdapter(
    private val categories: ArrayList<String>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<AzkarViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AzkarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_azkar, parent, false)
        return AzkarViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: AzkarViewHolder, position: Int) {
        holder.bind(categories[position])
    }


    override fun getItemCount() = categories.size

    fun updateData(it: List<String>) {
        categories.clear()
        categories.addAll(it)
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onItemClick(pos: Int, categoryName: String?)
    }

}