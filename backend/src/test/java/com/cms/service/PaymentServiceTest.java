package com.cms.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cms.dto.PaymentRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.FeeStructure;
import com.cms.model.Payment;
import com.cms.model.StudentProfile;
import com.cms.repository.FeeStructureRepository;
import com.cms.repository.PaymentRepository;
import com.cms.repository.StudentProfileRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock private PaymentRepository paymentRepo;
    @Mock private StudentProfileRepository studentProfileRepo;
    @Mock private FeeStructureRepository feeStructureRepo;
    @InjectMocks private PaymentService service;

    private Payment payment;
    private StudentProfile student;
    private FeeStructure feeStructure;

    @BeforeEach
    void setUp() {
        student = new StudentProfile();
        student.setId(1L); student.setFirstName("Jane"); student.setLastName("Smith");
        feeStructure = new FeeStructure();
        feeStructure.setId(1L);
        payment = new Payment();
        payment.setId(1L); payment.setStudent(student); payment.setFeeStructure(feeStructure);
        payment.setAmount(new BigDecimal("50000.00")); payment.setPaymentDate(LocalDate.of(2024, 6, 1));
        payment.setPaymentMethod("ONLINE"); payment.setStatus("PENDING");
    }

    @Test void findAll() {
        when(paymentRepo.findAll()).thenReturn(List.of(payment));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));
        var resp = service.findById(1L);
        assertThat(resp.studentName()).isEqualTo("Jane Smith");
        assertThat(resp.feeStructureId()).isEqualTo(1L);
    }

    @Test void findById_nullFeeStructure() {
        payment.setFeeStructure(null);
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));
        assertThat(service.findById(1L).feeStructureId()).isNull();
    }

    @Test void findById_notFound() {
        when(paymentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        PaymentRequest req = new PaymentRequest(1L, 1L, new BigDecimal("50000"), LocalDate.of(2024, 6, 1), "ONLINE", "TXN123", "PENDING", null);
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(feeStructureRepo.findById(1L)).thenReturn(Optional.of(feeStructure));
        when(paymentRepo.save(any())).thenReturn(payment);
        assertThat(service.create(req).amount()).isEqualTo(new BigDecimal("50000.00"));
    }

    @Test void create_nullFeeStructureId() {
        PaymentRequest req = new PaymentRequest(1L, null, new BigDecimal("50000"), LocalDate.of(2024, 6, 1), "ONLINE", null, "PENDING", null);
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(paymentRepo.save(any())).thenReturn(payment);
        service.create(req);
        verify(paymentRepo).save(any());
    }

    @Test void create_nullStatus() {
        PaymentRequest req = new PaymentRequest(1L, null, new BigDecimal("50000"), LocalDate.of(2024, 6, 1), "ONLINE", null, null, null);
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(paymentRepo.save(any())).thenReturn(payment);
        service.create(req);
        verify(paymentRepo).save(any());
    }

    @Test void create_studentNotFound() {
        PaymentRequest req = new PaymentRequest(99L, null, new BigDecimal("50000"), LocalDate.of(2024, 6, 1), "ONLINE", null, null, null);
        when(studentProfileRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_feeStructureNotFound() {
        PaymentRequest req = new PaymentRequest(1L, 99L, new BigDecimal("50000"), LocalDate.of(2024, 6, 1), "ONLINE", null, null, null);
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(feeStructureRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        PaymentRequest req = new PaymentRequest(1L, 1L, new BigDecimal("60000"), LocalDate.of(2024, 7, 1), "CASH", "TXN456", "COMPLETED", "paid");
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(feeStructureRepo.findById(1L)).thenReturn(Optional.of(feeStructure));
        when(paymentRepo.save(any())).thenReturn(payment);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullFeeStructureId() {
        PaymentRequest req = new PaymentRequest(1L, null, new BigDecimal("60000"), LocalDate.of(2024, 7, 1), "CASH", null, "COMPLETED", null);
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(paymentRepo.save(any())).thenReturn(payment);
        service.update(1L, req);
        assertThat(payment.getFeeStructure()).isNull();
    }

    @Test void update_nullStatus() {
        PaymentRequest req = new PaymentRequest(1L, null, new BigDecimal("60000"), LocalDate.of(2024, 7, 1), "CASH", null, null, null);
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));
        when(studentProfileRepo.findById(1L)).thenReturn(Optional.of(student));
        when(paymentRepo.save(any())).thenReturn(payment);
        service.update(1L, req);
        assertThat(payment.getStatus()).isEqualTo("PENDING");
    }

    @Test void update_notFound() {
        PaymentRequest req = new PaymentRequest(1L, null, new BigDecimal("60000"), LocalDate.of(2024, 7, 1), "CASH", null, null, null);
        when(paymentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(paymentRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(paymentRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(paymentRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
