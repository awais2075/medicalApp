package application.medical.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import application.medical.R;
import application.medical.adapter.BloodAdapter;
import application.medical.database.DatabaseHandler;
import application.medical.interface_.FirebaseInterface;
import application.medical.interface_.ItemClickListener;
import application.medical.model.Blood;

public class BloodActivity extends BaseActivity implements FirebaseInterface<Blood>, ItemClickListener<Blood>, MaterialSpinner.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private MaterialSpinner spinner;
    private BloodAdapter bloodAdapter;
    private List<Blood> bloodList = new ArrayList<>();
    String[] bloodTypesArray = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood);

        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.spinner);

        setSpinner();

        bloodAdapter = new BloodAdapter(bloodList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(bloodAdapter);

    }

    private void setSpinner() {
        bloodTypesArray = getResources().getStringArray(R.array.blood_groups);

        spinner.setItems(bloodTypesArray);
        spinner.setOnItemSelectedListener(this);
        /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_expandable_list_item_1, doctorTypesArray
        );
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);*/

    }

    private void addBloodToDB() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        FirebaseDatabase firebaseDatabase = databaseHandler.getInstance();


        String[] bloodTypes = getResources().getStringArray(R.array.blood_groups);

        for (int i = 0; i < bloodTypes.length; i++) {
            DatabaseReference databaseReference = databaseHandler.getReference(firebaseDatabase, "Blood", bloodTypes[i]);

            //firebaseDatabase.getReference().child(bloodTypes[i]);
            Blood blood1 = new Blood(databaseReference.push().getKey(), bloodTypes[i], "PIMS Islamabad", 10);
            Blood blood2 = new Blood(databaseReference.push().getKey(), bloodTypes[i], "Polyclinic Islamabad", 4);
            Blood blood3 = new Blood(databaseReference.push().getKey(), bloodTypes[i], "Ali Medical Islamabad", 8);
            Blood blood4 = new Blood(databaseReference.push().getKey(), bloodTypes[i], "Al-Shifa Islamabad", 11);
            Blood blood5 = new Blood(databaseReference.push().getKey(), bloodTypes[i], "Kalsoom International Islamabad", 30);

            databaseHandler.insert(databaseReference, blood1.getBloodId(), blood1);
            databaseHandler.insert(databaseReference, blood2.getBloodId(), blood2);
            databaseHandler.insert(databaseReference, blood3.getBloodId(), blood3);
            databaseHandler.insert(databaseReference, blood4.getBloodId(), blood4);
            databaseHandler.insert(databaseReference, blood5.getBloodId(), blood5);
        }
    }

    @Override
    public void getValue(List<Blood> list) {
        materialDialog.hide();
        if (list.isEmpty()) {
            findViewById(R.id.textView).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.textView).setVisibility(View.GONE);


            bloodList.addAll(list);
            bloodAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void itemClicked(Blood blood, int position) {

    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        Toast.makeText(this, bloodTypesArray[position] + "", Toast.LENGTH_SHORT).show();

        bloodList.clear();
        if (position != 0) {
            getBloodFromDB(bloodTypesArray[position]);
        }
    }

    private void getBloodFromDB(String bloodType) {
        materialDialog.show();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        FirebaseDatabase firebaseDatabase = databaseHandler.getInstance();
        DatabaseReference databaseReference = databaseHandler.getReference(firebaseDatabase, "Blood", bloodType);
        databaseHandler.view(databaseReference, "quantity", Blood.class);

    }

}
