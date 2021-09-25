package tool.mariam.fihhuda.tafseer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tool.mariam.fihhuda.R;
import tool.mariam.fihhuda.tafseer.tafseerSearchModel.SearchModel;

public class TafseerFragmentAdapter extends RecyclerView.Adapter<TafseerFragmentAdapter.TafseerFragmentAdapterViewHolde> {

    List<SearchModel> list = new ArrayList<>();
    onAyahClickListener onAyahClickListener;

    public TafseerFragmentAdapter(List<SearchModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TafseerFragmentAdapterViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_ayah_fargment, parent, false);
        return new TafseerFragmentAdapterViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TafseerFragmentAdapterViewHolde holder, int position) {

              final SearchModel ayah = list.get(position);
              holder.suarname.setText(ayah.getSurahName());

              holder.ayah.setText(ayah.getAyah().getText());

              if (onAyahClickListener != null) {
                  holder.itemView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          onAyahClickListener.onAyahClick(ayah);
                      }
                  });
              }
               }

    @Override
    public int getItemCount() {

        return list.size();
    }


    public void setOnAyahClickListener(TafseerFragmentAdapter.onAyahClickListener onAyahClickListener) {
        this.onAyahClickListener = onAyahClickListener;
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

 public interface onAyahClickListener{
        public void onAyahClick(SearchModel model);
    }
}
