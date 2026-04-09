package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class FinanceService {
    private final FeeInstallmentRepository installmentRepo;
    private final FeeConcessionRepository concessionRepo;
    private final RefundRepository refundRepo;
    private final ExpenseRepository expenseRepo;
    private final DepartmentBudgetRepository budgetRepo;
    private final FinancialYearRepository financialYearRepo;
    private final SalaryStructureRepository salaryRepo;
    private final PayrollRepository payrollRepo;

    public FinanceService(FeeInstallmentRepository installmentRepo, FeeConcessionRepository concessionRepo, RefundRepository refundRepo, ExpenseRepository expenseRepo, DepartmentBudgetRepository budgetRepo, FinancialYearRepository financialYearRepo, SalaryStructureRepository salaryRepo, PayrollRepository payrollRepo) {
        this.installmentRepo = installmentRepo; this.concessionRepo = concessionRepo; this.refundRepo = refundRepo;
        this.expenseRepo = expenseRepo; this.budgetRepo = budgetRepo; this.financialYearRepo = financialYearRepo;
        this.salaryRepo = salaryRepo; this.payrollRepo = payrollRepo;
    }

    public List<FeeInstallment> findAllInstallments() { return installmentRepo.findAll(); }
    public FeeInstallment createInstallment(FeeInstallment i) { return installmentRepo.save(i); }
    public FeeInstallment updateInstallment(FeeInstallment i) { return installmentRepo.save(i); }

    public List<FeeConcession> findAllConcessions() { return concessionRepo.findAll(); }
    public FeeConcession createConcession(FeeConcession c) { return concessionRepo.save(c); }
    public FeeConcession approveConcession(Long id, String approver) { return concessionRepo.findById(id).map(c -> { c.setApprovedBy(approver); c.setStatus("APPROVED"); return concessionRepo.save(c); }).orElse(null); }

    public List<Refund> findAllRefunds() { return refundRepo.findAll(); }
    public Refund createRefund(Refund r) { return refundRepo.save(r); }
    public Refund processRefund(Long id) { return refundRepo.findById(id).map(r -> { r.setStatus("PROCESSED"); r.setProcessedDate(java.time.LocalDate.now()); return refundRepo.save(r); }).orElse(null); }

    public List<Expense> findAllExpenses() { return expenseRepo.findAll(); }
    public Expense createExpense(Expense e) { return expenseRepo.save(e); }

    public List<DepartmentBudget> findAllBudgets() { return budgetRepo.findAll(); }
    public DepartmentBudget createBudget(DepartmentBudget b) { return budgetRepo.save(b); }
    public DepartmentBudget updateBudget(DepartmentBudget b) { return budgetRepo.save(b); }

    public List<FinancialYear> findAllFinancialYears() { return financialYearRepo.findAll(); }
    public FinancialYear createFinancialYear(FinancialYear f) { return financialYearRepo.save(f); }

    public List<SalaryStructure> findAllSalaryStructures() { return salaryRepo.findAll(); }
    public SalaryStructure createSalaryStructure(SalaryStructure s) { return salaryRepo.save(s); }

    public List<Payroll> findAllPayrolls() { return payrollRepo.findAll(); }
    public Payroll generatePayroll(Payroll p) { return payrollRepo.save(p); }
}
