package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import java.time.LocalDateTime;
import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Room;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IRoomRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IRoomService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.LoggedInUser;

public class RoomService implements IRoomService {
    private final IRoomRepository repo;

    public RoomService(IRoomRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Room> findAll() {
        return repo.findAll();
    }

    @Override
    public Room findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Integer create(Room room) {
        room.setId(null);
        room.setCreatedAt(LocalDateTime.now());
        room.setCreatedBy(LoggedInUser.ID);
        room.setUpdatedAt(LocalDateTime.now());
        room.setUpdatedBy(LoggedInUser.ID);
        return repo.insert(room);
    }

    @Override
    public Integer update(Room room) {
        room.setUpdatedAt(LocalDateTime.now());
        room.setUpdatedBy(LoggedInUser.ID);
        return repo.update(room);
    }

    @Override
    public Integer delete(Long id) {
        return repo.delete(id);
    }
}
