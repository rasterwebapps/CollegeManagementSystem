import { gql } from 'apollo-angular';

// ============ ADMIN QUERIES ============

export const GET_DEPARTMENTS = gql`
  query GetDepartments {
    departments {
      id
      name
      code
      vision
      mission
      email
      phone
      createdAt
      updatedAt
    }
  }
`;

export const GET_PROGRAMS = gql`
  query GetPrograms {
    programs {
      id
      name
      code
      department {
        id
        name
      }
      durationYears
      degreeType
      totalIntake
      eligibilityCriteria
      createdAt
      updatedAt
    }
  }
`;

export const GET_COURSES = gql`
  query GetCourses {
    courses {
      id
      name
      code
      program {
        id
        name
      }
      credits
      courseType
      category
      createdAt
      updatedAt
    }
  }
`;

export const GET_ACADEMIC_YEARS = gql`
  query GetAcademicYears {
    academicYears {
      id
      name
      startDate
      endDate
      active
      createdAt
      updatedAt
    }
  }
`;

export const GET_SEMESTERS = gql`
  query GetSemesters {
    semesters {
      id
      name
      semesterNumber
      academicYear {
        id
        name
      }
      startDate
      endDate
      createdAt
      updatedAt
    }
  }
`;

export const GET_CALENDAR_EVENTS = gql`
  query GetCalendarEvents {
    calendarEvents {
      id
      title
      description
      eventDate
      eventType
      academicYear {
        id
        name
      }
      createdAt
      updatedAt
    }
  }
`;

// ============ ADMIN MUTATIONS ============

export const CREATE_DEPARTMENT = gql`
  mutation CreateDepartment($input: DepartmentInput!) {
    createDepartment(input: $input) {
      id
      name
      code
    }
  }
`;

export const UPDATE_DEPARTMENT = gql`
  mutation UpdateDepartment($id: ID!, $input: DepartmentInput!) {
    updateDepartment(id: $id, input: $input) {
      id
      name
      code
    }
  }
`;

export const DELETE_DEPARTMENT = gql`
  mutation DeleteDepartment($id: ID!) {
    deleteDepartment(id: $id)
  }
`;

export const CREATE_PROGRAM = gql`
  mutation CreateProgram($input: ProgramInput!) {
    createProgram(input: $input) {
      id
      name
      code
    }
  }
`;

export const UPDATE_PROGRAM = gql`
  mutation UpdateProgram($id: ID!, $input: ProgramInput!) {
    updateProgram(id: $id, input: $input) {
      id
      name
      code
    }
  }
`;

export const DELETE_PROGRAM = gql`
  mutation DeleteProgram($id: ID!) {
    deleteProgram(id: $id)
  }
`;

export const DELETE_COURSE = gql`
  mutation DeleteCourse($id: ID!) {
    deleteCourse(id: $id)
  }
`;

export const DELETE_ACADEMIC_YEAR = gql`
  mutation DeleteAcademicYear($id: ID!) {
    deleteAcademicYear(id: $id)
  }
`;

export const DELETE_SEMESTER = gql`
  mutation DeleteSemester($id: ID!) {
    deleteSemester(id: $id)
  }
`;

export const DELETE_CALENDAR_EVENT = gql`
  mutation DeleteCalendarEvent($id: ID!) {
    deleteCalendarEvent(id: $id)
  }
`;

// ============ STUDENT QUERIES ============

export const GET_STUDENT_PROFILES = gql`
  query GetStudentProfiles($page: Int, $size: Int) {
    studentProfiles(page: $page, size: $size) {
      content {
        id
        keycloakId
        enrollmentNumber
        firstName
        lastName
        email
        phone
        currentSemester
        status
        admissionDate
        dateOfBirth
        gender
        program {
          id
          name
        }
        department {
          id
          name
        }
      }
      totalElements
      totalPages
      pageNumber
      pageSize
    }
  }
`;

export const GET_STUDENT_PROFILE = gql`
  query GetStudentProfile($id: ID!) {
    studentProfile(id: $id) {
      id
      keycloakId
      enrollmentNumber
      firstName
      lastName
      email
      phone
      currentSemester
      status
      admissionDate
      dateOfBirth
      gender
      bloodGroup
      nationality
      category
      photoUrl
      program {
        id
        name
      }
      department {
        id
        name
      }
    }
  }
`;

export const GET_STUDENT_GUARDIANS = gql`
  query GetStudentGuardians($studentId: ID!) {
    studentGuardians(studentId: $studentId) {
      id
      name
      relation
      phone
      email
      occupation
      annualIncome
      isPrimary
    }
  }
`;

export const GET_STUDENT_DOCUMENTS = gql`
  query GetStudentDocuments($studentId: ID!) {
    studentDocuments(studentId: $studentId) {
      id
      documentType
      documentName
      fileUrl
      uploadDate
      status
    }
  }
`;

export const GET_STUDENT_ACHIEVEMENTS = gql`
  query GetStudentAchievements($studentId: ID!) {
    studentAchievements(studentId: $studentId) {
      id
      title
      category
      level
      date
      description
      certificateUrl
    }
  }
`;

export const GET_BACKLOGS = gql`
  query GetBacklogs($studentId: ID!) {
    backlogs(studentId: $studentId) {
      id
      course {
        id
        name
      }
      semester {
        id
        name
      }
      originalMarks
      status
      attemptNumber
    }
  }
`;

// ============ LIBRARY QUERIES ============

export const GET_BOOKS = gql`
  query GetBooks {
    books {
      id
      isbn
      title
      author
      publisher
      edition
      publicationYear
      category
      language
      shelfLocation
      totalCopies
      availableCopies
      status
    }
  }
`;

export const GET_LIBRARY_MEMBERS = gql`
  query GetLibraryMembers {
    libraryMembers {
      id
      membershipNumber
      memberType
      maxBooksAllowed
      expiryDate
      status
    }
  }
`;

export const GET_BOOK_ISSUES = gql`
  query GetBookIssues($memberId: ID!) {
    bookIssues(memberId: $memberId) {
      id
      book {
        id
        title
        isbn
      }
      issueDate
      dueDate
      returnDate
      fineAmount
      status
    }
  }
`;

// ============ HOSTEL QUERIES ============

export const GET_HOSTELS = gql`
  query GetHostels {
    hostels {
      id
      name
      code
      hostelType
      totalCapacity
      occupiedBeds
      contactPhone
      contactEmail
      status
    }
  }
`;

export const GET_HOSTEL_ROOMS = gql`
  query GetHostelRooms($hostelId: ID!) {
    hostelRooms(hostelId: $hostelId) {
      id
      roomNumber
      floor
      roomType
      capacity
      currentOccupancy
      monthlyRent
      status
    }
  }
`;

// ============ PLACEMENT QUERIES ============

export const GET_COMPANIES = gql`
  query GetCompanies {
    companies {
      id
      name
      industry
      website
      contactPerson
      tier
      status
    }
  }
`;

export const GET_PLACEMENT_DRIVES = gql`
  query GetPlacementDrives($academicYearId: ID) {
    placementDrives(academicYearId: $academicYearId) {
      id
      company {
        id
        name
      }
      title
      driveDate
      lastDateToApply
      rolesOffered
      packageMin
      packageMax
      registeredCount
      selectedCount
      status
    }
  }
`;

// ============ COMMUNICATION QUERIES ============

export const GET_ANNOUNCEMENTS = gql`
  query GetAnnouncements {
    announcements {
      id
      title
      content
      category
      priority
      publishDate
      expiryDate
      pinned
      publishedBy
      status
    }
  }
`;

export const GET_GRIEVANCES = gql`
  query GetGrievances {
    grievances {
      id
      ticketNumber
      complainantName
      category
      subject
      priority
      status
      createdAt
    }
  }
`;

export const GET_MY_GRIEVANCES = gql`
  query GetMyGrievances($keycloakId: String!) {
    myGrievances(keycloakId: $keycloakId) {
      id
      ticketNumber
      category
      subject
      description
      priority
      status
      resolution
      createdAt
    }
  }
`;

// ============ EXAM QUERIES ============

export const GET_EXAM_SCHEDULES = gql`
  query GetExamSchedules($semesterId: ID) {
    examSchedules(semesterId: $semesterId) {
      id
      course {
        id
        name
      }
      semester {
        id
        name
      }
      examType
      date
      startTime
      duration
      roomLocation
      maxMarks
      passingMarks
      status
    }
  }
`;

export const GET_STUDENT_MARKS = gql`
  query GetStudentMarks($studentId: ID!, $semesterId: ID) {
    studentMarks(studentId: $studentId, semesterId: $semesterId) {
      id
      course {
        id
        name
      }
      internalMarks
      externalMarks
      totalMarks
      maxMarks
      grade
      result
    }
  }
`;

// ============ ATTENDANCE QUERIES ============

export const GET_THEORY_ATTENDANCE = gql`
  query GetTheoryAttendance($studentId: ID!, $courseId: ID!) {
    theoryAttendance(studentId: $studentId, courseId: $courseId) {
      id
      attendanceDate
      status
      remarks
    }
  }
`;

export const GET_THEORY_ATTENDANCE_BY_DATE = gql`
  query GetTheoryAttendanceByDate($courseId: ID!, $date: String!) {
    theoryAttendanceByDate(courseId: $courseId, date: $date) {
      id
      student {
        id
        firstName
        lastName
        enrollmentNumber
      }
      attendanceDate
      status
    }
  }
`;

// ============ COMMUNICATION MUTATIONS ============

export const CREATE_ANNOUNCEMENT = gql`
  mutation CreateAnnouncement($input: AnnouncementInput!) {
    createAnnouncement(input: $input) {
      id
      title
      status
    }
  }
`;

export const CREATE_GRIEVANCE = gql`
  mutation CreateGrievance($input: GrievanceInput!) {
    createGrievance(input: $input) {
      id
      ticketNumber
      status
    }
  }
`;

// ============ EXAM MUTATIONS ============

export const RECORD_STUDENT_MARK = gql`
  mutation RecordStudentMark($input: StudentMarkInput!) {
    recordStudentMark(input: $input) {
      id
      totalMarks
      grade
      result
    }
  }
`;

export const RECORD_THEORY_ATTENDANCE = gql`
  mutation RecordTheoryAttendance($input: TheoryAttendanceInput!) {
    recordTheoryAttendance(input: $input) {
      id
      attendanceDate
      status
    }
  }
`;

// ── Transport ──
export const GET_VEHICLES = gql`
  query GetVehicles {
    vehicles {
      id
      type
      registrationNumber
      capacity
      driverName
      driverPhone
      insuranceExpiry
      status
    }
  }
`;

export const GET_TRANSPORT_ROUTES = gql`
  query GetTransportRoutes {
    transportRoutes {
      id
      routeName
      stops
      startTime
      endTime
      distanceKm
      status
    }
  }
`;

export const GET_TRANSPORT_PASSES = gql`
  query GetTransportPasses {
    transportPasses {
      id
      passType
      validFrom
      validTo
      fee
      status
    }
  }
`;

// ── Infrastructure ──
export const GET_BUILDINGS = gql`
  query GetBuildings {
    buildings {
      id
      name
      code
      floors
      address
      status
    }
  }
`;

export const GET_CAMPUS_FACILITIES = gql`
  query GetCampusFacilities {
    campusFacilities {
      id
      name
      type
      capacity
      location
      description
      status
    }
  }
`;

export const GET_MAINTENANCE_REQUESTS = gql`
  query GetMaintenanceRequests {
    maintenanceRequests {
      id
      requesterName
      location
      description
      priority
      status
      assignedTo
    }
  }
`;

// ── Feedback ──
export const GET_FEEDBACK_FORMS = gql`
  query GetFeedbackForms {
    feedbackForms {
      id
      title
      targetAudience
      startDate
      endDate
      status
    }
  }
`;

export const GET_FEEDBACK_RESPONSES = gql`
  query GetFeedbackResponses($formId: ID!) {
    feedbackResponses(formId: $formId) {
      id
      respondentKeycloakId
      answers
      submittedAt
    }
  }
`;

// ── Grievance ──
export const GET_GRIEVANCE_COMMENTS = gql`
  query GetGrievanceComments($grievanceId: ID!) {
    grievanceComments(grievanceId: $grievanceId) {
      id
      commenterName
      comment
      createdAt
    }
  }
`;

// ── Exams (enhanced) ──
export const GET_GRADE_SCALES = gql`
  query GetGradeScales {
    gradeScales {
      id
      grade
      minPercentage
      maxPercentage
      gradePoint
    }
  }
`;

// ── Hostel (enhanced) ──
export const GET_MESS_MENUS = gql`
  query GetMessMenus($hostelId: ID!) {
    messMenus(hostelId: $hostelId) {
      id
      dayOfWeek
      mealType
      items
    }
  }
`;

// ── Placement (enhanced) ──
export const GET_PLACEMENT_OFFERS = gql`
  query GetPlacementOffers {
    placementOffers {
      id
      role
      packageAmount
      joiningDate
      status
    }
  }
`;

export const GET_INTERNSHIPS = gql`
  query GetInternships {
    internships {
      id
      role
      startDate
      endDate
      stipend
      mentorName
      status
    }
  }
`;

// ── Library (enhanced) ──
export const GET_DIGITAL_RESOURCES = gql`
  query GetDigitalResources {
    digitalResources {
      id
      title
      type
      url
      accessLevel
      status
    }
  }
`;

// ── Communication (enhanced) ──
export const GET_NOTIFICATIONS = gql`
  query GetNotifications($recipientId: String!) {
    notifications(recipientId: $recipientId) {
      id
      title
      message
      type
      readStatus
      link
      createdAt
    }
  }
`;

export const GET_MESSAGES = gql`
  query GetMessages($userId: String!) {
    messages(userId: $userId) {
      id
      senderKeycloakId
      receiverKeycloakId
      subject
      body
      readStatus
      createdAt
    }
  }
`;
