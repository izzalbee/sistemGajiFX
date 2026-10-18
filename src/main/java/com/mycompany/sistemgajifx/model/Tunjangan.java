package com.mycompany.sistemgajifx.model;

/**
 * Class yang mengenkapsulasi aturan bisnis (business logic) terkait pemberian tunjangan loyalitas.
 * Nominal tunjangan dihitung secara dinamis berdasarkan durasi pengabdian (masa kerja) pegawai.
 */
public class Tunjangan {
    
    /** Total durasi pengabdian pegawai dalam hitungan tahun kalender. */
    private int masaKerja; 

    /**
     * Constructor untuk menetapkan masa kerja awal saat tunjangan dikaitkan ke pegawai.
     *
     * @param masaKerja Lama waktu pegawai telah bekerja (dalam satuan tahun penuh).
     */
    public Tunjangan(int masaKerja) {
        this.masaKerja = masaKerja;
    }

    /**
     * Mengambil informasi masa kerja.
     * 
     * @return Nilai tahun masa kerja.
     */
    public int getMasaKerja() {
        return masaKerja;
    }

    /**
     * Memodifikasi masa kerja dengan validasi agar tidak menerima angka negatif.
     * 
     * @param masaKerja Nilai tahun masa kerja yang baru.
     */
    public void setMasaKerja(int masaKerja) {
        if (masaKerja >= 0) {
            this.masaKerja = masaKerja;
        }
    }

    /**
     * Menghitung nilai rupiah dari tunjangan yang berhak diterima berdasarkan jenjang waktu.
     * Skema Aturan Bisnis:
     * - Kurang dari 1 tahun: Rp 0
     * - 1 hingga 3 tahun: Rp 1.000.000
     * - 4 hingga 5 tahun: Rp 2.000.000
     * - Lebih dari 5 tahun: Rp 3.000.000
     *
     * @return Nominal tunjangan yang sah diterima (bertipe double).
     */
    public double hitungNominal() {
        if (masaKerja < 1) {
            return 0;           
        } else if (masaKerja <= 3) {
            return 1000000;     
        } else if (masaKerja <= 5) {
            return 2000000;     
        } else {
            return 3000000;     
        }
    }
}