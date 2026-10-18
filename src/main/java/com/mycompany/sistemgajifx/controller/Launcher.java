/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemgajifx.controller;

import com.mycompany.sistemgajifx.controller.MainFX;

/**
 * Class pemancing (Launcher) untuk menjalankan aplikasi JavaFX.
 * Class ini digunakan untuk mem-bypass aturan ketat Java Module System 
 * sehingga pustaka JavaFX dapat dimuat dengan lancar tanpa error inisialisasi.
 */
public class Launcher {
    public static void main(String[] args) {
        // Memanggil fungsi main dari class MainFX secara tidak langsung
        MainFX.main(args);
    }
}