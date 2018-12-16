package application.medical.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import application.medical.R;
import application.medical.adapter.DoctorAdapter;
import application.medical.database.DatabaseHandler;
import application.medical.interface_.FirebaseInterface;
import application.medical.interface_.ItemClickListener;
import application.medical.model.Doctor;

public class DoctorActivity extends BaseActivity implements ItemClickListener<Doctor>, FirebaseInterface<Doctor>, AdapterView.OnItemSelectedListener, MaterialSpinner.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private MaterialSpinner spinner;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList = new ArrayList<>();
    String[] doctorTypesArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);


        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.spinner);

        setSpinner();

        doctorAdapter = new DoctorAdapter(doctorList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(doctorAdapter);

    }

    private void setSpinner() {
        doctorTypesArray = getResources().getStringArray(R.array.doctor_types);

        spinner.setItems(doctorTypesArray);
        spinner.setOnItemSelectedListener(this);
        /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_expandable_list_item_1, doctorTypesArray
        );
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);*/

    }

    @Override
    public void itemClicked(Doctor doctor, int position) {

    }

    @Override
    public void getValue(List<Doctor> list) {
        materialDialog.hide();
        if (list.isEmpty()) {
            findViewById(R.id.textView).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.textView).setVisibility(View.GONE);


            doctorList.addAll(list);
            doctorAdapter.notifyDataSetChanged();
        }

    }


    private void getDoctorsFromDB(String doctorType) {
        materialDialog.show();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        FirebaseDatabase firebaseDatabase = databaseHandler.getInstance();
        DatabaseReference databaseReference = databaseHandler.getReference(firebaseDatabase, "Doctor", doctorType);
        databaseHandler.view(databaseReference, "doctorRating", Doctor.class);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, doctorTypesArray[position] + "", Toast.LENGTH_SHORT).show();

        doctorList.clear();
        if (position != 0) {
            getDoctorsFromDB(doctorTypesArray[position]);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        Toast.makeText(this, doctorTypesArray[position] + "", Toast.LENGTH_SHORT).show();

        doctorList.clear();
        if (position != 0) {
            getDoctorsFromDB(doctorTypesArray[position]);
        }

    }
}
