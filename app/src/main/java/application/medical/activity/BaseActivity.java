package application.medical.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import application.medical.R;

public class BaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {


    private GoogleApiClient apiClient;
    private final int SIGN_IN_REQUEST_CODE = 111;
    protected MaterialDialog materialDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        setupDialog(false);
    }

    private void init() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
    }

    protected void signIn() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(apiClient), SIGN_IN_REQUEST_CODE);
    }

    protected void signOut() {
        Auth.GoogleSignInApi.signOut(apiClient).setResultCallback(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed : "+connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResult(@NonNull Status status) {
        Toast.makeText(this, "SignOut", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            String name = account.getDisplayName();
            String email = account.getEmail();

            Toast.makeText(this, name+"", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    protected void checkStatus() {
        OptionalPendingResult<GoogleSignInResult> result = Auth.GoogleSignInApi.silentSignIn(apiClient);

        if (result.isDone()) {
            handleSignInResult(result.get());
        } else {
            result.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signOut:
                signOut();
                break;
        }
        return true;
    }

    private void setupDialog(Boolean isHorizontal) {
        materialDialog = new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(R.string.content)
                .progress(true, 0)
                .progressIndeterminateStyle(isHorizontal)
                .build();
    }
}
