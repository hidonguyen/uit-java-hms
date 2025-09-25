package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IUserRepository;

import java.util.List;

public class UserService implements IUserService {
    private final IUserRepository repo;
    public UserService(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}