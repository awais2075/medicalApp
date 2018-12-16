package application.medical.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import application.medical.R;
import application.medical.database.DatabaseHandler;
import application.medical.interface_.FirebaseInterface;
import application.medical.model.Doctor;

public class SignInActivity extends BaseActivity implements View.OnClickListener, FirebaseInterface<Doctor> {

    private SignInButton button_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        button_signIn = findViewById(R.id.button_signIn);


        button_signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_signIn:
                signIn();
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        checkStatus();
    }


    @Override
    public void getValue(List<Doctor> model) {
    }
}
