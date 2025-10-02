package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto;

public class ServiceItem {
    private Long id;
    private String name;
    private String unit;
    private Double unitPrice;

    public ServiceItem(Long id, String name, String unit, Double unitPrice) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.unitPrice = unitPrice;
    }

    public Long getId() { return id; }
    public String toString() { return name; }
    public String getUnit() { return unit; }
    public Double getUnitPrice() { return unitPrice; }
}