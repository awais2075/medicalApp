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
import application.medical.model.Doctor;

public class DoctorAdapter extends RecyclerView.Adapter {

    private List<Doctor> doctorList;
    private ItemClickListener listener;


    public DoctorAdapter(List<Doctor> doctorList, ItemClickListener listener) {
        this.doctorList = doctorList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((MyHolder)holder).bind(doctorList.get(position));
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView_doctorName;
        private TextView textView_doctorRating;
        private TextView textView_doctorEmail;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            textView_doctorName = view.findViewById(R.id.textView_doctorName);
            textView_doctorRating = view.findViewById(R.id.textView_doctorRating);
            textView_doctorEmail = view.findViewById(R.id.textView_doctorEmail);

            view.setOnClickListener(this);

        }


        public void bind(Doctor doctor) {
            textView_doctorName.setText(doctor.getDoctorName());
            textView_doctorRating.setText(doctor.getDoctorRating());
            textView_doctorEmail.setText(doctor.getDoctorEmail());
        }


        @Override
        public void onClick(View v) {
            listener.itemClicked(doctorList.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
