package com.mycompany.sistemgajifx.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class turunan (Subclass) yang merepresentasikan Pegawai Lepas (Part-time / Freelance).
 * Model penggajian tidak berdasarkan masa kerja, melainkan kalkulasi tarif per jam yang diakumulasikan.
 */
public class Freelance extends Karyawan {
    
    /** Koleksi list yang mencatat total jam kerja untuk setiap hari. */
    private List<Integer> jamKerjaHarian;
    
    /** Tarif upah yang dibayarkan per satu jam pengerjaan (rate). */
    private double tarifPerJam;

    /**
     * Constructor untuk menyiapkan entitas pekerja lepas (Freelance).
     *
     * @param id          ID unik (misal: "F-01").
     * @param nama        Nama pegawai lepas.
     * @param jabatan     Objek Jabatan (umumnya gaji pokok diset ke-0 untuk model ini).
     * @param tarifPerJam Tarif yang disepakati untuk upah setiap jam (dalam Rupiah).
     */
    public Freelance(String id, String nama, Jabatan jabatan, double tarifPerJam) {
        super(id, nama, jabatan);
        this.tarifPerJam = tarifPerJam;
        this.jamKerjaHarian = new ArrayList<>();
    }

    /**
     * Mencatat (entry) jumlah jam operasional kerja yang dihabiskan dalam satu hari.
     * Memiliki validasi bahwa jam harian tidak mungkin lebih dari 24.
     * 
     * @param jam Angka jam kerja (rentang logis 0 hingga 24).
     */
    public void tambahJamKerjaHarian(int jam) {
        if (jam >= 0 && jam <= 24) {
            jamKerjaHarian.add(jam);
        } else {
            System.out.println("Input jam kerja harian tidak valid (0-24 jam).");
        }
    }

    /**
     * Melakukan iterasi (perulangan) untuk mengakumulasi total seluruh jam kerja pada periode bulan tersebut.
     * 
     * @return Total penjumlahan jam kerja (Integer).
     */
    public int getTotalJamBulanan() {
        int total = 0;
        for (int jam : jamKerjaHarian) {
            total += jam;
        }
        return total;
    }

    /**
     * Mengambil besaran tarif upah satu jam kerja.
     * 
     * @return Nominal tarif (double).
     */
    public double getTarifPerJam() {
        return tarifPerJam;
    }

    /**
     * Implementasi spesifik untuk menghitung Take-Home Pay pekerja lepas.
     * Menggunakan rumus perkalian: Total Jam Keseluruhan * Tarif Per Jam.
     *
     * @return Total akumulasi upah yang wajib dibayarkan (double).
     */
    @Override
    public double hitungGaji() {
        return getTotalJamBulanan() * tarifPerJam;
    }

    /**
     * Menghasilkan teks laporan spesifik terkait performa dan upah Freelance.
     * 
     * @return String berformat rapi yang menyertakan total jam dan tarif upah.
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Tarif/Jam: Rp%.0f | Total Jam: %d jam | Total Gaji Akhir: Rp%.0f", 
                tarifPerJam, getTotalJamBulanan(), hitungGaji());
    }
}