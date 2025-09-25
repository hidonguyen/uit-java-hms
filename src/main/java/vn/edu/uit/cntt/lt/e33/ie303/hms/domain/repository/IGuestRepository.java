package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Guest;

public interface IGuestRepository {
    List<Guest> findAll();
    Guest findById(Long id);
    int insert(Guest guest);
    int update(Guest guest);
    int delete(Long id);
}
