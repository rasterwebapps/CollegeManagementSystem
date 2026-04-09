package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.PaymentRequest;
import com.cms.dto.PaymentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.FeeStructure;
import com.cms.model.Payment;
import com.cms.model.StudentProfile;
import com.cms.repository.FeeStructureRepository;
import com.cms.repository.PaymentRepository;
import com.cms.repository.StudentProfileRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final FeeStructureRepository feeStructureRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          StudentProfileRepository studentProfileRepository,
                          FeeStructureRepository feeStructureRepository) {
        this.paymentRepository = paymentRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.feeStructureRepository = feeStructureRepository;
    }

    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll().stream().map(this::toResponse).toList();
    }

    public PaymentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public PaymentResponse create(PaymentRequest request) {
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Payment p = new Payment();
        p.setStudent(student);
        if (request.feeStructureId() != null) {
            FeeStructure fs = feeStructureRepository.findById(request.feeStructureId())
                .orElseThrow(() -> new ResourceNotFoundException("FeeStructure", request.feeStructureId()));
            p.setFeeStructure(fs);
        }
        p.setAmount(request.amount());
        p.setPaymentDate(request.paymentDate());
        p.setPaymentMethod(request.paymentMethod());
        p.setTransactionReference(request.transactionReference());
        p.setStatus(request.status() != null ? request.status() : "PENDING");
        p.setRemarks(request.remarks());
        return toResponse(paymentRepository.save(p));
    }

    public PaymentResponse update(Long id, PaymentRequest request) {
        Payment p = getOrThrow(id);
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        p.setStudent(student);
        if (request.feeStructureId() != null) {
            FeeStructure fs = feeStructureRepository.findById(request.feeStructureId())
                .orElseThrow(() -> new ResourceNotFoundException("FeeStructure", request.feeStructureId()));
            p.setFeeStructure(fs);
        } else {
            p.setFeeStructure(null);
        }
        p.setAmount(request.amount());
        p.setPaymentDate(request.paymentDate());
        p.setPaymentMethod(request.paymentMethod());
        p.setTransactionReference(request.transactionReference());
        if (request.status() != null) { p.setStatus(request.status()); }
        p.setRemarks(request.remarks());
        return toResponse(paymentRepository.save(p));
    }

    public void delete(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payment", id);
        }
        paymentRepository.deleteById(id);
    }

    private Payment getOrThrow(Long id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
    }

    private PaymentResponse toResponse(Payment p) {
        return new PaymentResponse(
            p.getId(), p.getStudent().getId(),
            p.getStudent().getFirstName() + " " + p.getStudent().getLastName(),
            p.getFeeStructure() != null ? p.getFeeStructure().getId() : null,
            p.getAmount(), p.getPaymentDate(), p.getPaymentMethod(),
            p.getTransactionReference(), p.getStatus(), p.getRemarks(),
            p.getCreatedAt(), p.getUpdatedAt()
        );
    }
}
