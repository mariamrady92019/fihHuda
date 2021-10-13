package com.example.fihhuda.tafseer.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fihhuda.R;
import com.example.fihhuda.quran.fullQuranReadingModels.Ayah;
import com.example.fihhuda.tafseer.tafseerSearchModel.SearchModel;
import com.example.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity.AyahsItem;

import java.util.ArrayList;
import java.util.List;

public class TafseerAllSuraAdapter extends RecyclerView.Adapter<TafseerAllSuraAdapter.TafseerFragmentAdapterViewHolde> {
    List<AyahsItem> AyatFortafseer;
    List<Ayah> tafseerOfAya;

    private int highlightItem = 0;

    public void toggleSelection(int pos) {
        this.highlightItem = pos;
    }

    public TafseerAllSuraAdapter(List<AyahsItem>AyatFortafseer, List<Ayah> tafseerOfAya) {
        this.tafseerOfAya= tafseerOfAya;
        this.AyatFortafseer =AyatFortafseer;
    }


    @NonNull
    @Override
    public TafseerFragmentAdapterViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tafseersurah_activity, parent, false);
        return new TafseerFragmentAdapterViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final  TafseerFragmentAdapterViewHolde holder, final int position) {

             holder.suarname.setText(this.tafseerOfAya.get(position).getText()+
                     "("+this.tafseerOfAya.get(position).getNumberInSurah()+")");
             holder.ayah.setText( this.AyatFortafseer.get(position).getText());

        if (position == highlightItem) {
            holder.itemView.setSelected(true);
            holder.itemView.setBackgroundResource(R.color.highlight);

            holder.itemView.postDelayed(new Runnable(){
                public void run(){
                    holder.itemView.setBackgroundResource(R.drawable.whiteborder);

                }
            },2000);
        } else {
            holder.itemView.setSelected(false);
        }


    }

    @Override
    public int getItemCount() {
        return this.AyatFortafseer.size();
    }




    public class TafseerFragmentAdapterViewHolde extends RecyclerView.ViewHolder {

        protected TextView suarname;
        protected TextView ayah;

        public TafseerFragmentAdapterViewHolde(@NonNull View itemView) {

            super(itemView);
            suarname= (TextView) itemView.findViewById(R.id.searched_ayah_souraName);
            ayah = (TextView) itemView.findViewById(R.id.searched_ayah);

        }

    }


}
