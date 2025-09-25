package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    User findByUsername(String username);
}