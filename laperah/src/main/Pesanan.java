package main;

import enums.StatusPesanan;
import menus.Menu;
import tables.Meja;

import java.util.ArrayList;

public class Pesanan {
    private final String id;
    private final Employee employee;
    private final String customerName;
    private final ArrayList<Meja> listMeja = new ArrayList<>();
    private StatusPesanan status = StatusPesanan.inReserve;
    private final ArrayList<Menu> listMenu = new ArrayList<>();

    public Pesanan(Employee employee, String name, int num) {
        this.employee = employee;
        customerName = name;
        id = String.format("%c%03d", employee.getCabang().name().charAt(0), num + 1);
    }

    public String getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ArrayList<Menu> getListMenu() {
        return listMenu;
    }

    public ArrayList<Meja> getListMeja() {
        return listMeja;
    }

    public String getCustomerName() {
        return customerName;
    }

    public StatusPesanan getStatus() {
        return status;
    }

    public void setStatus(StatusPesanan status) {
        this.status = status;
    }

    public void addMeja(Meja meja) {
        listMeja.add(meja);
    }

    public void addMenu(Menu menu) {
        if (status == StatusPesanan.inReserve) {
            status = StatusPesanan.inOrder;
        }
        listMenu.add(menu);
    }

    public void view() {
        System.out.println("\nOrder ID: " + id);
        System.out.println("\nEmployee ID: " + employee.getId());
        System.out.println("Customer Name: " + customerName);
        System.out.println("Status: " + status.name());
        if (status == StatusPesanan.inOrder) {
            System.out.println("\nItems:");
            for (Menu menu : listMenu) {
                System.out.println(menu.getId() + " | " + menu.getName() + " | " + menu.getPrice());
            }
        }
    }

    public void viewReceipt() {
        System.out.println("\nOrder ID: " + id);
        System.out.println("\nEmployee ID: " + employee.getId());
        System.out.println("Customer Name: " + customerName);
        System.out.println("Status: " + status.name());
        System.out.println("\nItems:");
        double sum = 0;
        for (Menu menu : listMenu) {
            System.out.println(menu.getId() + " | " + menu.getName() + " | " + menu.getPrice());
            sum += menu.getPrice();
        }
        System.out.printf("\nTotal: %.2f\n", sum);
    }
}
