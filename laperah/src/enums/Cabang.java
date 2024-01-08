package enums;

public enum Cabang {
    Bandung(true),
    Jakarta(true),
    Kuta(true),
    Surabaya(false),
    Samarinda(false),
    Padang(false);

    private final boolean spesial;
    Cabang(boolean spesial) {
        this.spesial = spesial;
    }
    public boolean isSpesial() {
        return spesial;
    }
}
