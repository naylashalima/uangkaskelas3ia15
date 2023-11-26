package com.example.uangkaskelas3ia15.model;

public class PengeluaranModel {
    private String keterangan;
    private int nominal;

    public PengeluaranModel(String keterangan, int nominal) {
        this.keterangan = keterangan;
        this.nominal = nominal;
    }

    public PengeluaranModel() {
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }
}
