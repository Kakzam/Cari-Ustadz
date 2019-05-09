package cari_ustadz.pondokit.id.cari_ustadz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cari_ustadz.pondokit.id.cari_ustadz.activities.PlaceSelectionActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMenuRegistrasi, btnLogin;
    EditText etPasswordLogin, etEmailLogin;

    //Autentifikasi firebase
    private FirebaseAuth mAuth;

    //dabase sementara
    private DatabaseReference mUserDataBase;

    //Proses Loading
    private ProgressDialog progressDialog;

    //memanggil penyimpanan sementara
    private cari_ustadz.pondokit.id.cari_ustadz.PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setView();
        setOnClick();

        //activity yang sudah login tidak perlu login lagi
        prefManager = new  cari_ustadz.pondokit.id.cari_ustadz.PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()){
            prefManager.setIsFirstTimeLaunch(false);
            sendTo(getApplicationContext(), DasboardActivity.class);
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        mUserDataBase = FirebaseDatabase.getInstance().getReference().child("Users");


    }

    private void sendTo(Context layout_awal, Class layout_tujuan) {

        Intent send = new Intent(layout_awal, layout_tujuan);
        send.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(send);

    }

    public void setView() {
        btnMenuRegistrasi = findViewById(R.id.btn_menu_registrasi);
        btnLogin = findViewById(R.id.btn_menu_login);
        etEmailLogin = findViewById(R.id.et_email_login);
        etPasswordLogin = findViewById(R.id.et_password_login);
    }

    public void setOnClick() {

        btnLogin.setOnClickListener(this);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final String email_login = etEmailLogin.getText().toString();
//                final String password_login = etPasswordLogin.getText().toString();
//
//                if (TextUtils.isEmpty(email_login) || email_login.toString().length() <= 11) {
//                    Toast.makeText(LoginActivity.this, "afwan, email tidak sesuai mohon masukkan kembali", Toast.LENGTH_SHORT).show();
//                } else if (TextUtils.isEmpty(password_login) || password_login.toString().length() <= 8) {
//                    Toast.makeText(LoginActivity.this, "afwan, password tidak sesuai mohon masukkan kembali", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    //activity yang sudah login tidak perlu login lagi
//                    prefManager = new cari_ustadz.pondokit.id.cari_ustadz.PrefManager(getApplicationContext());
//                    prefManager.setIsFirstTimeLaunch(false);
//                    Intent btndasboard = new Intent(getApplicationContext(), PlaceSelectionActivity.class);
//                    startActivity(btndasboard);
//                    finish();
//
//                }
//            }
//        });

        btnMenuRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnregis = new Intent(getApplicationContext(), RegistrasiActivity.class);
                startActivity(btnregis);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.btn_menu_registrasi:
//
//                sendTo(getApplicationContext(), DasboardActivity.class);
//                break;

            case R.id.btn_menu_login:

                String email = etEmailLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    progressDialog.setMessage("Mohon tunggu sebentar...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    LoginUser(email, password);

                } else {

                    Toast.makeText(this, "Mohon isi semua : )", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void LoginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    progressDialog.dismiss();

                    String current_user_id = mAuth.getCurrentUser().getUid();
                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    mUserDataBase.child(current_user_id).child("device_token").setValue(device_token).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            prefManager.setIsFirstTimeLaunch(false);
                            Toast.makeText(LoginActivity.this, "Login Berhasil...", Toast.LENGTH_SHORT).show();
                            sendTo(getApplicationContext(), PlaceSelectionActivity.class);
                            finish();

                        }
                    });
                } else {

                    progressDialog.hide();

                    String cek_error = task.getException().getMessage().toString();
                    Toast.makeText(LoginActivity.this, "ini error = " + cek_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
