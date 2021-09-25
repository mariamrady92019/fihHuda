package tool.mariam.fihhuda.tafseer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tool.mariam.fihhuda.R;
import tool.mariam.fihhuda.quran.fullQuranReadingModels.Ayah;
import tool.mariam.fihhuda.tafseer.tafseerSearchModel.forTafseerReadingInActivity.AyahsItem;

public class TafseerAllSuraAdapter extends RecyclerView.Adapter<TafseerAllSuraAdapter.TafseerFragmentAdapterViewHolde> {
    List<AyahsItem> AyatFortafseer;
    List<Ayah> tafseerOfAya;


    public TafseerAllSuraAdapter(List<AyahsItem> AyatFortafseer, List<Ayah> tafseerOfAya) {
        this.tafseerOfAya = tafseerOfAya;
        this.AyatFortafseer = AyatFortafseer;
    }


    @NonNull
    @Override
    public TafseerFragmentAdapterViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tafseersurah_activity, parent, false);
        return new TafseerFragmentAdapterViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TafseerFragmentAdapterViewHolde holder, int position) {

             holder.suarname.setText(this.tafseerOfAya.get(position).getText());
             holder.ayah.setText( this.AyatFortafseer.get(position).getText());



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
