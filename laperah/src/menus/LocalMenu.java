package menus;

import menus.Menu;

public class LocalMenu extends Menu {
    private final String narasi;
    private final String lokasi;

    public LocalMenu(String id, String name, double price, String narasi, String lokasi) {
        super(id, name, price);
        this.narasi = narasi;
        this.lokasi = lokasi;
    }

    public String getNarasi() {
        return narasi;
    }

    public String getLokasi() {
        return lokasi;
    }
}
