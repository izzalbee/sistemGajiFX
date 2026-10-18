package com.mycompany.sistemgajifx.model;

/**
 * Class turunan (Subclass) yang merepresentasikan Pegawai Berstatus Tetap (Full-time).
 * Pewarisan dari class Karyawan ini mencakup penambahan struktur data Tunjangan khusus.
 */
public class KaryawanTetap extends Karyawan {
    
    /** Komponen tunjangan loyalitas berbasis masa kerja. */
    private Tunjangan tunjangan;

    /**
     * Constructor untuk membentuk identitas Karyawan Tetap beserta komponen gajinya.
     *
     * @param id        ID identifikasi unik (misal: "T-01").
     * @param nama      Nama lengkap pegawai.
     * @param jabatan   Objek Jabatan yang merepresentasikan level posisi struktural.
     * @param tunjangan Objek Tunjangan yang telah diinisialisasi masa kerjanya.
     */
    public KaryawanTetap(String id, String nama, Jabatan jabatan, Tunjangan tunjangan) {
        // Pemanggilan constructor dari parent class (Karyawan)
        super(id, nama, jabatan);
        this.tunjangan = tunjangan;
    }

    /** 
     * Mengambil objek tunjangan milik karyawan.
     * 
     * @return Objek Tunjangan.
     */
    public Tunjangan getTunjangan() { 
        return tunjangan; 
    }

    /** 
     * Memperbarui komponen tunjangan karyawan.
     * 
     * @param tunjangan Objek Tunjangan baru.
     */
    public void setTunjangan(Tunjangan tunjangan) { 
        this.tunjangan = tunjangan; 
    }

    /**
     * Implementasi spesifik untuk menghitung Take-Home Pay Karyawan Tetap.
     * Menggunakan rumus: Nilai Gaji Pokok Jabatan + Nilai Nominal Tunjangan.
     *
     * @return Total akumulasi pendapatan kotor (double).
     */
    @Override
    public double hitungGaji() {
        return getGajiPokok() + tunjangan.hitungNominal();
    }

    /**
     * Menghasilkan teks laporan rinci spesifik untuk Karyawan Tetap.
     * 
     * @return String berformat rapi yang menyertakan masa kerja dan tunjangan.
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Masa Kerja: %d tahun | Tunjangan: Rp%.0f | Total Gaji Akhir: Rp%.0f", 
                tunjangan.getMasaKerja(), tunjangan.hitungNominal(), hitungGaji());
    }
}