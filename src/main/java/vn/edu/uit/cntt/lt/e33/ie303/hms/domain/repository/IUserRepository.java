package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;

import java.util.List;

public interface IUserRepository {
    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    int insert(User user);
    int update(User user);
    int delete(Long id);
}
