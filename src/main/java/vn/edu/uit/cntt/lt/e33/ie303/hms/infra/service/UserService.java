package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IUserRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IUserService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.CryptoHelper;

import java.util.List;

public class UserService implements IUserService {
    private final IUserRepository repo;
    public UserService(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User login(String username, String password) {
        User user = repo.findByUsername(username);

        if (user == null) {
            return null;
        }

        var passwordHash = CryptoHelper.encrypt(password);
        if (!user.getPasswordHash().equals(passwordHash)) {
            return null;
        }
        
        return user;
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