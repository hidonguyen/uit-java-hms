package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto;

public class RoomItem {
    private Long id;
    private String name;
    private String type;

    public RoomItem(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() { return id; }
    public String toString() { return name; }
    public String getType() { return type; }
}