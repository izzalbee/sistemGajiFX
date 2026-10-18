package com.mycompany.sistemgajifx.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class agregasi yang bertindak sebagai wadah (container) pengelompokan Karyawan.
 * Mengelola sekelompok pegawai yang berada di bawah unit kerja (divisi) yang sama.
 */
public class Departemen {
    
    /** Nama nomenklatur divisi/departemen. */
    private String namaDepartemen;
    
    /** Daftar seluruh pegawai yang terdaftar secara administratif di bawah departemen ini. */
    private List<Karyawan> daftarKaryawan;

    /**
     * Constructor untuk menginisialisasi departemen baru yang kosong.
     *
     * @param namaDepartemen Identitas divisi (contoh: "Departemen IT", "Departemen HRD").
     */
    public Departemen(String namaDepartemen) {
        this.namaDepartemen = namaDepartemen;
        this.daftarKaryawan = new ArrayList<>();
    }

    /**
     * Mengambil nama departemen.
     * 
     * @return String representasi nama departemen.
     */
    public String getNamaDepartemen() { 
        return namaDepartemen; 
    }

    /**
     * Menambahkan/menempatkan seorang pegawai baru ke dalam daftar departemen.
     * 
     * @param k Objek yang mewarisi class Karyawan (bisa KaryawanTetap atau Freelance).
     */
    public void tambahKaryawan(Karyawan k) { 
        daftarKaryawan.add(k); 
    }

    /**
     * Menghitung total beban anggaran yang harus dikeluarkan perusahaan khusus untuk mendanai
     * gaji seluruh pegawai di dalam departemen ini. Memanfaatkan Dynamic Method Dispatch (Polymorphism).
     *
     * @return Total anggaran gaji divisi terkait (double).
     */
    public double hitungTotalGajiDepartemen() {
        double total = 0;
        for (Karyawan k : daftarKaryawan) {
            total += k.hitungGaji();
        }
        return total;
    }

    /**
     * Menampilkan hasil rekapitulasi data anggota beserta gaji masing-masing 
     * ke antarmuka terminal/console (digunakan untuk keperluan laporan atau debugging).
     */
    public void tampilkanDaftarKaryawan() {
        System.out.println("  Departemen: " + namaDepartemen);
        if (daftarKaryawan.isEmpty()) {
            System.out.println("   - (Belum ada karyawan)");
            return;
        }
        
        for (Karyawan k : daftarKaryawan) {
            System.out.println("   - " + k.toString());
        }
    }
}