package menus;

import menus.Menu;

public class SpecialMenu extends Menu {
    private final String narasi;

    public SpecialMenu(String id, String name, double price, String narasi) {
        super(id, name, price);
        this.narasi = narasi;
    }

    public String getNarasi() {
        return narasi;
    }
}
