package application.medical.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.medical.ApiClient;
import application.medical.R;
import application.medical.adapter.HospitalAdapter;
import application.medical.interface_.ApiInterface;
import application.medical.interface_.ItemClickListener;
import application.medical.model.Hospital;
import application.medical.retrofit.Example;
import application.medical.retrofit.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalActivity extends BaseActivity implements Callback<Example>, ItemClickListener<Hospital>, View.OnClickListener, OnSuccessListener<Location>, OnFailureListener {

    private RecyclerView recyclerView;
    private HospitalAdapter hospitalAdapter;
    private List<Hospital> hospitalList = new ArrayList<>();
    private Button button_currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);


        recyclerView = findViewById(R.id.recyclerView);
        button_currentLocation = findViewById(R.id.button_currentLocation);

        button_currentLocation.setOnClickListener(this);

        hospitalAdapter = new HospitalAdapter(hospitalList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(hospitalAdapter);

    }

    private void callService(String latLng) {
        materialDialog.show();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("location", latLng);
        hashMap.put("radius", "1500");
        hashMap.put("type", "hospital");
        hashMap.put("keyword", "hospital");
        hashMap.put("key", "AIzaSyBxTqht9GHx3cIWWSy0zfweGXi44SPNAOY");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Example> call = apiInterface.getNearbyLocations(hashMap);

        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Example> call, Response<Example> response) {
        materialDialog.hide();
        String status = response.body().getStatus();
        List<Result> resultsList = response.body().getResults();
        if (status.equals("OK") && !resultsList.isEmpty()) {
            button_currentLocation.setVisibility(View.GONE);
            for (int i = 0; i < resultsList.size(); i++) {
                String hospitalName = resultsList.get(i).getName();
                double hospitalLatitude = resultsList.get(i).getGeometry().getLocation().getLat();
                double hospitalLongitude = resultsList.get(i).getGeometry().getLocation().getLng();
                double hospitalRating = resultsList.get(i).getRating();
                String hospitalLocation = resultsList.get(i).getVicinity();

                hospitalList.add(new Hospital(hospitalName, hospitalLatitude, hospitalLongitude, hospitalLocation, hospitalRating));
            }
            hospitalAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No Results for current Location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<Example> call, Throwable t) {
        materialDialog.hide();
        Toast.makeText(this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClicked(Hospital hospital, int position) {
        startActivity(new Intent(this, MapsActivity.class).putExtra("hospital", hospital));
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this);
        fusedLocationProviderClient.getLastLocation().addOnFailureListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_currentLocation:
                if (isGPSEnabled()) {
                    getLocation();
                } else {
                    Toast.makeText(this, "Please Turn on GPS Location", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onSuccess(android.location.Location location) {
        if (location != null) {
            callService(location.getLatitude()+","+location.getLongitude());
            Toast.makeText(this, location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Can't get current Location", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
