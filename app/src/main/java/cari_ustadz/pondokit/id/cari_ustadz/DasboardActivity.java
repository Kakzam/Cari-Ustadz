package cari_ustadz.pondokit.id.cari_ustadz;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cari_ustadz.pondokit.id.cari_ustadz.activities.PrayerTimesActivity;

public class DasboardActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    TextView tvLogoutDasboard;
    EditText etCariDas;
    ImageView btnDelete;
    public MyRecyclerViewAdapter adapter;

    //memanggil penyimpanan sementara
    private cari_ustadz.pondokit.id.cari_ustadz.PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        setView();
        setOnclick();

        // data to populate the RecyclerView with
        String[] data = {
                "Profile",
                "Jadwal Ustad",
//                "Chat Ustad",
//                "Telpon Ustad",
//                "Kajian",
                "Jadwal Shalat",
                "Catat Hafalan",
                "Saran & Masukan",
                "Tentang Aplikasi",

        };

        int[] image = {
                R.drawable.menu_profile,
                R.drawable.menu_jadwal_ustad,
//                R.drawable.menu_chat_ustad,
//                R.drawable.menu_telp_ustad,
//                R.drawable.menu_kajian,
                R.drawable.menu_jadwal_shalat,
                R.drawable.menu_catatan_hafalan,
                R.drawable.menu_saran_masukan,
                R.drawable.menu_tentang_aplikasi,

        };

        int[] color = {
                R.color.bgmenubirudasartrans,
                R.color.bgmenubluetrans,
                R.color.bgmenuunguviolettrans,
                R.color.bgmenubirudasartrans,
                R.color.bgmenubluetrans,
                R.color.bgmenuunguviolettrans,
//                R.color.bgmenubirudasartrans,
//                R.color.bgmenubluetrans,
//                R.color.bgmenuunguviolettrans,

        };

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, data, image, color);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void setOnclick() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCariDas.setText(null);
            }
        });

        tvLogoutDasboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //agar pada saat logout login hilang
                prefManager = new cari_ustadz.pondokit.id.cari_ustadz.PrefManager(getApplicationContext());
                if (!prefManager.isFirstTimeLaunch()) {
                    prefManager.setIsFirstTimeLaunch(true);
                    sendTo(getApplicationContext(), DasboardActivity.class);
                    finish();
                }

                Intent tvlogoutdasboard = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(tvlogoutdasboard);
                finish();

            }

            private void sendTo(Context LayoutAwal, Class LayoutTujuan) {
                Intent kirim = new Intent(LayoutAwal, LayoutTujuan);
                kirim.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(kirim);
            }
        });

    }

    private void setView() {
        btnDelete = findViewById(R.id.btn_delete);
        etCariDas = findViewById(R.id.et_cari_dasboard);
        tvLogoutDasboard = findViewById(R.id.tv_logout_dasboard);

    }

    @Override
    public void onItemClick(View view, int position) {

        if (    adapter.getItem(position).contentEquals("Chat Ustad") ||
                adapter.getItem(position).contentEquals("Telpon Ustad") ||
                adapter.getItem(position).contentEquals("Kajian")) {

            Toast.makeText(this, "Afwan, menu masih dalam tahap perkembangan", Toast.LENGTH_SHORT).show();

        } else if(adapter.getItem(position).contentEquals("Profile")) {

            Intent menuprofile = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(menuprofile);

        } else if(adapter.getItem(position).contentEquals("Jadwal Ustad")) {

            Intent menuprofile = new Intent(getApplicationContext(), JadwalUstadActivity.class);
            startActivity(menuprofile);

        } else if(adapter.getItem(position).contentEquals("Jadwal Shalat")) {

            Intent menuprofile = new Intent(getApplicationContext(), PrayerTimesActivity.class);
            startActivity(menuprofile);

        } else if (adapter.getItem(position).contentEquals("Catat Hafalan")) {

            Toast.makeText(this, "Afwan, menu ini masih dalam proses.", Toast.LENGTH_SHORT).show();

//            //di url playstore ada "com.facebook.katana" itu yang di ambil / id aplikasi di playstore
//            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
//            if (launchIntent != null) {
//                startActivity(launchIntent);//null pointer check in case package name was not found
//            } else {
//
//                Intent launchIntentplaystore = getPackageManager().getLaunchIntentForPackage("com.android.vending");
//
//                // package name and activity
//                ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity");
//                launchIntentplaystore.setComponent(comp);
//
//                // sample to open facebook app(parse di isi url playstore untuk masuk playstore)
//                launchIntentplaystore.setData(Uri.parse("market://details?id=com.facebook.katana"));
//                startActivity(launchIntentplaystore);
//
//            }

        } else if (adapter.getItem(position).contentEquals("Saran & Masukan")) {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "ustadcari@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pelayanan");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Saran & masukan :\n\n\n");
            //emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
            startActivity(Intent.createChooser(emailIntent, "Pilih untuk saran & masukan "));

        } else if (adapter.getItem(position).contentEquals("Tentang Aplikasi")){

            // custom dialog
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.setTitle("Tentang aplikasi ini");
//            dialog.setCanceledOnTouchOutside(false);
            Button dialogButton = dialog.findViewById(R.id.btn_ok);

            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }
//        } else if (adapter.getItem(position).contentEquals("Profile")) {
//            SendTo(getApplicationContext(), ProfileActivity.class);
    }

    private void sendTo(Context layout_awal, Class layout_tujuan) {

        Intent send = new Intent(layout_awal, layout_tujuan);
        send.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(send);

    }
}