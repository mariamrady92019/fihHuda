package com.example.fihhuda.azkar.views;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fihhuda.R;
import com.example.fihhuda.azkar.adapters.AzkarFragmentAdapter;
import com.example.fihhuda.azkar.viewModels.AzkarViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AzkarFragment extends Fragment {


    protected RecyclerView azkarRecycler;
    AzkarViewModel azkarViewModel;
    View rootView;
    List<String> categories = new ArrayList<>();
    AzkarFragmentAdapter azkarFragmentAdapter;

    public AzkarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_azkar, container, false);

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        azkarViewModel = new ViewModelProvider(getActivity()).get(AzkarViewModel.class);
        azkarViewModel.setContext(getActivity());
       getDataFromViewModel();
        initView(rootView);

        //  pickFile();
        //  afterViews();
    }


    private void getDataFromViewModel()
    {
       categories= azkarViewModel.getCategories();
    }


    private void initView(View rootView) {
        azkarRecycler = (RecyclerView) rootView.findViewById(R.id.azkar_recycler);
         azkarFragmentAdapter = new AzkarFragmentAdapter(categories);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        azkarRecycler.setAdapter(azkarFragmentAdapter);
        azkarRecycler.setLayoutManager(layoutManager);
           onItemClick();
              }

    private void onItemClick() {
        azkarFragmentAdapter.setOnClickListener(new AzkarFragmentAdapter.OnClickListener() {
            @Override
            public void onItemClick(int pos, String categoryName) {
                // here take categoryname and start new activity
                Intent intent = new Intent(getActivity(),AzkarDetailsActivity.class);
                intent.putExtra("categoryName",categoryName);
                startActivity(intent);

            }
        });
    }
}


