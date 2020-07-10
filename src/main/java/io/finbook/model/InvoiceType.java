package io.finbook.model;

public enum InvoiceType {
    INCOME("income"),
    EGRESS("egress"),
    PAYROLL("payroll");

    private final String label;

    InvoiceType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
