package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import java.time.*;
import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.UserStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IUserRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IUserService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.ApiException;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.CryptoHelper;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.LoggedInUser;

public class UserService implements IUserService {
    private final IUserRepository repo;

    public UserService(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User login(String username, String password) throws ApiException {
        User user = repo.findByUsername(username);

        if (user == null) {
            throw new ApiException(Constants.ErrorTitle.LOGIN, Constants.ErrorCode.USER_NOT_FOUND,
                    Constants.ErrorMessage.USER_NOT_FOUND);
        }

        if (user.getStatus() == UserStatus.Locked) {
            throw new ApiException(Constants.ErrorTitle.LOGIN, Constants.ErrorCode.USER_ACCOUNT_HAS_BEEN_LOCKED,
                    Constants.ErrorMessage.USER_ACCOUNT_HAS_BEEN_LOCKED);
        }

        var passwordHash = CryptoHelper.encrypt(password);
        if (!user.getPasswordHash().equals(passwordHash)) {
            throw new ApiException(Constants.ErrorTitle.LOGIN, Constants.ErrorCode.USER_PASSWORD_IS_INCORRECT,
                    Constants.ErrorMessage.USER_PASSWORD_IS_INCORRECT);
        }

        user.setLastLoginAt(OffsetDateTime.now());
        repo.update(user);
        
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

    @Override
    public Integer create(User user) {
        user.setId(null);
        user.setCreatedAt(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
        user.setCreatedBy(LoggedInUser.ID);
        user.setUpdatedAt(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
        user.setUpdatedBy(LoggedInUser.ID);
        return repo.insert(user);
    }

    @Override
    public Integer update(User user) {
        user.setUpdatedAt(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
        user.setUpdatedBy(LoggedInUser.ID);
        return repo.update(user);
    }

    @Override
    public Integer delete(Long id) {
        // TODO: Check foreign key constraints
        return repo.delete(id);
    }
}