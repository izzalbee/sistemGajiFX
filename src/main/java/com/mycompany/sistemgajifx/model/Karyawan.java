package com.mycompany.sistemgajifx.model;

/**
 * Class abstract yang merepresentasikan entitas dasar Karyawan.
 * Class ini mendefinisikan atribut dan perilaku umum yang akan diwariskan (Inheritance)
 * kepada class spesifik seperti KaryawanTetap dan Freelance.
 */
public abstract class Karyawan {
    
    /** Nomor identitas unik pegawai. */
    private String id;
    
    /** Nama lengkap pegawai. */
    private String nama;
    
    /** Referensi jabatan yang diemban oleh pegawai. */
    private Jabatan jabatan;

    /**
     * Constructor untuk membentuk entitas Karyawan.
     *
     * @param id      Nomor ID unik pegawai.
     * @param nama    Nama lengkap pegawai.
     * @param jabatan Objek Jabatan yang mendefinisikan posisi dan gaji pokok dasar.
     */
    public Karyawan(String id, String nama, Jabatan jabatan) {
        this.id = id;
        this.nama = nama;
        this.jabatan = jabatan;
    }

    /** 
     * Mengambil nomor ID pegawai.
     * 
     * @return ID unik pegawai dalam format String.
     */
    public String getId() { 
        return id; 
    }
    
    /** 
     * Mengambil nama pegawai.
     * 
     * @return Nama lengkap pegawai.
     */
    public String getNama() {
        return nama; 
    }
    
    /** 
     * Mengambil objek jabatan yang terkait dengan pegawai ini.
     * 
     * @return Objek Jabatan pegawai.
     */
    public Jabatan getJabatan() {
        return jabatan; 
    }
    
    /** 
     * Mengambil nominal gaji pokok dari jabatan yang diemban.
     * 
     * @return Nominal gaji pokok (double).
     */
    public double getGajiPokok() { 
        return jabatan.getGajiPokok(); 
    }

    /**
     * Method abstract untuk mengkalkulasi total gaji akhir (Take-Home Pay).
     * Memaksa setiap subclass (anak class) untuk mendefinisikan rumus perhitungannya masing-masing
     * (Penerapan konsep Polymorphism).
     * 
     * @return Total nominal gaji akhir.
     */
    public abstract double hitungGaji();

    /**
     * Menghasilkan representasi teks dari objek Karyawan.
     * 
     * @return String yang berisi nama, nama jabatan, dan gaji pokok.
     */
    @Override
    public String toString() {
        return String.format("%s (%s) | Gaji Pokok: Rp%.0f", 
                nama, jabatan.getNamaJabatan(), getGajiPokok());
    }
}