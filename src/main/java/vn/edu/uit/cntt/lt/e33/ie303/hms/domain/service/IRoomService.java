package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service;

import java.util.List;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Room;

public interface IRoomService {
    List<Room> findAll();

    Room findById(Long id);

    Integer create(Room room);

    Integer update(Room room);

    Integer delete(Long id);
}
