package io.finbook.model;

public enum InvoiceType {
    I("Income"),
    R("Refund"),
    S("Salary");

    private final String description;

    InvoiceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
