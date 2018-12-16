package application.medical.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import application.medical.R;
import application.medical.util.Permission;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private CardView cardView_blood;
    private CardView cardView_hospital;
    private CardView cardView_signOut;
    private CardView cardView_doctor;

    private Permission permission;
    private final int PERMISSION_REQUEST_CODE = 100;
    private final String[] MULTIPLE_PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView_blood = findViewById(R.id.cardView_blood);
        cardView_doctor = findViewById(R.id.cardView_doctor);
        cardView_hospital = findViewById(R.id.cardView_hospital);
        cardView_signOut = findViewById(R.id.cardView_signOut);

        cardView_blood.setOnClickListener(this);
        cardView_doctor.setOnClickListener(this);
        cardView_hospital.setOnClickListener(this);
        cardView_signOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardView_hospital:
                permission = new Permission(this, MULTIPLE_PERMISSIONS, PERMISSION_REQUEST_CODE);
                if (permission.checkPermissions()) {
                    startActivity(new Intent(this, HospitalActivity.class));
                } else {
                    permission.requestPermissions();
                }
                break;
            case R.id.cardView_blood:
                startActivity(new Intent(this, BloodActivity.class));
                break;
            case R.id.cardView_doctor:
                startActivity(new Intent(this, DoctorActivity.class));
                break;
            case R.id.cardView_signOut:
                signOut();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permission.check(requestCode, grantResults)) {
            startActivity(new Intent(this, HospitalActivity.class));
        }else {
            Toast.makeText(this, "Permissions Denied, Turn on Explicitly", Toast.LENGTH_SHORT).show();
        }
    }
}
