package tool.mariam.fihhuda.azkar.views

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import tool.mariam.fihhuda.R
import tool.mariam.fihhuda.azkar.adapters.AzkarDetailsAdapter
import tool.mariam.fihhuda.azkar.viewModels.AzkarViewModel

class AzkarDetailsActivity : AppCompatActivity() {

    private val viewModel by viewModels<AzkarViewModel>()
    private val category: String? by lazy { intent.getStringExtra(EXTRA_CATEGORY_NAME) }
    private val tvCategoryName: TextView by lazy { findViewById(R.id.category_name) }
    private val adapter by lazy { AzkarDetailsAdapter(arrayListOf()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_azkar_details)

        viewModel.prepareAzkarByCategory(this, category)
        buildRecycler()
        tvCategoryName.text = category

        viewModel.azkars.observe(this) {
            adapter.updateData(it)
        }

    }

    private fun buildRecycler() {
        val azkarDetailsRecycler = findViewById<View>(R.id.azkar_details_recycler) as RecyclerView
        azkarDetailsRecycler.adapter = adapter
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(azkarDetailsRecycler)
    }


    companion object {
        const val EXTRA_CATEGORY_NAME = "category_name"
    }
}