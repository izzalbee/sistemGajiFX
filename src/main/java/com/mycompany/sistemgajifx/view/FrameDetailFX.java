package com.mycompany.sistemgajifx.view;

import com.mycompany.sistemgajifx.model.*;
import com.mycompany.sistemgajifx.controller.MainFX;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Cursor;

/**
 * Class View yang merepresentasikan antarmuka pengguna untuk rincian (Slip Gaji).
 * Bertanggung jawab menampilkan laporan dekomposisi Take-Home Pay secara dinamis, 
 * melakukan parsing format mata uang (Currency Formatter), dan menangani skenario input 
 * data jam kerja temporer khusus Pegawai Freelance.
 */
public class FrameDetailFX {
    
    /** Layar kanvas utuh untuk frame detail. */
    private Scene scene;
    
    /** Referensi objek delegasi pengendali (Router). */
    private MainFX mainApp;
    
    /** Data pegawai spesifik yang informasinya sedang dirender. */
    private Karyawan karyawan;

    private Label lblNamaDetail;
    private Label lblJabatanDetail;
    private Label lblGajiPokok;
    private Label lblInfoTambahan;
    private Label lblTotalGaji;
    private Button btnInputJam;

    /**
     * Constructor untuk pembentukan Frame Slip Gaji (Rincian).
     *
     * @param mainApp  Objek referensi navigasi dari Main Controller.
     * @param karyawan Objek data entitas yang dipilih pengguna melalui Tabel Direktori.
     */
    public FrameDetailFX(MainFX mainApp, Karyawan karyawan) {
        this.mainApp = mainApp;
        this.karyawan = karyawan;
        buatTampilan();
        perbaruiTeks(); // Lakukan update teks pertama kali form dipanggil
    }

    /**
     * Merakit kerangka penyusun Slip Gaji yang meliputi Navigasi Kembali, Komponen Profil,
     * Rincian Dasar Tunjangan, Highlight Penerimaan (Total), dan Action Buttons.
     */
    private void buatTampilan() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F1F5F9;"); 
        root.setPadding(new Insets(40));

        // 1. Tombol Backward Routing
        Button btnKembali = new Button("<< Kembali ke Direktori");
        btnKembali.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btnKembali.setStyle("-fx-background-color: transparent; -fx-text-fill: #4F46E5; -fx-cursor: hand; -fx-padding: 10 0;");
        
        btnKembali.setOnMouseEntered(e -> btnKembali.setStyle("-fx-background-color: transparent; -fx-text-fill: #312E81; -fx-cursor: hand; -fx-padding: 10 0;"));
        btnKembali.setOnMouseExited(e -> btnKembali.setStyle("-fx-background-color: transparent; -fx-text-fill: #4F46E5; -fx-cursor: hand; -fx-padding: 10 0;"));
        
        btnKembali.setOnAction(e -> mainApp.bukaFrameUtama());

        HBox topBox = new HBox(btnKembali);
        topBox.setAlignment(Pos.CENTER_LEFT);
        root.setTop(topBox);

        // 2. Kartu Rincian di Tengah (Center Card Form)
        VBox card = new VBox(25);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 16; -fx-padding: 40;");
        card.setMaxWidth(600);
        card.setAlignment(Pos.TOP_LEFT);

        DropShadow shadow = new DropShadow(30, Color.rgb(0, 0, 0, 0.05));
        shadow.setOffsetY(10);
        card.setEffect(shadow);

        lblNamaDetail = new Label();
        lblNamaDetail.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        lblNamaDetail.setTextFill(Color.web("#0F172A"));

        lblJabatanDetail = new Label();
        lblJabatanDetail.setFont(Font.font("Segoe UI", 16));
        lblJabatanDetail.setTextFill(Color.web("#64748B"));

        VBox headerBox = new VBox(5, lblNamaDetail, lblJabatanDetail);

        Separator sep = new Separator();
        sep.setStyle("-fx-padding: 10 0;");

        VBox detailsBox = new VBox(15);
        lblGajiPokok = new Label();
        lblGajiPokok.setFont(Font.font("Segoe UI", 15));
        lblGajiPokok.setTextFill(Color.web("#334155"));
        
        lblInfoTambahan = new Label();
        lblInfoTambahan.setFont(Font.font("Segoe UI", 15));
        lblInfoTambahan.setTextFill(Color.web("#334155"));

        detailsBox.getChildren().addAll(lblGajiPokok, lblInfoTambahan);

        // Highlighting Penerimaan Akhir (Gaji Total)
        VBox totalBox = new VBox(8);
        totalBox.setStyle("-fx-background-color: #EEF2FF; -fx-background-radius: 12; -fx-padding: 25;");
        totalBox.setAlignment(Pos.CENTER_LEFT);

        Label lblTotalText = new Label("Total Take-Home Pay");
        lblTotalText.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        lblTotalText.setTextFill(Color.web("#4F46E5"));

        lblTotalGaji = new Label();
        lblTotalGaji.setFont(Font.font("Segoe UI", FontWeight.BOLD, 42));
        lblTotalGaji.setTextFill(Color.web("#4F46E5"));

        totalBox.getChildren().addAll(lblTotalText, lblTotalGaji);

        // Opsi Aksi Khusus Pekerja Lepas (Freelance)
        btnInputJam = new Button("Input Jam Kerja Harian");
        btnInputJam.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btnInputJam.setStyle("-fx-background-color: #4F46E5; -fx-text-fill: white; -fx-padding: 12 25; -fx-background-radius: 8; -fx-cursor: hand;");
        
        btnInputJam.setOnMouseEntered(e -> btnInputJam.setStyle("-fx-background-color: #4338CA; -fx-text-fill: white; -fx-padding: 12 25; -fx-background-radius: 8; -fx-cursor: hand;"));
        btnInputJam.setOnMouseExited(e -> btnInputJam.setStyle("-fx-background-color: #4F46E5; -fx-text-fill: white; -fx-padding: 12 25; -fx-background-radius: 8; -fx-cursor: hand;"));
        
        btnInputJam.setOnAction(e -> handleInputJam());

        HBox actionBox = new HBox(btnInputJam);
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        actionBox.setPadding(new Insets(10, 0, 0, 0));

        card.getChildren().addAll(headerBox, sep, detailsBox, totalBox, actionBox);
        
        VBox centerWrapper = new VBox(card);
        centerWrapper.setAlignment(Pos.CENTER);
        root.setCenter(centerWrapper);

        this.scene = new Scene(root, 1100, 700);
    }

    /**
     * Menangani intervensi event klik ketika user bermaksud menambahkan rekam jam kerja.
     * Menginisiasi dialog teks input standar (Pop-up bawaan OS), melakukan konversi angka,
     * menyuntikkan (inject) kalkulasi jam terbaru ke entitas Model (Freelance), dan merefresh visualisasi data.
     */
    private void handleInputJam() {
        if (karyawan instanceof Freelance) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Input Jam Kerja");
            dialog.setHeaderText("Masukkan jam kerja harian tambahan untuk " + karyawan.getNama());
            
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(jamStr -> {
                try {
                    int jam = Integer.parseInt(jamStr);
                    ((Freelance) karyawan).tambahJamKerjaHarian(jam);
                    perbaruiTeks(); 
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Input harus berupa angka bulat!");
                    alert.show();
                }
            });
        }
    }

    /**
     * Memperbarui komponen visual teks di layar yang diambil langsung (real-time) 
     * dari state objek memori (Model). 
     * Memanfaatkan string formatter "%,.0f" untuk memanipulasi konversi ribuan standar Rupiah, 
     * serta melalukan filter kondisi render tombol secara pintar (tersembunyi jika tipe data bukan Freelance).
     */
    private void perbaruiTeks() {
        lblNamaDetail.setText(karyawan.getNama());
        lblJabatanDetail.setText(karyawan.getJabatan().getNamaJabatan());
        lblTotalGaji.setText(String.format("Rp%,.0f", karyawan.hitungGaji()).replace(',', '.'));

        // Filter tipe Polymorphism Down-Casting untuk mengakses fungsi spesifik tiap pewaris
        if (karyawan instanceof KaryawanTetap) {
            KaryawanTetap kt = (KaryawanTetap) karyawan;
            lblGajiPokok.setText(String.format("Gaji Pokok Jabatan: Rp%,.0f", kt.getGajiPokok()).replace(',', '.'));
            lblInfoTambahan.setText(String.format("Tunjangan Masa Kerja (%d Tahun): Rp%,.0f", kt.getTunjangan().getMasaKerja(), kt.getTunjangan().hitungNominal()).replace(',', '.'));
            
            // Logika UI adaptif: Tombol dinonaktifkan dari hirarki tata letak karena tidak diperlukan
            btnInputJam.setVisible(false); 
            btnInputJam.setManaged(false); 
            
        } else if (karyawan instanceof Freelance) {
            Freelance fr = (Freelance) karyawan;
            lblGajiPokok.setText("Gaji Pokok: Rp0 (Sistem Tarif Per Jam)");
            lblInfoTambahan.setText(String.format("Tarif Upah: Rp%,.0f / Jam   |   Total Terkumpul: %d Jam", fr.getTarifPerJam(), fr.getTotalJamBulanan()).replace(',', '.'));
            
            btnInputJam.setVisible(true); 
            btnInputJam.setManaged(true);
        }
    }

    /**
     * Mengekspos kanvas (Scene) dari kelas rincian ini.
     * 
     * @return Scene yang sudah dilengkapi komponen UI.
     */
    public Scene getScene() {
        return scene;
    }
}