package application.medical.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import application.medical.R;
import application.medical.interface_.ItemClickListener;
import application.medical.model.Hospital;

public class HospitalAdapter extends RecyclerView.Adapter {

    private List<Hospital> hospitalList;
    private ItemClickListener listener;

    public HospitalAdapter(List<Hospital> hospitalList, ItemClickListener listener) {
        this.hospitalList = hospitalList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((MyHolder)holder).bind(hospitalList.get(position));
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView_hospitalName;
        private TextView textView_hospitalRating;
        private TextView textView_hospitalLocation;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            textView_hospitalName = view.findViewById(R.id.textView_hospitalName);
            textView_hospitalRating = view.findViewById(R.id.textView_hospitalRating);
            textView_hospitalLocation = view.findViewById(R.id.textView_hospitalLocation);

            view.setOnClickListener(this);

        }


        public void bind(Hospital hospital) {
            textView_hospitalName.setText(hospital.getHospitalName());
            textView_hospitalRating.setText(hospital.getHospitalRating()+"");
            textView_hospitalLocation.setText(hospital.getHospitalLocation());
        }


        @Override
        public void onClick(View v) {
            listener.itemClicked(hospitalList.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
