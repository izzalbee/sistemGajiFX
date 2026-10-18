package com.mycompany.sistemgajifx.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class agregasi tertinggi yang menampung seluruh struktur organisasi (Kumpulan Departemen).
 * Berfungsi memfasilitasi pelaporan tingkat korporasi (Grand Total) dari seluruh beban keuangan.
 */
public class Perusahaan {
    
    /** Nama identitas entitas bisnis atau perusahaan. */
    private String namaPerusahaan;
    
    /** Koleksi departemen/divisi yang beroperasi di dalam perusahaan. */
    private List<Departemen> daftarDepartemen;

    /**
     * Constructor inisialisasi entitas korporat baru.
     *
     * @param namaPerusahaan Nama resmi dari perusahaan/organisasi.
     */
    public Perusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
        this.daftarDepartemen = new ArrayList<>();
    }

    /**
     * Mengambil nama resmi perusahaan.
     * 
     * @return String nama perusahaan.
     */
    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    /**
     * Mendaftarkan divisi/departemen baru ke dalam hirarki organisasi perusahaan.
     * 
     * @param dept Objek Departemen yang telah terstruktur sebelumnya.
     */
    public void tambahDepartemen(Departemen dept) {
        daftarDepartemen.add(dept);
        System.out.println("Departemen " + dept.getNamaDepartemen() + " berhasil ditambahkan.");
    }

    /**
     * Memproses agregasi perhitungan untuk seluruh departemen yang ada guna mendapatkan 
     * total anggaran (Capital Expenditure) perusahaan secara menyeluruh.
     * 
     * @return Total biaya pengeluaran operasional gaji (double).
     */
    public double hitungTotalGajiPerusahaan() {
        double total = 0;
        for (Departemen dept : daftarDepartemen) {
            total += dept.hitungTotalGajiDepartemen();
        }
        return total;
    }

    /**
     * Mengeksekusi pencetakan laporan komprehensif ke antarmuka konsol (Console).
     * Laporan mencakup pembedahan beban per unit divisi hingga rekapitulasi puncak perusahaan.
     */
    public void tampilkanLaporanPerusahaan() {
        System.out.println("\n=== LAPORAN PERUSAHAAN " + namaPerusahaan.toUpperCase() + " ===");
        for (Departemen dept : daftarDepartemen) {
            dept.tampilkanDaftarKaryawan();
            System.out.printf("  [Total Gaji Dept %s: Rp%.0f]\n\n", 
                              dept.getNamaDepartemen(), 
                              dept.hitungTotalGajiDepartemen());
        }
        System.out.printf("TOTAL PENGELUARAN PERUSAHAAN: Rp%.0f\n", hitungTotalGajiPerusahaan());
    }
}