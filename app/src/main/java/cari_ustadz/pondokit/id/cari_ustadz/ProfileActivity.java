package cari_ustadz.pondokit.id.cari_ustadz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    TextView etNamaProfile, etTelpProfile, etEmailProfile, etPasswordProfile;
    Button btn_edit_simpan_pfrofile;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    public DatabaseReference mUserRef;
    private DatabaseReference mUserDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        setView();
        setOneClick();

        //masuk ke autentifikasi
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {

            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        }

        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //child di isi nama key
                String nama_lengkap = dataSnapshot.child("nama_lengkap").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String password = dataSnapshot.child("password").getValue().toString();

                //nama string dalam setText
                etNamaProfile.setText(nama_lengkap);
                etEmailProfile.setText(email);
                etTelpProfile.setText(phone);
                etPasswordProfile.setText(password);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setView() {

        etNamaProfile = findViewById(R.id.et_nama_profile);
        etTelpProfile = findViewById(R.id.et_telp_profile);
        etEmailProfile = findViewById(R.id.et_email_profile);
        etPasswordProfile = findViewById(R.id.et_password_profile);
        btn_edit_simpan_pfrofile = findViewById(R.id.btn_edit_simpan_profile);

    }

    private void setOneClick() {

        btn_edit_simpan_pfrofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama_lengkap = etNamaProfile.getText().toString();
                String email = etEmailProfile.getText().toString();
                String phone = etTelpProfile.getText().toString();
                String password = etPasswordProfile.getText().toString();

                post_profile(nama_lengkap, email, phone, password);

            }

            private void post_profile(String nama_lengkap, String email, String phone, String password) {

                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = current_user.getUid();
                String id = mUserDatabase.push().getKey();

                mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users_ustadzku").child(uid);
                String device_token = FirebaseInstanceId.getInstance().getToken();

                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("nama_lengkap", nama_lengkap);
                userMap.put("email", email);
                userMap.put("phone", phone);
                userMap.put("password", password);
                userMap.put("device_token", device_token);

                mUserDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            //ngosongin edittext jika sudah di simpan
//                            etEmailProfile.setText(null);
//                            etNamaProfile.setText(null);
//                            etTelpProfile.setText(null);

                            Toast.makeText(ProfileActivity.this, "Data sudah di perbarui...", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(ProfileActivity.this, "Data tidak berhasil di simpan..", Toast.LENGTH_SHORT).show();

                        }
                    }

                });
            }
        });

    }
}
