package tool.mariam.fihhuda.azkar.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tool.mariam.fihhuda.R;
import tool.mariam.fihhuda.azkar.parsingJson.AzkarDataItem;

public class AzkarDetailsAdapter extends RecyclerView.Adapter<AzkarDetailsAdapter.AzkarDetailsAdapterViewHolde> {
    ArrayList<AzkarDataItem> list = new ArrayList<>();

    public AzkarDetailsAdapter(ArrayList<AzkarDataItem> list) {
        this.list = list;
    }


    public void updateData(List<AzkarDataItem> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AzkarDetailsAdapterViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.azkar_details_activity_item, parent, false);
        return new AzkarDetailsAdapter.AzkarDetailsAdapterViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AzkarDetailsAdapterViewHolde holder, int position) {


        AzkarDataItem zexr = list.get(position);
        holder.zekr.setText(zexr.getZekr());
        holder.fadl.setText(zexr.getDescription() + "");
        holder.count.setText(zexr.getCount().equals("") ? "(مرة واحدة)" : "(" + zexr.getCount() + "مرات)");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AzkarDetailsAdapterViewHolde extends RecyclerView.ViewHolder {

        protected TextView zekr;
        protected TextView fadl;
        protected TextView count;

        public AzkarDetailsAdapterViewHolde(@NonNull View itemView) {

            super(itemView);
            zekr = itemView.findViewById(R.id.zekr);
            fadl = itemView.findViewById(R.id.fadl);
            count = itemView.findViewById(R.id.count);

        }

    }
}
