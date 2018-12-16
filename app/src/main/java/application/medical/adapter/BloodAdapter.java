package application.medical.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import application.medical.R;
import application.medical.activity.BloodActivity;
import application.medical.interface_.ItemClickListener;
import application.medical.model.Blood;

public class BloodAdapter extends RecyclerView.Adapter {
    private List<Blood> bloodList;
    private ItemClickListener<Blood> listener;


    public BloodAdapter(List<Blood> bloodList, ItemClickListener<Blood> listener) {
        this.bloodList = bloodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blood, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyHolder)holder).bind(bloodList.get(position));
    }

    @Override
    public int getItemCount() {
        return bloodList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView_bloodType;
        private TextView textView_quantity;
        private TextView textView_hospitalName;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view) {
            textView_bloodType = view.findViewById(R.id.textView_bloodType);
            textView_quantity = view.findViewById(R.id.textView_quantity);
            textView_hospitalName = view.findViewById(R.id.textView_hospitalName);

            view.setOnClickListener(this);
        }


        public void bind(Blood blood) {
            textView_bloodType.setText(blood.getBloodType());
            textView_quantity.setText(blood.getQuantity()+" Bottle(s)");
            textView_hospitalName.setText(blood.getHospitalName());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
