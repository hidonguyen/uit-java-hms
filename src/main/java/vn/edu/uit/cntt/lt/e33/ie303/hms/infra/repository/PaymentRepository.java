package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Payment;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IPaymentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class PaymentRepository implements IPaymentRepository {
    private final DataSource ds;

    public PaymentRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement query = connection.prepareStatement(Payment.findAllQuery());
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                payments.add(new Payment(rs));
            }
            return payments;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Payment findById(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Payment.findByIdQuery())) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Payment(rs);
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Payment payment) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Payment.insertQuery())) {
            payment.setInsertParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Payment payment) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Payment.updateQuery())) {
            payment.setUpdateParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Payment.deleteQuery())) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
