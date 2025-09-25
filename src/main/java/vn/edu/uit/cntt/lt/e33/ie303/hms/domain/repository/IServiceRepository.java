package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Service;

public interface IServiceRepository {
    List<Service> findAll();
    Service findById(Long id);
    int insert(Service service);
    int update(Service service);
    int delete(Long id);
}
