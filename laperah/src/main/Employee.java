package main;

import enums.Cabang;

public class Employee {
    private final String id;
    private final String name;
    private final Cabang cabang;

    public Employee(String id, String name, Cabang cabang) {
        this.id = id;
        this.name = name;
        this.cabang = cabang;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Cabang getCabang() {
        return cabang;
    }
}
