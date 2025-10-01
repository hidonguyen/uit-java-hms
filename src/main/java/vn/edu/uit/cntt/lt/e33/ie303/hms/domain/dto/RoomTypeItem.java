package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto;

public class RoomTypeItem {
    private Long id;
    private String name;

    public RoomTypeItem(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public String toString() { return name; }
}