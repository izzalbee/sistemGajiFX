package com.mycompany.sistemgajifx.controller;

import com.mycompany.sistemgajifx.view.FrameDetailFX;
import com.mycompany.sistemgajifx.view.FrameUtamaFX;
import com.mycompany.sistemgajifx.model.Freelance;
import com.mycompany.sistemgajifx.model.Jabatan;
import com.mycompany.sistemgajifx.model.Karyawan;
import com.mycompany.sistemgajifx.model.KaryawanTetap;
import com.mycompany.sistemgajifx.model.Tunjangan;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * Class pengontrol utama (Controller) dalam arsitektur aplikasi MVC.
 * Bertanggung jawab untuk menginisialisasi sistem, menyimpan struktur data inti (Model), 
 * dan mengatur alur navigasi (routing) antar antarmuka pengguna (View).
 */
public class MainFX extends Application {
    
    /** Jendela/bingkai utama aplikasi JavaFX yang menampung seluruh tampilan. */
    private Stage primaryStage;
    
    /** Struktur data yang menyimpan daftar seluruh karyawan yang terdaftar di perusahaan. */
    private List<Karyawan> listKaryawan = new ArrayList<>();

    /**
     * Method daur hidup (lifecycle) utama dari JavaFX yang dipanggil setelah inisialisasi aplikasi.
     * Bertugas mengatur konfigurasi jendela utama dan menampilkan layar pertama.
     * 
     * @param primaryStage Stage utama yang disediakan oleh platform JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Aplikasi Penggajian JavaFX");
        
        inisialisasiData();
        bukaFrameUtama(); 
        
        primaryStage.show();
    }

    /**
     * Mengisi data awal (dummy data) ke dalam memori aplikasi saat sistem pertama kali dijalankan.
     * Membentuk objek Jabatan, Karyawan Tetap, dan Karyawan Freelance.
     */
    private void inisialisasiData() {
        Jabatan jabManagerIT = new Jabatan("Manager IT", 10000000);
        Jabatan jabStaffHRD = new Jabatan("Staff HRD", 5000000);
        Jabatan jabFreelance = new Jabatan("Freelance Programmer", 0);
        
        listKaryawan.add(new KaryawanTetap("T-01", "Budi", jabManagerIT, new Tunjangan(6)));
        listKaryawan.add(new KaryawanTetap("T-02", "Andi", jabStaffHRD, new Tunjangan(2)));
        listKaryawan.add(new Freelance("F-01", "Siti", jabFreelance, 100000));
    }

    /**
     * Mengambil seluruh data karyawan yang tersimpan di dalam sistem.
     * 
     * @return List yang berisi objek-objek keturunan dari abstract class Karyawan.
     */
    public List<Karyawan> getListKaryawan() {
        return listKaryawan;
    }

    /** 
     * Menambahkan entitas karyawan baru ke dalam sistem memori aplikasi.
     * Dipanggil ketika pengguna berhasil menyimpan data dari form registrasi.
     * 
     * @param karyawan Objek Karyawan (KaryawanTetap atau Freelance) yang baru dibuat.
     */
    public void tambahKaryawan(Karyawan karyawan) {
        listKaryawan.add(karyawan);
    }

    /**
     * Memuat dan menampilkan halaman antarmuka utama (Dashboard dan Direktori).
     * Method ini menukar Scene yang sedang aktif di Stage utama dengan Scene dari FrameUtamaFX.
     */
    public void bukaFrameUtama() {
        FrameUtamaFX frameUtama = new FrameUtamaFX(this);
        primaryStage.setScene(frameUtama.getScene());
    }

    /**
     * Memuat dan menampilkan halaman rincian gaji (Detail Gaji) secara dinamis.
     * 
     * @param karyawanTerpilih Objek Karyawan spesifik yang datanya ingin dilihat secara rinci.
     */
    public void bukaFrameDetail(Karyawan karyawanTerpilih) {
        FrameDetailFX frameDetail = new FrameDetailFX(this, karyawanTerpilih);
        primaryStage.setScene(frameDetail.getScene());
    }

    /**
     * Method utama bawaan JavaFX untuk memulai lifecycle aplikasi GUI.
     * 
     * @param args Argumen baris perintah (command line arguments).
     */
    public static void main(String[] args) {
        launch(args);
    }
}