package cari_ustadz.pondokit.id.cari_ustadz;

/**
 * Created By Kak Zam Zam On 31/01/19.
 */
public class ModelJadwalUstad {

    private String nama;
    private String nama_masjid;
    private String provinsi;
    private String kota;
    private String kecamatan;
    private String jalan;
    private String kode_pos;
    private String jam_awal;
    private String menit_awal;
    private String jam_selesai;
    private String menit_selesai;
    private String tanggal;
    private String bulan;
    private String tahun;
    private String urut;

    public ModelJadwalUstad(){}

    public ModelJadwalUstad(String nama, String nama_masjid, String provinsi, String kota, String kecamatan, String jalan, String kode_pos, String jam_awal, String menit_awal, String jam_selesai, String menit_selesai, String tanggal, String bulan, String tahun, String urut) {
        this.nama = nama;
        this.nama_masjid = nama_masjid;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.jalan = jalan;
        this.kode_pos = kode_pos;
        this.jam_awal = jam_awal;
        this.menit_awal = menit_awal;
        this.jam_selesai = jam_selesai;
        this.menit_selesai = menit_selesai;
        this.tanggal = tanggal;
        this.bulan = bulan;
        this.tahun = tahun;
        this.urut = urut;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_masjid() {
        return nama_masjid;
    }

    public void setNama_masjid(String nama_masjid) {
        this.nama_masjid = nama_masjid;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getJalan() {
        return jalan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(String kode_pos) {
        this.kode_pos = kode_pos;
    }

    public String getJam_awal() {
        return jam_awal;
    }

    public void setJam_awal(String jam_awal) {
        this.jam_awal = jam_awal;
    }

    public String getMenit_awal() {
        return menit_awal;
    }

    public void setMenit_awal(String menit_awal) {
        this.menit_awal = menit_awal;
    }

    public String getJam_selesai() {
        return jam_selesai;
    }

    public void setJam_selesai(String jam_selesai) {
        this.jam_selesai = jam_selesai;
    }

    public String getMenit_selesai() {
        return menit_selesai;
    }

    public void setMenit_selesai(String menit_selesai) {
        this.menit_selesai = menit_selesai;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getUrut() {
        return urut;
    }

    public void setUrut(String urut) {
        this.urut = urut;
    }
}
