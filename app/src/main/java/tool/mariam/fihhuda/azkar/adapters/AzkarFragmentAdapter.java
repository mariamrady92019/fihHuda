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

public class AzkarFragmentAdapter extends RecyclerView.Adapter<AzkarFragmentAdapter.AzkarViewHolder> {

    List<String> categories = new ArrayList<>();
    OnClickListener onClickListener;

    public AzkarFragmentAdapter(List<String> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public AzkarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.azkar_recyclerfargment_item,parent,false);
        return new AzkarFragmentAdapter.AzkarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AzkarViewHolder holder, final int position) {
        holder.zekrCategory.setText(categories.get(position));
        if(onClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClick(position,holder.zekrCategory.getText().toString());
                }
            });
        }


    }

    public void  setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
    @Override
    public int getItemCount() {
        return categories.size();
    }




    public interface OnClickListener {
        void onItemClick(int pos , String categoryName);
    }

    public class AzkarViewHolder extends RecyclerView.ViewHolder {
          TextView zekrCategory ;

        public AzkarViewHolder(@NonNull View itemView) {
            super(itemView);
            zekrCategory= itemView.findViewById(R.id.zekr_category_name);
        }
    }



}
