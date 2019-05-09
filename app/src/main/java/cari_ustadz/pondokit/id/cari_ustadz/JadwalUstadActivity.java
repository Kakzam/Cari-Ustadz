package cari_ustadz.pondokit.id.cari_ustadz;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class JadwalUstadActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<ModelJadwalUstad, NewsViewHolder> mAdapter;
    private FirebaseUser mCyrrentUser;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_ustad);
        setTitle("Jadwal Ustad");
        getjadwalustadz();

    }

    public void getjadwalustadz() {

        //Get Jadwal dan waktu menggunakan recyle di database
        mCyrrentUser = FirebaseAuth.getInstance().getCurrentUser();

        //syscron database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Jadwal_Ustad");
        mDatabase.keepSynced(true);

        //memberi lokasi tempat layout Recyclerview berada
        recyclerView = findViewById(R.id.rc_jadwal_ustad);

        //Pemberian Query saat get data
        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("Jadwal_Ustad");
        Query personsQuery = personsRef.orderByKey();

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(mLayoutManager);

        //membuat get data berbentuk recycle di firebase
        final FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ModelJadwalUstad>().setQuery(personsQuery, ModelJadwalUstad.class).build();

        mAdapter = new FirebaseRecyclerAdapter<ModelJadwalUstad, NewsViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull NewsViewHolder holder, int position, @NonNull final ModelJadwalUstad model) {

                //set dan get data
                holder.setNama_masjid(model.getNama_masjid());
                holder.setJam_awal(model.getJam_awal());
                holder.setMenit_awal(model.getMenit_awal());
                holder.setJam_selesai(model.getJam_selesai());
                holder.setMenit_selesai(model.getMenit_selesai());
                holder.setUrut(model.getUrut());
                holder.setTanggal(model.getTanggal());
                holder.setBulan(model.getBulan());
                holder.setTahun(model.getTahun());
                holder.setJalan(model.getJalan());
                holder.setKota(model.getKota());
                holder.setKecamatan(model.getKecamatan());
                holder.setKode_pos(model.getKode_pos());
                holder.setProvinsi(model.getProvinsi());
                holder.setUrut(Integer.toString(position+1));

            }

            @NonNull
            @Override
            public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_jadwal_ustad, viewGroup, false);
                return new NewsViewHolder(view);

            }
        };

        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        private NewsViewHolder(android.view.View itemView) {
            super(itemView);
            mView = itemView;
        }

        private void setNama_masjid(String nama_masjid) {
            TextView tvNamaMasjidRow = mView.findViewById(R.id.tv_nama_masjid_row_item_jadwal_ustadz);
            tvNamaMasjidRow.setText(nama_masjid);
        }

        private void setProvinsi(String provinsi) {
            TextView tvProvinsi = mView.findViewById(R.id.tv_provinsi_row_item_jadwal_ustadz);
            tvProvinsi.setText(provinsi);
        }

        private void setKota(String kota) {
            TextView tvKotaRow = mView.findViewById(R.id.tv_kab_kota_row_item_jadwal_ustadz);
            tvKotaRow.setText(kota);
        }

        private void setKecamatan(String kecamatan) {
            TextView tvKecamatanRow = mView.findViewById(R.id.tv_kecamatan_row_item_jadwal_ustadz);
            tvKecamatanRow.setText(kecamatan);
        }

        private void setJalan(String jalan) {
            TextView tvJalanRow = mView.findViewById(R.id.tv_alamat_row_item_jadwal_ustadz);
            tvJalanRow.setText(jalan);
        }

        private void setKode_pos(String kode_pos) {
            TextView tvKodePosRow = mView.findViewById(R.id.tv_kode_pos_row_item_jadwal_ustadz);
            tvKodePosRow.setText(kode_pos);
        }

        private void setJam_awal(String jam_awal) {
            TextView tvJamAwalRow = mView.findViewById(R.id.tv_jam_awal_row_item_jadwal_ustadz);
            tvJamAwalRow.setText(jam_awal);
        }

        private void setMenit_awal(String menit_awal) {
            TextView tvMenitAwalRow = mView.findViewById(R.id.tv_menit_awal_row_item_jadwal_ustadz);
            tvMenitAwalRow.setText(menit_awal);
        }
        private void setJam_selesai(String jam_selesai) {
            TextView tvJamSelesaiRow = mView.findViewById(R.id.tv_jam_selesai_row_item_jadwal_ustadz);
            tvJamSelesaiRow.setText(jam_selesai);
        }

        private void setMenit_selesai(String menit_selesai) {
            TextView tvMenitSelesaiRow = mView.findViewById(R.id.tv_menit_selesai_row_item_jadwal_ustadz);
            tvMenitSelesaiRow.setText(menit_selesai);
        }

        private void setTanggal(String tanggal) {
            TextView tvTanggalRow = mView.findViewById(R.id.tv_tanggal_row_item_jadwal_ustadz);
            tvTanggalRow.setText(tanggal);
        }
        private void setBulan(String bulan) {
            TextView tvBulanRow = mView.findViewById(R.id.tv_bulan_row_item_jadwal_ustadz);
            tvBulanRow.setText(bulan);
        }

        private void setTahun(String tahun) {
            TextView tvTahunRow = mView.findViewById(R.id.tv_tahun_row_item_jadwal_ustadz);
            tvTahunRow.setText(tahun);
        }

        private void setUrut(String urut) {
            TextView tvUrutRow = mView.findViewById(R.id.tv_urutan_row_item_jadwal_ustadz);
            tvUrutRow.setText(urut);
        }

    }

}
