package com.example.fihhuda.quran.views;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fihhuda.R;
import com.example.fihhuda.quran.adapters.QuranRecyclerAdapter;
import com.example.fihhuda.quran.fullQuranReadingModels.FullQuran;
import com.example.fihhuda.quran.fullQuranReadingModels.Surah;
import com.example.fihhuda.quran.viewsModel.QuranViewModel;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuranFragment extends Fragment implements View.OnClickListener {


    protected View rootView;
    protected RecyclerView QuranRecycler;
    protected Button searchBar;
    protected Toolbar toolbar;
    BottomSheetLayout bottomSheet;
    QuranRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public static QuranViewModel viewModel;
    public static String  shikh_name="الشيخ مشاري راشد" ;
    int shikh_id=1;


    public QuranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_quran, container, false);


        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(QuranViewModel.class);
        viewModel.context = getActivity();
        initView(rootView);
        getData();
        observeQuranLiveData();


    }

    public void getData() {

        viewModel.getAllQuranFromJson();

    }


    public void observeQuranLiveData() {
        FullQuran fullQuran = viewModel.fullQuran.getValue();
        //the next line is to get lest of suars from fullquraan
        // class that parsed from jason in viewmodel that observen in the line before
        List<Surah> surahs = fullQuran.getData().getSurahs();
        String message = viewModel.message.getValue();
        initRecyclerView(surahs);
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void initRecyclerView(List<Surah> list) {
        QuranRecycler = (RecyclerView) rootView.findViewById(R.id.Quran_Recycler);
        adapter = new QuranRecyclerAdapter(list);
        layoutManager = new LinearLayoutManager(getActivity());
        QuranRecycler.setAdapter(adapter);
        QuranRecycler.setLayoutManager(layoutManager);
        onItemClick(list);
    }

    public void onItemClick(final List<Surah> list) {
        adapter.setOnClickListener(new QuranRecyclerAdapter.OnClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(
                        getContext(), SuraDetailsActivity.class);
                intent.putExtra("position", pos);
                intent.putExtra("name", list.get(pos).getName());
                intent.putExtra("shekh_id", shikh_id);
                startActivity(intent);


            }

            @Override
            public void onEqraaButtonClicked(int pos) {
             Intent intent = new Intent(
                        getContext(), ListenDetailsActivity.class);
                intent.putExtra("position", pos);
                intent.putExtra("name", list.get(pos).getName());
                intent.putExtra("shekh_id", shikh_id);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    private void initView(View rootView) {
     searchBar = (Button) rootView.findViewById(R.id.search_bar);
         bottomSheet = (BottomSheetLayout) rootView.findViewById(R.id.bottomsheet);
       searchBar.setOnClickListener(QuranFragment.this);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(QuranFragment.this);
        QuranRecycler = (RecyclerView) rootView.findViewById(R.id.Quran_Recycler);

    }

     void showPopUpMenue(View view){
         PopupMenu popup = new PopupMenu(getActivity(), view);
         // popup.setOnMenuItemClickListener(this);
         popup.inflate(R.menu.popup_menu);
         popup.show();
         searchBar.setText("clicked");
         Log.e("error","clicked");
         Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();

    }

     void createBottomSheet(){
         final MenuSheetView menuSheetView =
                 new MenuSheetView(getActivity(), MenuSheetView.MenuType.LIST, "",
                         new MenuSheetView.OnMenuItemClickListener() {
                     @Override
                     public boolean onMenuItemClick(MenuItem item) {
                         searchBar.setText(item.getTitle());
                         shikh_name=item.getTitle()+"";

                            getShikh_id();
                         Log.e("shekh id :",shikh_id+"");

                         Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                //create list of sheikh name , get name from bottomsheet
                         // and get position from list and pass torequst api
                         if (bottomSheet.isSheetShowing()) {
                             bottomSheet.dismissSheet();
                         }


                         return true;
                     }
                 });
         menuSheetView.inflateMenu(R.menu.popup_menu);
         bottomSheet.showWithSheetView(menuSheetView);
        //    bottomSheet.setContentView(rootView);

     }

    private void getShikh_id() {
        List<String> names = getShikh_names();
        for (int i = 0;i<getShikh_names().size();i++) {
            if(shikh_name.trim().contains(names.get(i))){
                shikh_id=i+1;
            }

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.search_bar) {
           //showPopUpMenue(view);
          createBottomSheet();


    } }

    List<String> getShikh_names (){

        List<String> names = new ArrayList<>();

        names.add("مشارى العفاسى");
        names.add("ماهر المعيقلي");
        names.add("أحــمد العجمى");
        names.add("سعد الغامدى");
        names.add("عبدالرحمن السديسى");
        names.add("محــمد جـــبريــل");
        names.add("عبدالــباسط عبدالصمد");
        names.add("محمد صديق المنشاوى");
        names.add("فـــــارس عـــباد");
        names.add("وديــــع اليمني");
        names.add("مـــحمد الطبلاوى");
        names.add("أحــمد الحذيفى");
        names.add("نــاصر القطامي");
        names.add("عبدالله الجهنى");
        names.add("ياسر الدوسري");
        names.add("الشيخ بندر بليلة");
        names.add("سعود الشريم");
        names.add("إدريس أبكر");
        names.add("محمود خليل الحصري");
        names.add("أحمد خضر الطرابلسي");


        return names;
    }


}
