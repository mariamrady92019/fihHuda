package com.example.fihhuda.azkar.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.fihhuda.R;
import com.example.fihhuda.azkar.adapters.AzkarDetailsAdapter;
import com.example.fihhuda.azkar.parsingJson.AzkarDataItem;
import com.example.fihhuda.azkar.viewModels.AzkarViewModel;

import java.util.ArrayList;
import java.util.List;

public class AzkarDetailsActivity extends AppCompatActivity {

    protected TextView categoryName;
    protected RecyclerView azkarDetailsRecycler;
    private AzkarViewModel viewModel;
    String category;
    List<AzkarDataItem> azkar = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_azkar_details);
        viewModel = new ViewModelProvider(AzkarDetailsActivity.this).get(AzkarViewModel.class);
         viewModel.setContext(this);
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
        category = getIntent().getStringExtra("categoryName");
        categoryName.setText(category);
    }

    private void initView() {
        categoryName = (TextView) findViewById(R.id.category_name);
        azkarDetailsRecycler = (RecyclerView) findViewById(R.id.azkar_details_recycler);
    }
}