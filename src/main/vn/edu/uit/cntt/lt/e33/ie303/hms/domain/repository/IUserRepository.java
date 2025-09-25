package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;

public interface IUserRepository {
    User findByUsername(String username);
}
