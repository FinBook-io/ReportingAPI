package io.finbook.model;

public enum InvoiceType {
    INCOME("I"),
    REFUND("R");

    private final String label;

    InvoiceType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
