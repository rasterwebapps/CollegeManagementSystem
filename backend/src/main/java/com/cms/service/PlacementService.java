package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Company;
import com.cms.model.PlacementDrive;
import com.cms.model.PlacementApplication;
import com.cms.repository.CompanyRepository;
import com.cms.repository.PlacementDriveRepository;
import com.cms.repository.PlacementApplicationRepository;

@Service
public class PlacementService {

    private final CompanyRepository companyRepository;
    private final PlacementDriveRepository placementDriveRepository;
    private final PlacementApplicationRepository placementApplicationRepository;

    public PlacementService(CompanyRepository companyRepository,
                            PlacementDriveRepository placementDriveRepository,
                            PlacementApplicationRepository placementApplicationRepository) {
        this.companyRepository = companyRepository;
        this.placementDriveRepository = placementDriveRepository;
        this.placementApplicationRepository = placementApplicationRepository;
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public Company findCompanyById(Long id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Company", id));
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<PlacementDrive> findAllDrives() {
        return placementDriveRepository.findAll();
    }

    public PlacementDrive findDriveById(Long id) {
        return placementDriveRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("PlacementDrive", id));
    }

    public PlacementDrive createDrive(PlacementDrive drive) {
        return placementDriveRepository.save(drive);
    }

    public List<PlacementDrive> findDrivesByStatus(String status) {
        return placementDriveRepository.findByStatus(status);
    }

    public List<PlacementApplication> findApplicationsByDriveId(Long driveId) {
        return placementApplicationRepository.findByDriveId(driveId);
    }

    public List<PlacementApplication> findApplicationsByStudentId(Long studentId) {
        return placementApplicationRepository.findByStudentId(studentId);
    }

    public PlacementApplication createApplication(PlacementApplication application) {
        return placementApplicationRepository.save(application);
    }

    public PlacementApplication findApplicationById(Long id) {
        return placementApplicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("PlacementApplication", id));
    }
}
