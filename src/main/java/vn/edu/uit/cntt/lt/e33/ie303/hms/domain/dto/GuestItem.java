package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto;

public class GuestItem {
    private Long id;
    private String name;
    private String phone;

    public GuestItem(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public String toString() { return name; }
    public String getPhone() { return phone; }
}