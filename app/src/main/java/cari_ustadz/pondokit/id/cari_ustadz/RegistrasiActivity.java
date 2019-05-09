package cari_ustadz.pondokit.id.cari_ustadz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegistrasiActivity extends AppCompatActivity {

    EditText etNameReg, etTelpReg, etGmailReg, etPasswordReg;
    Button btnReg;

    //Loading
    ProgressDialog mRegProgress;

    //Firebase untuk menyambungkan ke database
    private DatabaseReference mDatabase;

    //Authentifikasi
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        setView();
        setOneClick();

        mRegProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
    }

    public void setView() {

        //Inisialisasi edit text
        etNameReg = findViewById(R.id.et_nama_registerasi);
        etTelpReg = findViewById(R.id.et_telephone_registrasi);
        etGmailReg = findViewById(R.id.et_email_registrasi);
        etPasswordReg = findViewById(R.id.et_password_registrasi);
        btnReg = findViewById(R.id.btn_menu_registrasi_login);


    }

    public void setOneClick() {

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //inisialisasi String
                final String strnamereg = etNameReg.getText().toString();
                final String strtelpreg = etTelpReg.getText().toString();
                final String strgmailreg = etGmailReg.getText().toString();
                final String strpasswordreg = etPasswordReg.getText().toString();

                if (TextUtils.isEmpty(strnamereg) || strnamereg.toString().length() <= 5) {
                    Toast.makeText(RegistrasiActivity.this, "afwan, mohon mengisikan nama dengan benar", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(strtelpreg) || strtelpreg.toString().length() <= 11) {
                    Toast.makeText(RegistrasiActivity.this, "afwan, mohon mengisikan nomer telephone dengan benar", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(strgmailreg) || strgmailreg.toString().length() <= 12) {
                    Toast.makeText(RegistrasiActivity.this, "afwan, mohon isikan akun email dengan benar", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(strpasswordreg) || strpasswordreg.toString().length() < 8) {
                    Toast.makeText(RegistrasiActivity.this, "afwan, mohon isikan password dengan benar, atau panjang password minimal 8 kata", Toast.LENGTH_SHORT).show();

                } else {

                    mRegProgress.setMessage("Mohon Tunggu Sebentar...");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(strgmailreg, strpasswordreg, strnamereg, strtelpreg);

                }
            }

            private void register_user(final String strgmailreg, final String strpasswordreg, final String strnamereg, final String strtelpreg) {

                mAuth.createUserWithEmailAndPassword(strgmailreg, strpasswordreg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            String ambil_token = FirebaseInstanceId.getInstance().getToken();

                            //Penamaan untuk data yang ada di firebase dalam tanda kutip digunakan untuk nanti get data
                            HashMap<String, String> penamaan_id = new HashMap<>();
                            penamaan_id.put("nama_lengkap", strnamereg);
                            penamaan_id.put("phone", strtelpreg);
                            penamaan_id.put("email", strgmailreg);
                            penamaan_id.put("password", strpasswordreg);
                            penamaan_id.put("token", ambil_token);

                            mDatabase.setValue(penamaan_id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(RegistrasiActivity.this, "Registrasi Berhasil...", Toast.LENGTH_SHORT).show();
                                        Intent btnregis = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(btnregis);
                                        finish();

                                    }
                                }
                            });
                        } else {

                            String cek_error = task.getException().getMessage().toString();
                            mRegProgress.hide();
                            Toast.makeText(RegistrasiActivity.this, "error ini = " + cek_error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}