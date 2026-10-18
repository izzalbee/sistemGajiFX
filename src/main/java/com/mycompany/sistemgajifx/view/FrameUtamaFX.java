package com.mycompany.sistemgajifx.view;

import com.mycompany.sistemgajifx.model.*;
import com.mycompany.sistemgajifx.controller.MainFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class View yang mengurus pembangunan antarmuka visual (UI) utama aplikasi.
 * Terdiri dari komponen Layout seperti Sidebar navigasi, Dashboard indikator kinerja (KPI), 
 * dan Tabel Direktori interaktif untuk pendaftaran serta pemantauan entitas Pegawai.
 */
public class FrameUtamaFX {
    
    /** Kanvas tampilan UI (Scene) yang siap dirender oleh JavaFX. */
    private Scene scene;
    
    /** Referensi balik ke Main Controller untuk proses komunikasi (Delegasi/Dependency Injection). */
    private MainFX mainApp;
    
    /** Menyimpan status kursor (baris tabel) yang sedang disorot/dipilih pengguna. */
    private Karyawan karyawanTerpilih;
    
    /** Layout kontainer dasar yang membagi layar ke 5 wilayah tata letak (Top, Bottom, Left, Right, Center). */
    private BorderPane rootLayout;
    
    // Konstanta Skema Warna UI SaaS Modern (Palet Terkustomisasi)
    private final String COLOR_BG = "#F1F5F9"; 
    private final String COLOR_SIDEBAR = "#4F46E5"; 
    private final String COLOR_PRIMARY = "#3B82F6"; 
    private final String COLOR_TEXT_DARK = "#0F172A"; 
    private final String COLOR_TEXT_MUTED = "#64748B"; 

    /**
     * Constructor untuk membentuk FrameUtamaFX dan langsung memicu rendering UI.
     * 
     * @param mainApp Objek referensi milik MainFX untuk mekanisme routing layar.
     */
    public FrameUtamaFX(MainFX mainApp) {
        this.mainApp = mainApp;
        buatTampilanUtama();
    }

    /**
     * Merakit kerangka utama (Layout Skeleton) dengan menempelkan panel Sidebar di kiri 
     * dan panel Dashboard di tengah.
     */
    private void buatTampilanUtama() {
        rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        rootLayout.setLeft(buatSidebar());
        rootLayout.setCenter(buatHalamanDashboard());
        this.scene = new Scene(rootLayout, 1100, 700);
    }

    /**
     * Membangun panel penahan menu (Sidebar) beraksen melayang di sisi kiri layar.
     * 
     * @return Komponen kontainer vertikal (VBox) berisi tipografi logo dan navigasi button.
     */
    private VBox buatSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPrefWidth(260);
        sidebar.setStyle("-fx-background-color: " + COLOR_SIDEBAR + "; -fx-padding: 30 20;");
        
        DropShadow shadow = new DropShadow(25, Color.rgb(0, 0, 0, 0.1));
        shadow.setOffsetX(5);
        sidebar.setEffect(shadow);

        Label lblLogo1 = new Label("Pay");
        lblLogo1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
        lblLogo1.setTextFill(Color.WHITE); 
        
        Label lblLogo2 = new Label("Roll.");
        lblLogo2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
        lblLogo2.setTextFill(Color.web("#A5B4FC")); 
        
        HBox logoBox = new HBox(lblLogo1, lblLogo2);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(logoBox, new Insets(0, 0, 40, 0));

        Button btnDashboard = buatTombolMenu("Overview");
        Button btnDataKaryawan = buatTombolMenu("Employee Data");

        // Peristiwa navigasi penukaran isi komponen tengah (Center Box)
        btnDashboard.setOnAction(e -> rootLayout.setCenter(buatHalamanDashboard()));
        btnDataKaryawan.setOnAction(e -> rootLayout.setCenter(buatHalamanDataKaryawan()));

        sidebar.getChildren().addAll(logoBox, btnDashboard, btnDataKaryawan);
        return sidebar;
    }

    /**
     * Pabrik komponen (Factory Component) khusus untuk mencetak tombol menu dinamis.
     * Berisi implementasi CSS Pseudo-class Hover untuk animasi transisi warna minimalis.
     * 
     * @param teks Nilai tulisan (String) yang di-render di atas tombol.
     * @return Tombol interaktif (Button).
     */
    private Button buatTombolMenu(String teks) {
        Button btn = new Button(teks);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #C7D2FE; -fx-padding: 15 20; -fx-background-radius: 8;");
        btn.setCursor(Cursor.HAND);
        
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #4338CA; -fx-text-fill: white; -fx-padding: 15 20; -fx-background-radius: 8;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #C7D2FE; -fx-padding: 15 20; -fx-background-radius: 8;"));
        return btn;
    }

    /**
     * Membangun panel Insight Eksekutif (Dashboard) yang menampilkan matrik kuantitatif.
     * Menghitung total data populasi Karyawan Tetap dan Freelance secara otomatis.
     * 
     * @return VBox yang menyajikan statistik berwujud Kartu Grafis (Cards).
     */
    private VBox buatHalamanDashboard() {
        VBox layout = new VBox(30);
        layout.setPadding(new Insets(40));

        VBox headerText = new VBox(5);
        Label lblCompany = new Label("PT MAJU BERSAMA");
        lblCompany.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lblCompany.setTextFill(Color.web(COLOR_PRIMARY));

        Label lblWelcome = new Label("Dashboard Insight");
        lblWelcome.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        lblWelcome.setTextFill(Color.web(COLOR_TEXT_DARK)); 
        
        Label lblSubWelcome = new Label("Pantau ringkasan performa dan pengeluaran gaji perusahaan secara real-time.");
        lblSubWelcome.setFont(Font.font("Segoe UI", 16));
        lblSubWelcome.setTextFill(Color.web(COLOR_TEXT_MUTED));
        headerText.getChildren().addAll(lblCompany, lblWelcome, lblSubWelcome);

        HBox gridKartu = new HBox(25);
        
        // Komputasi Streaming API Data Karyawan dari Main Controller
        int totalKaryawan = mainApp.getListKaryawan().size();
        long totalTetap = mainApp.getListKaryawan().stream().filter(k -> k instanceof KaryawanTetap).count();
        long totalFreelance = mainApp.getListKaryawan().stream().filter(k -> k instanceof Freelance).count();

        gridKartu.getChildren().addAll(
            buatKartuBerwarna("Total Karyawan", String.valueOf(totalKaryawan), "#3B82F6", "#2563EB"),
            buatKartuBerwarna("Karyawan Tetap", String.valueOf(totalTetap), "#10B981", "#059669"),
            buatKartuBerwarna("Freelance", String.valueOf(totalFreelance), "#F59E0B", "#D97706")
        );

        layout.getChildren().addAll(headerText, gridKartu);
        return layout;
    }

    /**
     * Pabrik komponen untuk membentuk bingkai kartu 3D berestetika gradasi linier.
     * 
     * @param judul      Slogan/Keterangan metrik (misal: "Total Karyawan").
     * @param angka      Nilai hasil komputasi yang dirender dalam ukuran masif (bold 48).
     * @param colorStart Nilai hex warna awal gradasi.
     * @param colorEnd   Nilai hex warna ujung pudar gradasi.
     * @return VBox yang dimodifikasi layaknya komponen Panel Kotak (Card Widget).
     */
    private VBox buatKartuBerwarna(String judul, String angka, String colorStart, String colorEnd) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefWidth(240);
        card.setPrefHeight(150);
        
        String gradientStyle = String.format("-fx-background-color: linear-gradient(to bottom right, %s, %s); -fx-background-radius: 16; -fx-padding: 30;", colorStart, colorEnd);
        card.setStyle(gradientStyle);
        
        DropShadow shadow = new DropShadow(20, Color.rgb(0, 0, 0, 0.15));
        shadow.setOffsetY(10);
        card.setEffect(shadow);

        Label lblJudul = new Label(judul);
        lblJudul.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        lblJudul.setTextFill(Color.WHITE);
        lblJudul.setOpacity(0.9);

        Label lblAngka = new Label(angka);
        lblAngka.setFont(Font.font("Segoe UI", FontWeight.BOLD, 48));
        lblAngka.setTextFill(Color.WHITE);

        card.getChildren().addAll(lblJudul, lblAngka);
        return card;
    }

    /**
     * Membangun panel operasional (Employee Directory) yang berisi fitur tabel rekap Data Pegawai
     * beserta navigasi peluncur Modul Registrasi (Form Data).
     * 
     * @return Kontainer VBox interaktif berlapis tabel dan tombol aksi.
     */
    private VBox buatHalamanDataKaryawan() {
        VBox layout = new VBox(25);
        layout.setPadding(new Insets(40));

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);

        VBox textHeader = new VBox(5);
        Label lblJudul = new Label("Direktori Karyawan");
        lblJudul.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblJudul.setTextFill(Color.web(COLOR_TEXT_DARK));
        Label lblSubJudul = new Label("Daftar lengkap pegawai berdasarkan departemen.");
        lblSubJudul.setFont(Font.font("Segoe UI", 15));
        lblSubJudul.setTextFill(Color.web(COLOR_TEXT_MUTED));
        textHeader.getChildren().addAll(lblJudul, lblSubJudul);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnTambah = new Button("+ Tambah Pegawai");
        btnTambah.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI'; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        btnTambah.setOnMouseEntered(e -> btnTambah.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI'; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;"));
        btnTambah.setOnMouseExited(e -> btnTambah.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI'; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;"));

        headerBox.getChildren().addAll(textHeader, spacer, btnTambah);

        // Styling Tabel tanpa border kaku (Borderless Table)
        VBox tableContainer = new VBox();
        tableContainer.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 10;");
        DropShadow tableShadow = new DropShadow(15, Color.rgb(0, 0, 0, 0.04));
        tableContainer.setEffect(tableShadow);

        TableView<Karyawan> tabel = new TableView<>();
        tabel.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: transparent;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 14px;" +
            "-fx-table-cell-border-color: transparent;" +
            "-fx-selection-bar: #EEF2FF;" +             
            "-fx-selection-bar-non-focused: #EEF2FF;" + 
            "-fx-selection-bar-text: #4F46E5;"          
        );
        tabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Karyawan, String> colDept = new TableColumn<>("Departemen");
        colDept.setCellValueFactory(data -> {
            String jab = data.getValue().getJabatan().getNamaJabatan().toLowerCase();
            return new SimpleStringProperty(jab.contains("it") || jab.contains("programmer") ? "IT" : "HRD");
        });

        TableColumn<Karyawan, String> colId = new TableColumn<>("ID Pegawai");
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        
        TableColumn<Karyawan, String> colNama = new TableColumn<>("Nama Lengkap");
        colNama.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNama()));
        
        TableColumn<Karyawan, String> colJabatan = new TableColumn<>("Posisi");
        colJabatan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJabatan().getNamaJabatan()));

        tabel.getColumns().addAll(colDept, colId, colNama, colJabatan);
        
        // Logika Intervensi Style Hover Baris
        tabel.setRowFactory(tv -> {
            TableRow<Karyawan> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) row.setStyle(""); 
            });
            row.setOnMouseEntered(event -> {
                if (!row.isEmpty() && !row.isSelected()) row.setStyle("-fx-background-color: #F8FAFC; -fx-cursor: hand;");
            });
            row.setOnMouseExited(event -> {
                if (!row.isEmpty() && !row.isSelected()) row.setStyle(""); 
            });
            return row;
        });
        
        ObservableList<Karyawan> observableKaryawan = FXCollections.observableArrayList(mainApp.getListKaryawan());
        tabel.setItems(observableKaryawan);
        VBox.setVgrow(tabel, Priority.ALWAYS);
        
        tableContainer.getChildren().add(tabel);
        VBox.setVgrow(tableContainer, Priority.ALWAYS);

        // Trigger peluncur Modul Popup Formulir
        btnTambah.setOnAction(e -> tampilkanFormTambah(tabel));

        Button btnLihatDetail = new Button("Lihat Detail Gaji");
        btnLihatDetail.setStyle("-fx-background-color: " + COLOR_SIDEBAR + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI'; -fx-padding: 12 25; -fx-background-radius: 8; -fx-cursor: hand;");
        btnLihatDetail.setDisable(true);
        
        btnLihatDetail.setOnMouseEntered(e -> {
            if(!btnLihatDetail.isDisabled()) btnLihatDetail.setStyle("-fx-background-color: #4338CA; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI'; -fx-padding: 12 25; -fx-background-radius: 8; -fx-cursor: hand;");
        });
        btnLihatDetail.setOnMouseExited(e -> {
            if(!btnLihatDetail.isDisabled()) btnLihatDetail.setStyle("-fx-background-color: " + COLOR_SIDEBAR + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI'; -fx-padding: 12 25; -fx-background-radius: 8; -fx-cursor: hand;");
        });

        // Event listener tabel (Kondisi saat baris dipilih)
        tabel.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                karyawanTerpilih = newSel;
                btnLihatDetail.setDisable(false);
                btnLihatDetail.setStyle("-fx-background-color: " + COLOR_SIDEBAR + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Segoe UI'; -fx-padding: 12 25; -fx-background-radius: 8; -fx-cursor: hand;");
            }
        });

        btnLihatDetail.setOnAction(e -> mainApp.bukaFrameDetail(karyawanTerpilih));

        HBox bottomBox = new HBox(btnLihatDetail);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);

        layout.getChildren().addAll(headerBox, tableContainer, bottomBox);
        return layout;
    }

    /**
     * Membuka Sub-jendela Popup (Modal Window) untuk melakukan input data secara dinamis.
     * Logika formulir terhubung dengan prinsip instansiasi polymorphism (objek dapat berubah
     * bergantung pada combobox Tipe Pegawai).
     * 
     * @param tabel Komponen TableView yang disuplai agar data dapat direfresh saat submit sukses.
     */
    private void tampilkanFormTambah(TableView<Karyawan> tabel) {
        Stage stage = new Stage();
        // Cegah user mengeklik aplikasi utama saat form pop-up terbuka
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.setTitle("Registrasi Pegawai Baru");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #FFFFFF;");

        Label lblJudul = new Label("Form Input Karyawan");
        lblJudul.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblJudul.setTextFill(Color.web(COLOR_TEXT_DARK));

        TextField txtId = buatTextField("ID Pegawai (Contoh: T-03)");
        TextField txtNama = buatTextField("Nama Lengkap");
        TextField txtJabatan = buatTextField("Nama Jabatan (Contoh: IT Support)");
        
        ComboBox<String> cmbTipe = new ComboBox<>();
        cmbTipe.getItems().addAll("Karyawan Tetap", "Freelance");
        cmbTipe.setValue("Karyawan Tetap");
        cmbTipe.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-background-radius: 8; -fx-pref-width: 350px;");

        TextField txtGajiPokok = buatTextField("Gaji Pokok Jabatan (Rp)");
        TextField txtSpesifik = buatTextField("Masa Kerja (Dalam Tahun)");

        // Mekanisme Form Adaptif (Dinamis)
        cmbTipe.setOnAction(e -> {
            if(cmbTipe.getValue().equals("Freelance")) {
                txtGajiPokok.setText("0");
                txtGajiPokok.setDisable(true); 
                txtSpesifik.setPromptText("Tarif Upah Per Jam (Rp)");
                txtSpesifik.clear();
            } else {
                txtGajiPokok.clear();
                txtGajiPokok.setDisable(false);
                txtSpesifik.setPromptText("Masa Kerja (Dalam Tahun)");
                txtSpesifik.clear();
            }
        });

        Button btnSimpan = new Button("Simpan Data Karyawan");
        btnSimpan.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 8; -fx-pref-width: 350px; -fx-cursor: hand;");
        
        // Pengecekan Validasi dan Parsing Input
        btnSimpan.setOnAction(e -> {
            try {
                String id = txtId.getText().trim();
                String nama = txtNama.getText().trim();
                String namaJab = txtJabatan.getText().trim();
                double gapok = Double.parseDouble(txtGajiPokok.getText());
                int nilaiSpesifik = Integer.parseInt(txtSpesifik.getText()); 

                if(id.isEmpty() || nama.isEmpty() || namaJab.isEmpty()) {
                    throw new IllegalArgumentException("Semua kolom teks harus diisi!");
                }

                Jabatan jab = new Jabatan(namaJab, gapok);
                Karyawan kBaru;

                if(cmbTipe.getValue().equals("Freelance")) {
                    kBaru = new Freelance(id, nama, jab, nilaiSpesifik);
                } else {
                    kBaru = new KaryawanTetap(id, nama, jab, new Tunjangan(nilaiSpesifik));
                }

                mainApp.tambahKaryawan(kBaru);
                
                // Segarkan tabel secara visual (Data Binding)
                tabel.setItems(FXCollections.observableArrayList(mainApp.getListKaryawan()));
                stage.close(); 

            } catch (NumberFormatException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Gaji, Tarif, dan Masa Kerja HARUS berupa angka bulat tanpa huruf/titik/koma!");
                a.show();
            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                a.show();
            }
        });

        layout.getChildren().addAll(lblJudul, txtId, txtNama, txtJabatan, cmbTipe, txtGajiPokok, txtSpesifik, btnSimpan);
        
        Scene s = new Scene(layout, 380, 520);
        stage.setScene(s);
        stage.showAndWait();
    }

    /**
     * Membangun kolom isian teks (Input Field) yang termodifikasi oleh CSS agar presisi dan elegan.
     * 
     * @param prompt Instruksi teks bayangan (placeholder).
     * @return Komponen TextField yang siap dirangkai di dalam form.
     */
    private TextField buatTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #CBD5E1; -fx-border-radius: 8; -fx-background-color: white;");
        return tf;
    }

    /**
     * Mengekspos (mengembalikan) Canvas Layar yang telah dirakit penuh agar dapat dikendalikan MainFX.
     * 
     * @return Objek tipe Scene (Layar UI siap render).
     */
    public Scene getScene() {
        return scene;
    }
}