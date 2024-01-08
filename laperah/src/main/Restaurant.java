package main;

import enums.Cabang;
import menus.Menu;

import java.util.ArrayList;

public class Restaurant {
    private final ArrayList<Menu> listMenu = new ArrayList<>();
    private final ArrayList<Pesanan> listPesanan = new ArrayList<>();
    private final Cabang cabang;

    public Restaurant(Cabang cabang) {
        this.cabang = cabang;
    }

    public ArrayList<Menu> getListMenu() {
        return listMenu;
    }

    public ArrayList<Pesanan> getListPesanan() {
        return listPesanan;
    }

    public Cabang getCabang() {
        return cabang;
    }
}
