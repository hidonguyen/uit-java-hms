package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Payment;

public interface IPaymentRepository {
    List<Payment> findAll();
    Payment findById(Long id);
    int insert(Payment payment);
    int update(Payment payment);
    int delete(Long id);
}
