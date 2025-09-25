package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IUserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class UserRepository implements IUserRepository {
    private final DataSource ds;

    public UserRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement query = connection.prepareStatement(User.findAllQuery());
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs));
            }
            return users;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(User.findByIdQuery())) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs);
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByUsername(String username) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(User.findByUsernameQuery())) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs);
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(User user) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(User.insertQuery())) {
            user.setInsertParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(User user) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(User.updateQuery())) {
            user.setUpdateParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(User.deleteQuery())) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
