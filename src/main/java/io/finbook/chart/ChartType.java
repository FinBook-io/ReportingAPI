package io.finbook.chart;

public enum ChartType {
    BAR("bar"),
    PIE("pie");

    private final String label;

    ChartType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
