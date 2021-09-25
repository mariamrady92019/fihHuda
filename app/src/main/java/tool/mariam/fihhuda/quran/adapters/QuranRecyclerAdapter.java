package tool.mariam.fihhuda.quran.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tool.mariam.fihhuda.R;
import tool.mariam.fihhuda.quran.fullQuranReadingModels.Surah;

public class QuranRecyclerAdapter extends
        RecyclerView.Adapter<QuranRecyclerAdapter.QuranViewHolder>{

    List<Surah> surasList = new ArrayList<>();
    OnClickListener onClickListener;


    public QuranRecyclerAdapter(List<Surah> surasList) {
        this.surasList = surasList;
    }

    @NonNull
    @Override
    public QuranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quran_recycleritem,parent,false);
        return new QuranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuranViewHolder holder, final int position) {

        Surah surah = surasList.get(position);
        holder.surahName.setText(surah.getName());
        holder.surahNumber.setText(position+1+"");
        if(surah.getRevelationType().contains("Medinan")){
        holder.surahIdentitiy.setText("مدنية");}
        else{
            holder.surahIdentitiy.setText("مكية");
        }
        holder.surahAyasNumber.setText(surah.getAyahs().size()+"");
//       holder.qraahIcon.setVisibility(View.GONE);

        if (onClickListener !=null){
         holder.eqraaLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
             onClickListener.onItemClick(position);
             }
         });
         holder.listenIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onEqraaButtonClicked(position);
                    Log.e("e", "onClick: button clicked");
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        return surasList.size();
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }




    public interface OnClickListener {
        void onItemClick(int pos);
        void onEqraaButtonClicked(int pos);
    }


    class QuranViewHolder extends RecyclerView.ViewHolder {

       protected  View eqraaLayout ;
        protected TextView surahNumber;
        protected TextView surahName;
        protected TextView surahIdentitiy;
        protected TextView surahAyasNumber;
        protected ImageView qraahIcon;
        protected ImageView listenIcon;

        public QuranViewHolder(@NonNull View rootView) {
            super(rootView);
            eqraaLayout= (View) rootView.findViewById(R.id.eqraa_layout);

            surahNumber = (TextView) rootView.findViewById(R.id.surah_number);
            surahName = (TextView) rootView.findViewById(R.id.surah_name);
            surahIdentitiy = (TextView) rootView.findViewById(R.id.surah_identitiy);
            surahAyasNumber = (TextView) rootView.findViewById(R.id.surah_AyasNumber);
           // qraahIcon = (ImageView) rootView.findViewById(R.id.qraah_icon);
            listenIcon = (ImageView) rootView.findViewById(R.id.listen_icon);
        }
    }



}
