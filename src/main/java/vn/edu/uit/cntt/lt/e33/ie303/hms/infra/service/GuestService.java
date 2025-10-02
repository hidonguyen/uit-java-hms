package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import java.time.LocalDateTime;
import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Guest;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IGuestRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IGuestService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.LoggedInUser;

public class GuestService implements IGuestService {
    private final IGuestRepository repo;

    public GuestService(IGuestRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Guest> findAll() {
        return repo.findAll();
    }

    @Override
    public Guest findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Integer create(Guest guest) {
        guest.setId(null);
        guest.setCreatedAt(LocalDateTime.now());
        guest.setCreatedBy(LoggedInUser.ID);
        guest.setUpdatedAt(LocalDateTime.now());
        guest.setUpdatedBy(LoggedInUser.ID);
        return repo.insert(guest);
    }

    @Override
    public Integer update(Guest guest) {
        guest.setUpdatedAt(LocalDateTime.now());
        guest.setUpdatedBy(LoggedInUser.ID);
        return repo.update(guest);
    }

    @Override
    public Integer delete(Long id) {
        return repo.delete(id);
    }
}
