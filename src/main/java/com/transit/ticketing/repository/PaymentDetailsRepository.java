package com.transit.ticketing.repository;

import com.transit.ticketing.entity.PaymentsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentDetailsRepository extends JpaRepository<PaymentsDetails,Long> {
    @Query(value = "select * from payment_details where order_id=?1",nativeQuery = true)
    PaymentsDetails findPaymentDetailsForOrderId(long order_id);
}
