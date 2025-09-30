package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto;

public class ServiceItem {
    private Long id;
    private String name;
    private Double unitPrice;

    public ServiceItem(Long id, String name, Double unitPrice) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public Long getId() { return id; }
    public String toString() { return name; }
    public Double getUnitPrice() { return unitPrice; }
}