package tool.mariam.fihhuda.azkar.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import tool.mariam.fihhuda.R
import tool.mariam.fihhuda.azkar.adapters.AzkarFragmentAdapter
import tool.mariam.fihhuda.azkar.viewModels.AzkarViewModel
import tool.mariam.fihhuda.azkar.views.AzkarDetailsActivity.EXTRA_CATEGORY_NAME


class AzkarFragment : Fragment(R.layout.fragment_azkar) {

    private val azkarViewModel by viewModels<AzkarViewModel>()

    var adapter: AzkarFragmentAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }


    private fun initView(rootView: View) {
        val azkarRecycler = rootView.findViewById<View>(R.id.rvAzkar) as RecyclerView
        adapter = AzkarFragmentAdapter(azkarViewModel.categories,
            object : AzkarFragmentAdapter.OnClickListener {
                override fun onItemClick(pos: Int, categoryName: String?) {
                    val intent = Intent(activity, AzkarDetailsActivity::class.java)
                    intent.putExtra(EXTRA_CATEGORY_NAME, categoryName)
                    startActivity(intent)
                }

            })
        azkarRecycler.adapter = adapter


    }


}