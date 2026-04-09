package com.cms.controller.graphql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import com.cms.model.*;
import com.cms.repository.*;
import com.cms.service.FinanceService;

@Controller
public class FinanceGraphQLController {
    private final FinanceService financeService;
    private final StudentProfileRepository studentRepo;
    private final FeeStructureRepository feeStructureRepo;
    private final DepartmentRepository departmentRepo;
    private final FacultyProfileRepository facultyRepo;
    private final PaymentRepository paymentRepo;

    public FinanceGraphQLController(FinanceService financeService, StudentProfileRepository studentRepo, FeeStructureRepository feeStructureRepo, DepartmentRepository departmentRepo, FacultyProfileRepository facultyRepo, PaymentRepository paymentRepo) {
        this.financeService = financeService; this.studentRepo = studentRepo; this.feeStructureRepo = feeStructureRepo;
        this.departmentRepo = departmentRepo; this.facultyRepo = facultyRepo; this.paymentRepo = paymentRepo;
    }

    @QueryMapping
    public List<FeeInstallment> feeInstallments(@Argument Long studentId) {
        return financeService.findAllInstallments().stream().filter(i -> studentId == null || i.getStudent().getId().equals(studentId)).toList();
    }

    @QueryMapping
    public List<FeeConcession> feeConcessions(@Argument Long studentId) {
        return financeService.findAllConcessions().stream().filter(c -> studentId == null || c.getStudent().getId().equals(studentId)).toList();
    }

    @QueryMapping
    public List<Refund> refunds(@Argument Long studentId) {
        return financeService.findAllRefunds().stream().filter(r -> studentId == null || r.getStudent().getId().equals(studentId)).toList();
    }

    @QueryMapping
    public List<Expense> expenses(@Argument Long departmentId, @Argument Long financialYearId) {
        return financeService.findAllExpenses().stream()
            .filter(e -> (departmentId == null || (e.getDepartment() != null && e.getDepartment().getId().equals(departmentId))))
            .toList();
    }

    @QueryMapping
    public List<DepartmentBudget> departmentBudgets(@Argument Long departmentId, @Argument Long financialYearId) {
        return financeService.findAllBudgets().stream()
            .filter(b -> (departmentId == null || b.getDepartment().getId().equals(departmentId)))
            .toList();
    }

    @QueryMapping
    public List<FinancialYear> financialYears() { return financeService.findAllFinancialYears(); }

    @QueryMapping
    public List<SalaryStructure> salaryStructures() { return financeService.findAllSalaryStructures(); }

    @QueryMapping
    public List<Payroll> payrolls(@Argument Long facultyId, @Argument Integer month, @Argument Integer year) {
        return financeService.findAllPayrolls().stream()
            .filter(p -> (facultyId == null || p.getFaculty().getId().equals(facultyId)) && (month == null || p.getMonth() == month) && (year == null || p.getYear() == year))
            .toList();
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FeeInstallment createFeeInstallment(@Argument Map<String, Object> input) {
        FeeInstallment i = new FeeInstallment();
        if (input.get("feeStructureId") != null) i.setFeeStructure(feeStructureRepo.findById(Long.valueOf(input.get("feeStructureId").toString())).orElse(null));
        if (input.get("studentId") != null) i.setStudent(studentRepo.findById(Long.valueOf(input.get("studentId").toString())).orElse(null));
        if (input.get("installmentNumber") != null) i.setInstallmentNumber((Integer) input.get("installmentNumber"));
        if (input.get("amount") != null) i.setAmount(new BigDecimal(input.get("amount").toString()));
        if (input.get("dueDate") != null) i.setDueDate(LocalDate.parse((String) input.get("dueDate")));
        i.setStatus((String) input.getOrDefault("status", "PENDING"));
        return financeService.createInstallment(i);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FeeInstallment updateFeeInstallment(@Argument Long id, @Argument Map<String, Object> input) {
        FeeInstallment i = financeService.findAllInstallments().stream().filter(inst -> inst.getId().equals(id)).findFirst().orElse(null);
        if (i == null) return null;
        if (input.containsKey("paidDate")) i.setPaidDate(LocalDate.parse((String) input.get("paidDate")));
        if (input.containsKey("status")) i.setStatus((String) input.get("status"));
        return financeService.updateInstallment(i);
    }

    @MutationMapping
    public FeeConcession createFeeConcession(@Argument Map<String, Object> input) {
        FeeConcession c = new FeeConcession();
        if (input.get("studentId") != null) c.setStudent(studentRepo.findById(Long.valueOf(input.get("studentId").toString())).orElse(null));
        if (input.get("feeStructureId") != null) c.setFeeStructure(feeStructureRepo.findById(Long.valueOf(input.get("feeStructureId").toString())).orElse(null));
        c.setConcessionType((String) input.get("concessionType"));
        if (input.get("amount") != null) c.setAmount(new BigDecimal(input.get("amount").toString()));
        c.setReason((String) input.get("reason"));
        c.setStatus("PENDING");
        return financeService.createConcession(c);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FeeConcession approveFeeConcession(@Argument Long id, @Argument String approvedBy) {
        return financeService.approveConcession(id, approvedBy);
    }

    @MutationMapping
    public Refund createRefund(@Argument Map<String, Object> input) {
        Refund r = new Refund();
        if (input.get("studentId") != null) r.setStudent(studentRepo.findById(Long.valueOf(input.get("studentId").toString())).orElse(null));
        if (input.get("paymentId") != null) r.setPayment(paymentRepo.findById(Long.valueOf(input.get("paymentId").toString())).orElse(null));
        if (input.get("amount") != null) r.setAmount(new BigDecimal(input.get("amount").toString()));
        r.setReason((String) input.get("reason"));
        r.setStatus("PENDING");
        return financeService.createRefund(r);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Refund processRefund(@Argument Long id, @Argument String approvedBy) {
        return financeService.processRefund(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Expense createExpense(@Argument Map<String, Object> input) {
        Expense e = new Expense();
        if (input.get("departmentId") != null) e.setDepartment(departmentRepo.findById(Long.valueOf(input.get("departmentId").toString())).orElse(null));
        e.setCategory((String) input.get("category"));
        e.setDescription((String) input.get("description"));
        if (input.get("amount") != null) e.setAmount(new BigDecimal(input.get("amount").toString()));
        if (input.get("expenseDate") != null) e.setExpenseDate(LocalDate.parse((String) input.get("expenseDate")));
        e.setReceiptNumber((String) input.get("receiptNumber"));
        e.setApprovedBy((String) input.get("approvedBy"));
        e.setStatus((String) input.getOrDefault("status", "PENDING"));
        return financeService.createExpense(e);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DepartmentBudget createDepartmentBudget(@Argument Map<String, Object> input) {
        DepartmentBudget b = new DepartmentBudget();
        if (input.get("departmentId") != null) b.setDepartment(departmentRepo.findById(Long.valueOf(input.get("departmentId").toString())).orElse(null));
        if (input.get("financialYearId") != null) b.setFinancialYear(financeService.findAllFinancialYears().stream().filter(f -> f.getId().equals(Long.valueOf(input.get("financialYearId").toString()))).findFirst().orElse(null));
        if (input.get("allocatedAmount") != null) b.setAllocatedAmount(new BigDecimal(input.get("allocatedAmount").toString()));
        b.setUtilizedAmount(BigDecimal.ZERO);
        return financeService.createBudget(b);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DepartmentBudget updateDepartmentBudget(@Argument Long id, @Argument Map<String, Object> input) {
        DepartmentBudget b = financeService.findAllBudgets().stream().filter(bd -> bd.getId().equals(id)).findFirst().orElse(null);
        if (b == null) return null;
        if (input.containsKey("utilizedAmount")) b.setUtilizedAmount(new BigDecimal(input.get("utilizedAmount").toString()));
        return financeService.updateBudget(b);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FinancialYear createFinancialYear(@Argument Map<String, Object> input) {
        FinancialYear f = new FinancialYear();
        f.setName((String) input.get("name"));
        if (input.get("startDate") != null) f.setStartDate(LocalDate.parse((String) input.get("startDate")));
        if (input.get("endDate") != null) f.setEndDate(LocalDate.parse((String) input.get("endDate")));
        f.setActive(Boolean.TRUE.equals(input.get("active")));
        return financeService.createFinancialYear(f);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public SalaryStructure createSalaryStructure(@Argument Map<String, Object> input) {
        SalaryStructure s = new SalaryStructure();
        s.setDesignation((String) input.get("designation"));
        if (input.get("basicPay") != null) s.setBasicPay(new BigDecimal(input.get("basicPay").toString()));
        if (input.get("da") != null) s.setDa(new BigDecimal(input.get("da").toString()));
        if (input.get("hra") != null) s.setHra(new BigDecimal(input.get("hra").toString()));
        if (input.get("specialAllowance") != null) s.setSpecialAllowance(new BigDecimal(input.get("specialAllowance").toString()));
        if (input.get("deductions") != null) s.setDeductions(new BigDecimal(input.get("deductions").toString()));
        return financeService.createSalaryStructure(s);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Payroll generatePayroll(@Argument Map<String, Object> input) {
        Payroll p = new Payroll();
        if (input.get("facultyId") != null) p.setFaculty(facultyRepo.findById(Long.valueOf(input.get("facultyId").toString())).orElse(null));
        if (input.get("month") != null) p.setMonth((Integer) input.get("month"));
        if (input.get("year") != null) p.setYear((Integer) input.get("year"));
        if (input.get("grossPay") != null) p.setGrossPay(new BigDecimal(input.get("grossPay").toString()));
        if (input.get("deductions") != null) p.setDeductions(new BigDecimal(input.get("deductions").toString()));
        if (input.get("netPay") != null) p.setNetPay(new BigDecimal(input.get("netPay").toString()));
        p.setStatus("GENERATED");
        return financeService.generatePayroll(p);
    }
}
