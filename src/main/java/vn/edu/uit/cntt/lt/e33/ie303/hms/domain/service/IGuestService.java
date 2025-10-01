package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service;

import java.util.List;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Guest;

public interface IGuestService {
    List<Guest> findAll();

    Guest findById(Long id);

    Integer create(Guest guest);

    Integer update(Guest guest);

    Integer delete(Long id);
}
