package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Service;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IServiceRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ServiceRepository implements IServiceRepository {
    private final DataSource ds;

    public ServiceRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<Service> findAll() {
        List<Service> services = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement query = connection.prepareStatement(Service.findAllQuery());
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                services.add(new Service(rs));
            }
            return services;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Service findById(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Service.findByIdQuery())) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Service(rs);
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Service service) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Service.insertQuery())) {
            service.setInsertParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Service service) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Service.updateQuery())) {
            service.setUpdateParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Service.deleteQuery())) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
