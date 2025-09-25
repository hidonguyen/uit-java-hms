package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IUserRepository;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository implements IUserRepository {
    private final DataSource ds;

    public UserRepository(DataSource ds) {
        this.ds = ds;
        
        // Run Flyway migrations on startup
        Flyway.configure()
                .dataSource(ds)
                .locations("classpath:db/migration")
                .load()
                .migrate();
        seedIfEmpty();
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id, username, email FROM users WHERE username = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email")
                );
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void seedIfEmpty() {
        String check = "SELECT COUNT(1) FROM users";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(check);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            if (rs.getInt(1) == 0) {
                try (PreparedStatement ins = c.prepareStatement(
                        "INSERT INTO users (username, password, email) VALUES " +
                                "('admin','admin123','admin@example.com'), " +
                                "('user','user123','user@example.com')")) {
                    ins.executeUpdate();
                }
            }
        } catch (Exception e) {
            // ignore seed errors
        }
    }
}
