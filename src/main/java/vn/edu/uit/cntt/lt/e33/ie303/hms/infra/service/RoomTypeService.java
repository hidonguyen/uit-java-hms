package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import java.time.*;
import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IRoomTypeRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IRoomTypeService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.LoggedInUser;

public class RoomTypeService implements IRoomTypeService {
    private final IRoomTypeRepository repo;

    public RoomTypeService(IRoomTypeRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<RoomType> findAll() {
        return repo.findAll();
    }

    @Override
    public RoomType findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Integer create(RoomType roomType) {
        roomType.setId(null);
        roomType.setCreatedAt(LocalDateTime.now());
        roomType.setCreatedBy(LoggedInUser.ID);
        roomType.setUpdatedAt(LocalDateTime.now());
        roomType.setUpdatedBy(LoggedInUser.ID);
        return repo.insert(roomType);
    }

    @Override
    public Integer update(RoomType roomType) {
        roomType.setUpdatedAt(LocalDateTime.now());
        roomType.setUpdatedBy(LoggedInUser.ID);
        return repo.update(roomType);
    }

    @Override
    public Integer delete(Long id) {
        int count = repo.countByRoomTypeId(id);
        if (count > 0) {
            throw new RuntimeException("Room type is used by " + count + " room(s). Reassign or delete rooms first.");
        }
        return repo.delete(id);
    }
}
