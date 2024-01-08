package menus;

public class Menu {
    private final String id;
    private String name;
    private double price;
    private boolean isOrdered;

    public Menu(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }
}
