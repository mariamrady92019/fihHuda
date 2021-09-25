package tool.mariam.fihhuda.azkar.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

import tool.mariam.fihhuda.R;
import tool.mariam.fihhuda.azkar.adapters.AzkarDetailsAdapter;
import tool.mariam.fihhuda.azkar.parsingJson.AzkarDataItem;
import tool.mariam.fihhuda.azkar.viewModels.AzkarViewModel;

public class AzkarDetailsActivity extends AppCompatActivity {

    protected TextView categoryName;
    protected RecyclerView azkarDetailsRecycler;
    private AzkarViewModel viewModel;
    String category;
    List<AzkarDataItem> azkar = new ArrayList<>();
    public static String EXTRA_CATEGORY_NAME = "category_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_azkar_details);
        viewModel = new ViewModelProvider(AzkarDetailsActivity.this).get(AzkarViewModel.class);
        initView();
        getDataIntented();
        getdataFromAzkarViewModel(category);
        buildRecycler(azkar);
    }

    private void getdataFromAzkarViewModel(String category) {
       azkar= viewModel.getAZkarByCategoryName(category);
    }

    private void buildRecycler(List<AzkarDataItem> azkar) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        AzkarDetailsAdapter azkarDetailsAdapter = new AzkarDetailsAdapter(azkar);
        azkarDetailsRecycler.setAdapter(azkarDetailsAdapter);
        azkarDetailsRecycler.setLayoutManager(layoutManager);
        layoutManager.setReverseLayout(true);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(azkarDetailsRecycler);
    }

    private void getDataIntented() {
        category = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);
        categoryName.setText(category);
    }

    private void initView() {
        categoryName = (TextView) findViewById(R.id.category_name);
        azkarDetailsRecycler = (RecyclerView) findViewById(R.id.azkar_details_recycler);
    }
}