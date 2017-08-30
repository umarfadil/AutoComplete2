package com.umarfadil.autocompletetextview;

/**
 * Created by umarfadil on 8/30/17.
 */

public class Klasifikasi {

    private String kode;
    private String name;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
