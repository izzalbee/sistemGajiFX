package com.mycompany.sistemgajifx.model;

/**
 * Class yang merepresentasikan referensi Jabatan/Posisi dalam organisasi perusahaan.
 * Berfungsi sebagai tipe data bentukan (komposisi) untuk digunakan di dalam entitas Karyawan.
 */
public class Jabatan {
    
    /** Nama posisi atau gelar jabatan di perusahaan. */
    private String namaJabatan;
    
    /** Nilai gaji pokok dasar yang ditetapkan untuk jabatan ini. */
    private double gajiPokok;

    /**
     * Constructor untuk membentuk objek Jabatan baru.
     *
     * @param namaJabatan Nama posisi struktural (contoh: "Manager IT", "Staff").
     * @param gajiPokok   Nominal gaji pokok dasar yang berlaku (dalam Rupiah).
     */
    public Jabatan(String namaJabatan, double gajiPokok) {
        this.namaJabatan = namaJabatan;
        this.gajiPokok = gajiPokok;
    }

    /**
     * Mengambil nama posisi jabatan.
     * 
     * @return String representasi nama jabatan.
     */
    public String getNamaJabatan() {
        return namaJabatan;
    }

    /**
     * Mengambil besaran nilai gaji pokok yang telah dikonfigurasi.
     * 
     * @return Nominal gaji pokok bertipe double.
     */
    public double getGajiPokok() {
        return gajiPokok;
    }
}