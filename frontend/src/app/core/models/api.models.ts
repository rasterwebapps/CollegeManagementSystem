// Administration
export interface DepartmentRequest {
  name: string;
  code: string;
  headName?: string;
  description?: string;
  active?: boolean;
}
export interface DepartmentResponse {
  id: number;
  name: string;
  code: string;
  headName?: string;
  description?: string;
  active: boolean;
}

export interface ProgramRequest {
  name: string;
  code: string;
  departmentId: number;
  degreeType: string;
  durationYears: number;
  totalCredits?: number;
  active?: boolean;
}
export interface ProgramResponse {
  id: number;
  name: string;
  code: string;
  departmentId: number;
  departmentName: string;
  degreeType: string;
  durationYears: number;
  totalCredits?: number;
  active: boolean;
}

export interface CourseRequest {
  name: string;
  code: string;
  programId: number;
  departmentId: number;
  credits: number;
  theoryHours: number;
  practicalHours: number;
  courseType: string;
  semesterNumber: number;
  active?: boolean;
}
export interface CourseResponse {
  id: number;
  name: string;
  code: string;
  programId: number;
  programName: string;
  departmentId: number;
  departmentName: string;
  credits: number;
  theoryHours: number;
  practicalHours: number;
  courseType: string;
  semesterNumber: number;
  active: boolean;
}

export interface AcademicYearRequest {
  name: string;
  startDate: string;
  endDate: string;
  current?: boolean;
}
export interface AcademicYearResponse {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
  current: boolean;
}

export interface SemesterRequest {
  academicYearId: number;
  name: string;
  number: number;
  startDate: string;
  endDate: string;
}
export interface SemesterResponse {
  id: number;
  academicYearId: number;
  academicYearName: string;
  name: string;
  number: number;
  startDate: string;
  endDate: string;
}

export interface CalendarEventRequest {
  academicYearId: number;
  title: string;
  eventType: string;
  startDate: string;
  endDate: string;
  description?: string;
}
export interface CalendarEventResponse {
  id: number;
  academicYearId: number;
  academicYearName: string;
  title: string;
  eventType: string;
  startDate: string;
  endDate: string;
  description?: string;
}

// Lab
export interface LabTypeRequest {
  name: string;
  description?: string;
}
export interface LabTypeResponse {
  id: number;
  name: string;
  description?: string;
}

export interface LabRequest {
  name: string;
  labTypeId: number;
  departmentId: number;
  building?: string;
  floor?: string;
  roomNumber?: string;
  capacity: number;
  description?: string;
  status?: string;
  active?: boolean;
}
export interface LabResponse {
  id: number;
  name: string;
  labTypeId: number;
  labTypeName: string;
  departmentId: number;
  departmentName: string;
  building?: string;
  floor?: string;
  roomNumber?: string;
  capacity: number;
  description?: string;
  status: string;
  active: boolean;
}

export interface LabStaffAssignmentRequest {
  labId: number;
  userKeycloakId: string;
  role: string;
  assignedDate?: string;
}
export interface LabStaffAssignmentResponse {
  id: number;
  labId: number;
  labName: string;
  userKeycloakId: string;
  role: string;
  assignedDate: string;
}

export interface LabSummaryResponse {
  totalLabs: number;
  activeLabs: number;
  underMaintenance: number;
  inactive: number;
}

// Curriculum
export interface SyllabusRequest {
  courseId: number;
  academicYearId: number;
  content?: string;
  version?: number;
  approved?: boolean;
}
export interface SyllabusResponse {
  id: number;
  courseId: number;
  courseName: string;
  academicYearId: number;
  academicYearName: string;
  content?: string;
  version: number;
  approved: boolean;
}

export interface CourseOutcomeRequest {
  courseId: number;
  code: string;
  description: string;
  bloomLevel: string;
}
export interface CourseOutcomeResponse {
  id: number;
  courseId: number;
  courseName: string;
  code: string;
  description: string;
  bloomLevel: string;
}

export interface ExperimentRequest {
  courseId: number;
  name: string;
  description?: string;
  objective?: string;
  procedureText?: string;
  preRequirements?: string;
  sequenceOrder?: number;
}
export interface ExperimentResponse {
  id: number;
  courseId: number;
  courseName: string;
  name: string;
  description?: string;
  objective?: string;
  procedureText?: string;
  preRequirements?: string;
  sequenceOrder: number;
}

export interface ExperimentOutcomeMappingRequest {
  experimentId: number;
  courseOutcomeId: number;
  mappingLevel: string;
}
export interface ExperimentOutcomeMappingResponse {
  experimentId: number;
  experimentName: string;
  courseOutcomeId: number;
  courseOutcomeCode: string;
  mappingLevel: string;
}

// Faculty
export interface FacultyProfileRequest {
  keycloakUserId: string;
  employeeId: string;
  departmentId: number;
  designation?: string;
  qualification?: string;
  specialization?: string;
  joiningDate?: string;
  phone?: string;
  active?: boolean;
}
export interface FacultyProfileResponse {
  id: number;
  keycloakUserId: string;
  employeeId: string;
  departmentId: number;
  departmentName: string;
  designation?: string;
  qualification?: string;
  specialization?: string;
  joiningDate?: string;
  phone?: string;
  active: boolean;
}

export interface FacultyLabExpertiseRequest {
  facultyId: number;
  labTypeId: number;
  proficiencyLevel: string;
  certified?: boolean;
}
export interface FacultyLabExpertiseResponse {
  id: number;
  facultyId: number;
  employeeId: string;
  labTypeId: number;
  labTypeName: string;
  proficiencyLevel: string;
  certified: boolean;
}

export interface FacultyLabAssignmentRequest {
  facultyId: number;
  labId: number;
  courseId: number;
  academicYearId: number;
  semesterId: number;
}
export interface FacultyLabAssignmentResponse {
  id: number;
  facultyId: number;
  employeeId: string;
  labId: number;
  labName: string;
  courseId: number;
  courseName: string;
  academicYearId: number;
  academicYearName: string;
  semesterId: number;
  semesterName: string;
}
