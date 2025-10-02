package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import java.time.LocalDateTime;
import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Service;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IServiceRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IServiceService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.LoggedInUser;

public class ServiceService implements IServiceService {
    private final IServiceRepository repo;

    public ServiceService(IServiceRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Service> findAll() {
        return repo.findAll();
    }

    @Override
    public Service findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Integer create(Service service) {
        service.setId(null);
        service.setCreatedAt(LocalDateTime.now());
        service.setCreatedBy(LoggedInUser.ID);
        service.setUpdatedAt(LocalDateTime.now());
        service.setUpdatedBy(LoggedInUser.ID);
        return repo.insert(service);
    }

    @Override
    public Integer update(Service service) {
        service.setUpdatedAt(LocalDateTime.now());
        service.setUpdatedBy(LoggedInUser.ID);
        return repo.update(service);
    }

    @Override
    public Integer delete(Long id) {
        return repo.delete(id);
    }
}
