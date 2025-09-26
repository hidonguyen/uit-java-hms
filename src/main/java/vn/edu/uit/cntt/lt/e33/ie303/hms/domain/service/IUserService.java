package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service;

import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;

public interface IUserService {
    User login(String username, String password);
    List<User> findAll();
    User findByUsername(String username);
    Integer create(User user);
    Integer update(User user);
    Integer delete(Long id);
}