package com.example.hotelReservationSystem.dataAccess;

import com.example.hotelReservationSystem.entity.concretes.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    boolean existsById(int invoiceId);

    boolean existsByInvoiceNo(String invoiceNo);

    boolean existsByPayment_Id(int paymentId);

    boolean existsByCustomer_UserId(int customerId);

    boolean existsByRoomBooking_Id(int roomBookingId);


    Invoice getInvoiceByInvoiceNo(String invoiceNo);

    Invoice getInvoiceByPayment_Id(int paymentId);

    List<Invoice> getAllByCustomer_UserId(int customerId);

    List<Invoice> getAllByRoomBooking_Id(int roomBookingId);

}
