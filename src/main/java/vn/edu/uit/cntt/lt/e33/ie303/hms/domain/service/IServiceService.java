package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service;

import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Service;

public interface IServiceService {
    List<Service> findAll();

    Service findById(Long id);

    Integer create(Service service);

    Integer update(Service service);

    Integer delete(Long id);
}
