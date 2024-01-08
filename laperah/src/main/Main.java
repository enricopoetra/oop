package main;

import enums.Cabang;
import enums.StatusPesanan;
import menus.LocalMenu;
import menus.Menu;
import menus.SpecialMenu;
import tables.Family;
import tables.General;
import tables.Meja;
import tables.Romantic;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Restaurant bandung = new Restaurant(Cabang.Bandung);
    private static final Restaurant jakarta = new Restaurant(Cabang.Jakarta);
    private static final Restaurant kuta = new Restaurant(Cabang.Kuta);
    private static final Restaurant surabaya = new Restaurant(Cabang.Surabaya);
    private static final Restaurant samarinda = new Restaurant(Cabang.Samarinda);
    private static final Restaurant padang = new Restaurant(Cabang.Padang);
    private static final ArrayList<Employee> listEmployee = new ArrayList<>();
    private static Employee activeEmployee;
    private static Restaurant activeRestaurant;
    private static final Scanner scan = new Scanner(System.in);

    private static int getIntInput(int max) {
        int opt = -1;
        do {
            System.out.print(">> ");
            try {
                opt = scan.nextInt();
            } catch (Exception ignored) {
            }
            scan.nextLine();
        } while (opt < 0 || opt > max);
        return opt;
    }

    private static int getIntInput(int min, int max, String message) {
        int opt = -1;
        do {
            System.out.print(message);
            try {
                opt = scan.nextInt();
            } catch (Exception ignored) {
            }
            scan.nextLine();
        } while (opt < min || opt > max);
        return opt;
    }

    private static double getFloatInput(int min, double max, String message) {
        double opt = -1;
        do {
            System.out.print(message);
            try {
                opt = scan.nextDouble();
            } catch (Exception ignored) {
            }
            scan.nextLine();
        } while (opt < min || opt > max);
        return opt;
    }

    public static void main(String[] args) {
        // Default employee untuk setiap cabang
        listEmployee.add(new Employee("BA001", "EmpBandung", Cabang.Bandung));
        listEmployee.add(new Employee("JA001", "EmpJakarta", Cabang.Jakarta));
        listEmployee.add(new Employee("KU001", "EmpKuta", Cabang.Kuta));
        listEmployee.add(new Employee("SU001", "EmpSurabaya", Cabang.Surabaya));
        listEmployee.add(new Employee("SA001", "EmpSamarinda", Cabang.Samarinda));
        listEmployee.add(new Employee("PA001", "EmpPadang", Cabang.Padang));

        int opt;
        while (true) {
            System.out.println("\nLaperAh (weird ahh name)");
            System.out.println("===================");
            System.out.println("1. View Reservations");
            System.out.println("2. New Reservation");
            System.out.println("3. View Orders");
            System.out.println("4. New Order");
            System.out.println("5. Checkout");
            System.out.println("6. Modify Menu");
            System.out.println("0. Exit");
            opt = getIntInput(6);
            if (opt == 0) {
                break;
            }

            login();
            switch (opt) {
                case 1:
                    viewReservation(false);
                    break;
                case 2:
                    newReservation();
                    break;
                case 3:
                    viewReservation(true);
                    break;
                case 4:
                    newOrder();
                    break;
                case 5:
                    checkout();
                    break;
                case 6:
                    modify();
                    break;
                default:
                    break;
            }
        }
    }

    private static void modify() {
        System.out.println("\nMenu Modifications:");
        System.out.println("1. Name");
        System.out.println("2. Price");
        System.out.println("3. Delete");
        System.out.println("4. Add");
        System.out.println("0. Back");
        int opt = getIntInput(4);

        boolean valid;
        int menuIndex;
        switch (opt) {
            case 1:
                viewMenu();
                valid = false;
                do {
                    menuIndex = 0;
                    System.out.print("Input Menu ID to Change Name: ");
                    String menuId = scan.nextLine();
                    for (Menu menu : activeRestaurant.getListMenu()) {
                        if (menu.getId().equals(menuId)) {
                            valid = true;
                            break;
                        }
                        menuIndex++;
                    }
                } while (!valid);
                System.out.print("Input New Name: ");
                String name = scan.nextLine();
                activeRestaurant.getListMenu().get(menuIndex).setName(name);
                break;
            case 2:
                viewMenu();
                valid = false;
                do {
                    menuIndex = 0;
                    System.out.print("Input Menu ID to Change Price: ");
                    String menuId = scan.nextLine();
                    for (Menu menu : activeRestaurant.getListMenu()) {
                        if (menu.getId().equals(menuId)) {
                            valid = true;
                            break;
                        }
                        menuIndex++;
                    }
                } while (!valid);
                double price = getFloatInput(0, Double.MAX_VALUE, "Input New Price: ");
                activeRestaurant.getListMenu().get(menuIndex).setPrice(price);
                break;
            case 3:
                viewMenu();
                valid = false;
                do {
                    System.out.print("Input Menu ID to Delete (0 to cancel): ");
                    String menuId = scan.nextLine();
                    if (menuId.equals("0")) {
                        break;
                    }
                    for (Menu menu : activeRestaurant.getListMenu()) {
                        if (menu.getId().equals(menuId)) {
                            if (menu.isOrdered()) {
                                System.out.println("Menu in Order, Cannot Delete");
                                break;
                            }
                            activeRestaurant.getListMenu().remove(menu);
                            valid = true;
                            break;
                        }
                    }
                } while (!valid);
                break;
            case 4:
                addMenu();
                break;
            default:
                break;
        }
    }

    private static void addMenu() {
        String name;
        System.out.print("Input Menu Name: ");
        name = scan.nextLine();
        double price = getFloatInput(0, Double.MAX_VALUE, "Input Menu Price: ");
        while (true) {
            System.out.println("Menu is Special [yes | no]: ");
            String opt  = scan.nextLine();
            if (opt.equals("yes")) {
                String description = scan.nextLine();
                System.out.println("Input Menu Description: ");
                if (!activeEmployee.getCabang().isSpesial()) {
                    System.out.println("Input Menu Location Source: ");
                    String source = scan.nextLine();
                    LocalMenu newMenu = new LocalMenu(String.format("%c%03d", name.toUpperCase().charAt(0), activeRestaurant.getListMenu().size()), name, price, description, source);
                    activeRestaurant.getListMenu().add(newMenu);
                    break;
                }
                SpecialMenu newMenu = new SpecialMenu(String.format("%c%03d", name.toUpperCase().charAt(0), activeRestaurant.getListMenu().size()), name, price, description);
                activeRestaurant.getListMenu().add(newMenu);
                break;
            } else if (opt.equals("no")) {
                break;
            }
        }
        Menu newMenu = new Menu(String.format("%c%03d", name.toUpperCase().charAt(0), activeRestaurant.getListMenu().size()), name, price);
        activeRestaurant.getListMenu().add(newMenu);
    }

    private static void checkout() {
        viewReservation(true);
        String id;
        boolean valid = false;
        do {
            System.out.print("Input Order ID to Checkout: ");
            id = scan.nextLine();
            int i = 0;
            for (Pesanan pesan : activeRestaurant.getListPesanan()) {
                if (pesan.getId().equals(id)) {
                    activeRestaurant.getListPesanan().get(i).setStatus(StatusPesanan.finalized);
                    pesan.viewReceipt();
                    System.out.println("Checkout Successful");
                    valid = true;
                }
                i++;
            }
        } while (!valid);
    }

    private static void viewMenu() {
        for (Menu menu : activeRestaurant.getListMenu()) {
            System.out.println("\nMenu ID: " + menu.getId());
            System.out.println("Name: " + menu.getName());
            System.out.printf("Price: %.2f\n", menu.getPrice());
            if (menu instanceof SpecialMenu) {
                System.out.println("Description:");
                System.out.println(((SpecialMenu) menu).getNarasi());
            }
            if (menu instanceof LocalMenu) {
                System.out.println("Origin: " + ((LocalMenu) menu).getLokasi());
                System.out.println("Description:");
                System.out.println(((LocalMenu) menu).getNarasi());
            }
        }
    }

    private static void newOrder() {
        System.out.println("\nNew Order for:");
        System.out.println("1. Existing Reservation");
        System.out.println("2. New Reservation");
        int opt = getIntInput(1, 2, ">> ");

        Pesanan pesanan = null;
        if (opt == 1) {
            viewReservation(false);
            String id;
            boolean valid = false;
            do {
                System.out.print("Input Reservation ID: ");
                id = scan.nextLine();
                for (Pesanan pesan : activeRestaurant.getListPesanan()) {
                    if (pesan.getId().equals(id)) {
                        pesanan = pesan;
                        valid = true;
                        break;
                    }
                }
            } while (!valid);
        } else {
            pesanan = newReservation();
        }

        viewMenu();
        String menuId;
        do {
            System.out.print("Input Menu ID to Add (0 to finish): ");
            menuId = scan.nextLine();
            int menuIndex = 0;
            for (Menu menu : activeRestaurant.getListMenu()) {
                if (menu.getId().equals(menuId)) {
                    activeRestaurant.getListMenu().get(menuIndex).setOrdered(true);
                    pesanan.addMenu(menu);
                    pesanan.setStatus(StatusPesanan.inOrder);
                }
                menuIndex++;
            }
        } while (!menuId.equals("0"));

        int pesanIndex = 0;
        for (Pesanan pesan : activeRestaurant.getListPesanan()) {
            if (pesan.getId().equals(pesanan.getId())) {
                activeRestaurant.getListPesanan().set(pesanIndex, pesanan);
                break;
            }
            pesanIndex++;
        }
    }

    private static Pesanan newReservation() {
        System.out.println();
        System.out.print("Customer Name: ");
        String name = scan.nextLine();
        Pesanan pesanan = new Pesanan(activeEmployee, name, activeRestaurant.getListPesanan().size());
        int tables = -1;
        do {
            System.out.print("Number of Tables: ");
            try {
                tables = scan.nextInt();
            } catch (Exception ignored) {
            }
            scan.nextLine();
        } while (tables < 1);
        for (int i = 0; i < tables; i++) {
            System.out.println("\nTable " + i + 1);
            int jumlah;
            Meja meja = null;
            do {
                System.out.print("Table type [Romantic | General | Family]: ");
                String type = scan.nextLine();
                jumlah = getIntInput(1, Integer.MAX_VALUE, "Number of person: ");
                meja = switch (type) {
                    case "Romantic" -> new Romantic(jumlah);
                    case "General" -> new General(jumlah);
                    case "Family" -> new Family(jumlah);
                    default -> meja;
                };
            } while (meja == null || meja.getKapasitas() < jumlah);
            pesanan.addMeja(meja);
        }
        activeRestaurant.getListPesanan().add(pesanan);
        return pesanan;
    }

    private static void viewReservation(boolean order) {
        System.out.println("\nReservations:");
        for (Pesanan pesanan : activeRestaurant.getListPesanan()) {
            if (pesanan.getStatus() == StatusPesanan.finalized) {
                continue;
            }
            if (!order && pesanan.getStatus() == StatusPesanan.inOrder) {
                continue;
            }
            if (order && pesanan.getStatus() == StatusPesanan.inReserve) {
                continue;
            }
            pesanan.view();
        }
    }

    private static void login() {
        String id;
        activeEmployee = null;
        System.out.println();
        while (true) {
            System.out.print("Input Employee ID: ");
            id = scan.nextLine();
            for (Employee emp : listEmployee) {
                if (emp.getId().equals(id)) {
                    activeEmployee = emp;
                    switch (emp.getCabang().name()) {
                        case "Bandung":
                            activeRestaurant = bandung;
                            break;
                        case "Jakarta":
                            activeRestaurant = jakarta;
                            break;
                        case "Kuta":
                            activeRestaurant = kuta;
                            break;
                        case "Surabaya":
                            activeRestaurant = surabaya;
                            break;
                        case "Samarinda":
                            activeRestaurant = samarinda;
                            break;
                        case "Padang":
                            activeRestaurant = padang;
                            break;
                    }
                    break;
                }
            }
            if (activeEmployee == null) {
                System.out.println("Invalid ID");
                continue;
            }
            return;
        }
    }
}