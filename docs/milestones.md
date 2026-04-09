# Project Milestones — College Management System (Enhanced)

> **College Management System (CMS)** — A comprehensive, production-grade college management platform with GraphQL API, role-based access control, and 15+ integrated modules.

---

## Architecture Overview

| Layer | Technology | Key Change |
|-------|-----------|-----------|
| **API** | Spring for GraphQL | Migrated from REST to GraphQL |
| **Backend** | Spring Boot 3.4, Java 21 | Enhanced entities, new modules |
| **Frontend** | Angular 21, Apollo Angular | GraphQL client replacing HttpClient |
| **Auth** | Keycloak 26 (OAuth2/OIDC) | Enhanced RBAC with field-level security |
| **Database** | H2 (local) / PostgreSQL 16 | 60+ entities with rich relationships |

---

## Summary

| Phase | Description | Total Tasks | Status |
|-------|-------------|:-----------:|--------|
| Phase 1 | Foundation & GraphQL Infrastructure | 8 | Not Started |
| Phase 2 | Enhanced Core Academic Modules | 14 | Not Started |
| Phase 3 | Enhanced Student Lifecycle | 12 | Not Started |
| Phase 4 | Enhanced Faculty & HR | 10 | Not Started |
| Phase 5 | Enhanced Lab & Infrastructure | 10 | Not Started |
| Phase 6 | Enhanced Finance & Accounting | 10 | Not Started |
| Phase 7 | Library Management (New) | 8 | Not Started |
| Phase 8 | Hostel Management (New) | 8 | Not Started |
| Phase 9 | Placement & Career Services (New) | 8 | Not Started |
| Phase 10 | Transport Management (New) | 6 | Not Started |
| Phase 11 | Communication & Notifications (New) | 8 | Not Started |
| Phase 12 | Grievance & Feedback (New) | 6 | Not Started |
| Phase 13 | Enhanced Examination System | 10 | Not Started |
| Phase 14 | Enhanced Analytics & Reporting | 8 | Not Started |
| Phase 15 | Frontend Feature Modules (GraphQL) | 15 | Not Started |

**Total: 141 tasks across 15 phases**

---

## Phase 1: Foundation & GraphQL Infrastructure

> **Focus:** Migrate from REST to GraphQL, enhance security infrastructure.

### 1.1 Backend GraphQL Setup

| # | Task | Status |
|---|------|:------:|
| 1 | Add spring-boot-starter-graphql dependency and configure GraphQL endpoint | Not Started |
| 2 | Create comprehensive GraphQL schema (.graphqls files) covering all 60+ types | Not Started |
| 3 | Implement custom scalar types (DateTime, BigDecimal, LocalDate) | Not Started |
| 4 | Implement GraphQL error handling with proper error extensions | Not Started |
| 5 | Implement GraphQL security — directive-based @auth field-level authorization | Not Started |

### 1.2 Frontend GraphQL Setup

| # | Task | Status |
|---|------|:------:|
| 1 | Install Apollo Angular + graphql packages, configure Apollo client with Keycloak JWT | Not Started |
| 2 | Set up GraphQL code generation for TypeScript types from schema | Not Started |
| 3 | Create base GraphQL service with error handling and caching patterns | Not Started |

---

## Phase 2: Enhanced Core Academic Modules

> **Focus:** Department, Program, Course, Academic Year, Semester — enhanced with rich data.

### 2.1 Department Module (Enhanced)

**Current:** Basic name + code CRUD.
**Enhanced additions:**

| # | Task | Status |
|---|------|:------:|
| 1 | Add HOD assignment (FacultyProfile), vision/mission, established year, accreditation status, budget, contact info | Not Started |
| 2 | GraphQL resolver with nested queries (department -> programs -> courses -> faculty) | Not Started |
| 3 | Frontend — Enhanced department detail page with stats, faculty list, program list | Not Started |
| 4 | Manual test cases for department module | Not Started |

### 2.2 Program Module (Enhanced)

**Current:** Basic name + code + duration + degree type.
**Enhanced additions:**

| # | Task | Status |
|---|------|:------:|
| 1 | Add program coordinator, total intake capacity, eligibility criteria, accreditation details, PLOs | Not Started |
| 2 | GraphQL resolver with nested queries (program -> courses -> syllabus -> outcomes) | Not Started |
| 3 | Manual test cases for program module | Not Started |

### 2.3 Course Module (Enhanced)

**Current:** Basic name + code + credits + courseType.
**Enhanced additions:**

| # | Task | Status |
|---|------|:------:|
| 1 | Add prerequisites, co-requisites, course category (Core/Elective/Open), materials, evaluation scheme | Not Started |
| 2 | Add CourseFeedback entity — student ratings and comments per course per semester | Not Started |
| 3 | GraphQL resolver with nested queries and feedback mutations | Not Started |
| 4 | Manual test cases for course module | Not Started |

### 2.4 Academic Calendar (Enhanced)

| # | Task | Status |
|---|------|:------:|
| 1 | Add recurring events, event categories, semester phase markers | Not Started |
| 2 | Frontend — Calendar view component with month/week/day views | Not Started |
| 3 | Manual test cases for calendar module | Not Started |

---

## Phase 3: Enhanced Student Lifecycle

> **Focus:** Complete student journey from admission to alumni.

### 3.1 Student Profile (Enhanced)

**Current:** Basic profile with keycloakId, name, email, phone, program, department, semester.
**Enhanced additions:**

| # | Task | Status |
|---|------|:------:|
| 1 | Add StudentGuardian entity — name, relation, phone, email, occupation, address | Not Started |
| 2 | Add address (permanent + correspondence), DOB, gender, bloodGroup, nationality, category, religion, aadhaarNumber, photoUrl, emergencyContact, medicalConditions | Not Started |
| 3 | Add StudentPreviousEducation entity — level (10th/12th/UG), board, institution, year, percentage, subjects | Not Started |
| 4 | GraphQL resolver with full nested profile (guardians, education, enrollments, GPA) | Not Started |

### 3.2 Student Academic Tracking (Enhanced)

| # | Task | Status |
|---|------|:------:|
| 1 | Add backlog/arrear tracking with attempt history entity | Not Started |
| 2 | Add academic probation management (auto-trigger on low GPA) | Not Started |
| 3 | Add StudentMentor entity — student, faculty, assignedDate, semester | Not Started |

### 3.3 Student Records & Documents

| # | Task | Status |
|---|------|:------:|
| 1 | Add StudentDocument entity — type (marksheet/certificate/ID), fileUrl, uploadDate, verified | Not Started |
| 2 | Add DisciplinaryRecord entity — student, incidentDate, description, action, status | Not Started |
| 3 | Add StudentAchievement entity — title, category (Academic/Sports/Cultural), date, description, level | Not Started |
| 4 | Add student transfer/migration/withdrawal status tracking | Not Started |
| 5 | Manual test cases for student module | Not Started |

---

## Phase 4: Enhanced Faculty & HR

> **Focus:** Complete faculty management with workload, research, and evaluation.

### 4.1 Faculty Profile (Enhanced)

**Current:** Basic profile with employeeCode, designation, department, joining date.
**Enhanced additions:**

| # | Task | Status |
|---|------|:------:|
| 1 | Add FacultyQualification entity — degree, university, year, specialization | Not Started |
| 2 | Add ResearchPublication entity — title, journal, year, DOI, impactFactor, type | Not Started |
| 3 | Add FacultyWorkload entity — semester, teachingHours, labHours, adminHours, researchHours | Not Started |
| 4 | Add FacultyAppraisal entity — year, teachingRating, researchRating, overallRating, reviewedBy | Not Started |

### 4.2 Faculty Operations

| # | Task | Status |
|---|------|:------:|
| 1 | Add LeaveType + LeaveApplication entities — leave balance, application, approval workflow | Not Started |
| 2 | Add CommitteeMembership entity — faculty, committee, role, startDate, endDate | Not Started |
| 3 | Add faculty timetable generation linking to course and room | Not Started |

### 4.3 Non-Teaching Staff

| # | Task | Status |
|---|------|:------:|
| 1 | Add Staff entity — profiles for admin, technical, support staff | Not Started |
| 2 | Add staff department assignment and designation hierarchy | Not Started |
| 3 | Manual test cases for faculty module | Not Started |

---

## Phase 5: Enhanced Lab & Infrastructure

> **Focus:** Richer lab management, room allocation, infrastructure tracking.

### 5.1 Lab Module (Enhanced)

**Current:** Lab CRUD, equipment, consumables, timetable, maintenance, attendance.
**Enhanced additions:**

| # | Task | Status |
|---|------|:------:|
| 1 | Add LabSafetyRecord entity — safety certificates, inspection dates, incident reports | Not Started |
| 2 | Add LabReservation entity — reservation/booking for ad-hoc lab usage | Not Started |
| 3 | Add SoftwareLicense entity — name, licenseCount, vendor, expiryDate, labAssignment | Not Started |
| 4 | Add lab utilization heatmap data aggregation | Not Started |

### 5.2 Infrastructure Module (Enhanced)

**Current:** Basic room resource and asset tracking.
**Enhanced additions:**

| # | Task | Status |
|---|------|:------:|
| 1 | Add Building entity — name, floors, address; Floor entity — building, floorNumber, rooms | Not Started |
| 2 | Add RoomAllocation entity — room, purpose, course/lab, timeSlot, conflict detection | Not Started |
| 3 | Add MaintenanceRequest entity — requester, location, description, priority, status, assignedTo | Not Started |
| 4 | Add CampusFacility entity — type (parking/cafeteria/sports/auditorium), capacity, status | Not Started |
| 5 | Manual test cases for lab and infrastructure module | Not Started |

### 5.3 Timetable (Enhanced)

| # | Task | Status |
|---|------|:------:|
| 1 | Add TheoryTimetable entity — course, faculty, room, semester, dayOfWeek, startTime, endTime | Not Started |

---

## Phase 6: Enhanced Finance & Accounting

> **Focus:** Complete financial management with installments, refunds, and expense tracking.

### 6.1 Fee Management (Enhanced)

**Current:** Basic fee structure, payment, scholarship.
**Enhanced additions:**

| # | Task | Status |
|---|------|:------:|
| 1 | Add FeeInstallment entity — feeStructure, student, installmentNumber, amount, dueDate, paidDate, status | Not Started |
| 2 | Add FeeConcession entity — student, feeStructure, concessionType, amount, approvedBy, status | Not Started |
| 3 | Add late fee penalty auto-calculation (days overdue x daily rate) | Not Started |
| 4 | Add Refund entity — student, payment, amount, reason, approvedBy, processedDate, status | Not Started |

### 6.2 Financial Operations

| # | Task | Status |
|---|------|:------:|
| 1 | Add Expense entity — department, category, description, amount, date, approvedBy | Not Started |
| 2 | Add DepartmentBudget entity — department, financialYear, allocatedAmount, utilizedAmount | Not Started |
| 3 | Add FinancialYear entity — name, startDate, endDate, active | Not Started |

### 6.3 Salary Management

| # | Task | Status |
|---|------|:------:|
| 1 | Add SalaryStructure entity — designation, basic, DA, HRA, specialAllowance, deductions | Not Started |
| 2 | Add Payroll entity — faculty/staff, month, year, grossPay, deductions, netPay, status | Not Started |
| 3 | Manual test cases for finance module | Not Started |

---

## Phase 7: Library Management (New Module)

> **Focus:** Complete library system with book catalog, circulation, and digital resources.

| # | Task | Status |
|---|------|:------:|
| 1 | Book entity — ISBN, title, author, publisher, edition, category, shelfLocation, totalCopies, availableCopies | Not Started |
| 2 | BookIssue entity — book, member, issueDate, dueDate, returnDate, status, fine | Not Started |
| 3 | LibraryMember entity — linked to StudentProfile/FacultyProfile, membershipType, maxBooksAllowed | Not Started |
| 4 | BookReservation entity — member, book, reservationDate, status, queuePosition | Not Started |
| 5 | DigitalResource entity — title, type (eBook/journal/paper), url, accessLevel, publisher | Not Started |
| 6 | LibraryFine entity — member, amount, reason, paidDate, status | Not Started |
| 7 | GraphQL resolvers for all library operations | Not Started |
| 8 | Manual test cases for library module | Not Started |

---

## Phase 8: Hostel Management (New Module)

> **Focus:** Hostel room allocation, mess management, and visitor tracking.

| # | Task | Status |
|---|------|:------:|
| 1 | Hostel entity — name, type (Boys/Girls), warden (FacultyProfile), capacity, address | Not Started |
| 2 | HostelRoom entity — hostel, roomNumber, floor, roomType (Single/Double/Triple), capacity, status | Not Started |
| 3 | HostelAllocation entity — student, room, academicYear, allocationDate, vacateDate, status | Not Started |
| 4 | MessMenu entity — hostel, dayOfWeek, mealType, items, date | Not Started |
| 5 | HostelFee entity — student, hostel, semester, roomFee, messFee, status | Not Started |
| 6 | VisitorLog entity — student, visitorName, relation, purpose, inTime, outTime | Not Started |
| 7 | GraphQL resolvers for hostel operations | Not Started |
| 8 | Manual test cases for hostel module | Not Started |

---

## Phase 9: Placement & Career Services (New Module)

> **Focus:** Campus recruitment, internships, and career counseling.

| # | Task | Status |
|---|------|:------:|
| 1 | Company entity — name, industry, website, contactPerson, email, tier (Dream/Regular/Service) | Not Started |
| 2 | PlacementDrive entity — company, academicYear, driveDate, eligibilityCriteria, packageOffered | Not Started |
| 3 | PlacementApplication entity — student, drive, applicationDate, status | Not Started |
| 4 | PlacementOffer entity — student, company, drive, role, packageAmount, joiningDate, status | Not Started |
| 5 | Internship entity — student, company, role, startDate, endDate, stipend, mentorName, status | Not Started |
| 6 | PlacementStatistics entity — academicYear, department, totalEligible, totalPlaced, highestPackage, avgPackage | Not Started |
| 7 | GraphQL resolvers for placement operations | Not Started |
| 8 | Manual test cases for placement module | Not Started |

---

## Phase 10: Transport Management (New Module)

> **Focus:** Bus routes, vehicle tracking, and pass management.

| # | Task | Status |
|---|------|:------:|
| 1 | Vehicle entity — registrationNumber, type, capacity, driverName, driverPhone, status | Not Started |
| 2 | TransportRoute entity — routeName, stops (JSON), startTime, endTime, vehicle, distance | Not Started |
| 3 | TransportPass entity — student/faculty, route, validFrom, validTo, passType, fee, status | Not Started |
| 4 | GraphQL resolvers for transport operations | Not Started |
| 5 | Frontend — Route management, pass allocation, vehicle dashboard | Not Started |
| 6 | Manual test cases for transport module | Not Started |

---

## Phase 11: Communication & Notifications (New Module)

> **Focus:** Announcements, messaging, and notification system.

| # | Task | Status |
|---|------|:------:|
| 1 | Announcement entity — title, content, category, targetRoles, targetDepartments, priority, postedBy | Not Started |
| 2 | Notification entity — recipient, title, message, type, readStatus, link, createdAt | Not Started |
| 3 | NotificationPreference entity — user, emailEnabled, smsEnabled, pushEnabled per category | Not Started |
| 4 | Message entity — sender, receiver, subject, body, readStatus, threadId | Not Started |
| 5 | MessageThread entity — participants, subject, lastMessageAt, status | Not Started |
| 6 | GraphQL resolvers with subscriptions for real-time notifications | Not Started |
| 7 | Frontend — Notification bell, announcement board, messaging interface | Not Started |
| 8 | Manual test cases for communication module | Not Started |

---

## Phase 12: Grievance & Feedback (New Module)

> **Focus:** Complaint management and feedback collection.

| # | Task | Status |
|---|------|:------:|
| 1 | Grievance entity — complainant, category, subject, description, status, assignedTo, priority, resolution | Not Started |
| 2 | GrievanceComment entity — grievance, commenter, comment, createdAt, isInternal | Not Started |
| 3 | FeedbackForm entity — title, targetAudience, questions (JSON), startDate, endDate, anonymous | Not Started |
| 4 | FeedbackResponse entity — form, respondent, answers (JSON), submittedAt | Not Started |
| 5 | GraphQL resolvers for grievance workflow and feedback collection | Not Started |
| 6 | Manual test cases for grievance module | Not Started |

---

## Phase 13: Enhanced Examination System

> **Focus:** Complete examination management beyond practicals.

### 13.1 Theory Examination (New)

| # | Task | Status |
|---|------|:------:|
| 1 | ExamSchedule entity — course, semester, examType, date, startTime, duration, room, maxMarks | Not Started |
| 2 | ExamSeatingPlan entity — exam, student, room, seatNumber | Not Started |
| 3 | StudentMark entity — student, exam, course, semester, marksObtained, grade, gradePoint, result | Not Started |
| 4 | GradeScale entity — grade, minPercentage, maxPercentage, gradePoint | Not Started |

### 13.2 Result Processing

| # | Task | Status |
|---|------|:------:|
| 1 | Internal vs external marks split (internal assessment + end-sem) | Not Started |
| 2 | Result publication workflow (draft -> verified -> published) | Not Started |
| 3 | RevaluationRequest entity — student, exam, reason, status, revisedMarks | Not Started |
| 4 | Enhanced GPA/CGPA with grade system, credit weighting, lab components | Not Started |

### 13.3 Question Bank

| # | Task | Status |
|---|------|:------:|
| 1 | QuestionBank entity — course, unit, question, type, bloomLevel, marks, createdBy | Not Started |
| 2 | QuestionPaper entity — exam, course, questions list, totalMarks, sections, approvedBy | Not Started |

---

## Phase 14: Enhanced Analytics & Reporting

> **Focus:** Advanced dashboards, KPIs, and exportable reports.

| # | Task | Status |
|---|------|:------:|
| 1 | Student analytics — enrollment trends, pass/fail rates, GPA distribution, dropout rates | Not Started |
| 2 | Faculty analytics — workload distribution, research output, student feedback scores | Not Started |
| 3 | Financial analytics — revenue vs expense, collection rates, outstanding dues | Not Started |
| 4 | Placement analytics — year-over-year trends, department-wise rates, package distribution | Not Started |
| 5 | Attendance analytics — trend analysis, defaulter lists, department comparison | Not Started |
| 6 | Lab utilization analytics — heatmaps, equipment downtime, cost per experiment | Not Started |
| 7 | Library analytics — popular books, overdue trends, member activity | Not Started |
| 8 | Export functionality — PDF/Excel generation for all report types | Not Started |

---

## Phase 15: Frontend Feature Modules (GraphQL)

> **Focus:** Complete Angular frontend with GraphQL integration for all modules.

| # | Task | Status |
|---|------|:------:|
| 1 | Enhanced Dashboard — Role-based widgets, real-time notifications, configurable layout | Not Started |
| 2 | Administration module — Department detail, Program detail, Course catalog with search/filter | Not Started |
| 3 | Student module — Profile detail with tabs, enrollment history, GPA chart, documents, achievements | Not Started |
| 4 | Faculty module — Profile detail with tabs, publications, workload chart, leave calendar | Not Started |
| 5 | Attendance module — Theory + Lab attendance, QR check-in, defaulter reports | Not Started |
| 6 | Finance module — Fee dashboard, payment history, installment tracker, receipt generation | Not Started |
| 7 | Lab module — Equipment dashboard, booking calendar, utilization heatmap | Not Started |
| 8 | Library module — Book catalog, issue/return, digital resources, membership management | Not Started |
| 9 | Hostel module — Room allocation matrix, mess menu, visitor log | Not Started |
| 10 | Placement module — Drive calendar, application tracker, offer management, statistics | Not Started |
| 11 | Transport module — Route map, pass management, vehicle status | Not Started |
| 12 | Communication module — Announcement board, messaging inbox, notification center | Not Started |
| 13 | Grievance module — Submission form, status tracker, resolution dashboard | Not Started |
| 14 | Examination module — Schedule view, seating plan, result publication, mark entry | Not Started |
| 15 | Analytics module — Interactive charts, filterable reports, PDF/Excel export | Not Started |

---

## Module Enhancement Summary

| Module | Current State | Enhanced State |
|--------|--------------|----------------|
| **Department** | name, code | + HOD, budget, accreditation, contact info, vision/mission |
| **Program** | name, code, duration, degree | + coordinator, intake, eligibility, PLOs, curriculum versions |
| **Course** | name, code, credits, type | + prerequisites, category, materials, feedback, evaluation scheme |
| **Student** | basic profile (8 fields) | + guardian, address, DOB, gender, blood group, medical, previous education, documents, achievements, disciplinary, mentor |
| **Faculty** | basic profile (6 fields) | + qualifications, publications, workload, appraisal, leave, committees |
| **Attendance** | Lab attendance only | + Theory attendance, multiple tracking methods, leave requests, defaulter management |
| **Finance** | basic fee + payment | + installments, penalties, refunds, expenses, budgets, salary |
| **Lab** | equipment + consumables | + safety compliance, booking, software licenses, utilization heatmaps |
| **Infrastructure** | room + asset | + building hierarchy, room allocation, maintenance work orders |
| **Examination** | practical exam only | + theory exams, seating plan, marks/grades, result workflow, question bank |
| **Analytics** | basic snapshots | + multi-dimensional analytics, trend analysis, exportable reports |

## New Modules Added

| Module | Key Entities | Capabilities |
|--------|-------------|-------------|
| **Library** | Book, BookIssue, LibraryMember, BookReservation, DigitalResource, LibraryFine | Catalog, circulation, reservations, fines, digital library |
| **Hostel** | Hostel, HostelRoom, HostelAllocation, MessMenu, HostelFee, VisitorLog | Room allocation, mess, fees, visitor management |
| **Placement** | Company, PlacementDrive, PlacementApplication, PlacementOffer, Internship, PlacementStatistics | Drives, applications, offers, internships, analytics |
| **Transport** | Vehicle, TransportRoute, TransportPass | Routes, vehicles, pass management |
| **Communication** | Announcement, Notification, NotificationPreference, Message, MessageThread | Announcements, messaging, preferences |
| **Grievance** | Grievance, GrievanceComment, FeedbackForm, FeedbackResponse | Complaints, feedback, resolution workflow |

---

## RBAC Matrix

| Module | ADMIN | FACULTY | STUDENT | LAB_INCHARGE | TECHNICIAN |
|--------|:-----:|:-------:|:-------:|:------------:|:----------:|
| Department CRUD | Full | Read | Read | Read | No |
| Program CRUD | Full | Read | Read | No | No |
| Course CRUD | Full | Edit own | Read | No | No |
| Student Profile | Full | Read all | Own profile | No | No |
| Student Academic | Full | Own students | Own records | No | No |
| Faculty Profile | Full | Own profile | Read | No | No |
| Faculty Leave | Approve | Apply/View | No | No | No |
| Attendance | Full | Mark/View | Own records | Lab only | No |
| Finance | Full | Own salary | Own fees | No | No |
| Lab Management | Full | Assigned labs | No | Full | Maintenance |
| Library | Full | Issue/Return | Reserve/View | No | No |
| Hostel | Full | Read (warden) | Own allocation | No | No |
| Placement | Full | Read | Apply/View | No | No |
| Transport | Full | Own pass | Own pass | No | No |
| Grievance | Resolve | Submit/View | Submit/View | Submit/View | Submit/View |
| Examination | Full | Marks/Questions | Own results | No | No |
| Analytics | Full | Department | Own | Lab | No |
| Announcements | Full | Post dept | Read | Post lab | No |

---

*Last updated: 2026-04-09*
