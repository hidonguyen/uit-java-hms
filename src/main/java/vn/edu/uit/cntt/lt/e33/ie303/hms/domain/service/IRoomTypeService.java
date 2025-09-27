package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service;

import java.util.List;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;

public interface IRoomTypeService {
    List<RoomType> findAll();

    RoomType findById(Long id);

    Integer create(RoomType roomType);

    Integer update(RoomType roomType);

    Integer delete(Long id);

}
