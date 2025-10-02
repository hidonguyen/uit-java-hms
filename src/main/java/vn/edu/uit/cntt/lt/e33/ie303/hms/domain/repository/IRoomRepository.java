package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Room;

public interface IRoomRepository {
    List<Room> findAll();
    List<Room> findByRoomTypeId(Long roomTypeId);
    Room findById(Long id);
    int insert(Room room);
    int update(Room room);
    int delete(Long id);
}
