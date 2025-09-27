package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;

public interface IRoomTypeRepository {
    List<RoomType> findAll();

    RoomType findById(Long id);

    int insert(RoomType roomType);

    int update(RoomType roomType);

    int delete(Long id);

    int countByRoomTypeId(Long roomTypeId);
}
