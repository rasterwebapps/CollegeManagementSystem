import { Injectable, inject, signal } from '@angular/core';
import { Apollo } from 'apollo-angular';
import {
  GET_DEPARTMENTS,
  GET_PROGRAMS,
  GET_COURSES,
  GET_ACADEMIC_YEARS,
  GET_SEMESTERS,
  GET_CALENDAR_EVENTS,
  CREATE_DEPARTMENT,
  UPDATE_DEPARTMENT,
  DELETE_DEPARTMENT,
  CREATE_PROGRAM,
  UPDATE_PROGRAM,
  DELETE_PROGRAM,
  DELETE_COURSE,
  DELETE_ACADEMIC_YEAR,
  DELETE_SEMESTER,
  DELETE_CALENDAR_EVENT,
} from '../../../core/graphql/operations';

export interface Department {
  id: number;
  name: string;
  code: string;
  vision?: string;
  mission?: string;
  email?: string;
  phone?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Program {
  id: number;
  name: string;
  code: string;
  department: { id: number; name: string };
  durationYears: number;
  degreeType: string;
  totalIntake?: number;
  eligibilityCriteria?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Course {
  id: number;
  name: string;
  code: string;
  program: { id: number; name: string };
  credits: number;
  courseType: string;
  category?: string;
  createdAt: string;
  updatedAt: string;
}

export interface AcademicYear {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface Semester {
  id: number;
  name: string;
  semesterNumber: number;
  academicYear: { id: number; name: string };
  startDate: string;
  endDate: string;
  createdAt: string;
  updatedAt: string;
}

export interface CalendarEvent {
  id: number;
  title: string;
  description: string;
  eventDate: string;
  eventType: string;
  academicYear: { id: number; name: string };
  createdAt: string;
  updatedAt: string;
}

@Injectable({ providedIn: 'root' })
export class AdminService {
  private readonly apollo = inject(Apollo);

  readonly departments = signal<Department[]>([]);
  readonly programs = signal<Program[]>([]);
  readonly courses = signal<Course[]>([]);
  readonly academicYears = signal<AcademicYear[]>([]);
  readonly semesters = signal<Semester[]>([]);
  readonly calendarEvents = signal<CalendarEvent[]>([]);

  loadDepartments() {
    this.apollo
      .watchQuery<{ departments: Department[] }>({ query: GET_DEPARTMENTS })
      .valueChanges.subscribe(({ data }) => this.departments.set((data?.departments ?? []) as Department[]));
  }

  createDepartment(dept: { name: string; code: string }) {
    return this.apollo.mutate<{ createDepartment: Department }>({
      mutation: CREATE_DEPARTMENT,
      variables: { input: dept },
      refetchQueries: [{ query: GET_DEPARTMENTS }],
    });
  }

  updateDepartment(id: number, dept: { name: string; code: string }) {
    return this.apollo.mutate<{ updateDepartment: Department }>({
      mutation: UPDATE_DEPARTMENT,
      variables: { id, input: dept },
      refetchQueries: [{ query: GET_DEPARTMENTS }],
    });
  }

  deleteDepartment(id: number) {
    return this.apollo.mutate({
      mutation: DELETE_DEPARTMENT,
      variables: { id },
      refetchQueries: [{ query: GET_DEPARTMENTS }],
    });
  }

  loadPrograms() {
    this.apollo
      .watchQuery<{ programs: Program[] }>({ query: GET_PROGRAMS })
      .valueChanges.subscribe(({ data }) => this.programs.set((data?.programs ?? []) as Program[]));
  }

  createProgram(p: {
    name: string;
    code: string;
    departmentId: number;
    durationYears: number;
    degreeType: string;
  }) {
    return this.apollo.mutate<{ createProgram: Program }>({
      mutation: CREATE_PROGRAM,
      variables: { input: p },
      refetchQueries: [{ query: GET_PROGRAMS }],
    });
  }

  updateProgram(
    id: number,
    p: { name: string; code: string; departmentId: number; durationYears: number; degreeType: string },
  ) {
    return this.apollo.mutate<{ updateProgram: Program }>({
      mutation: UPDATE_PROGRAM,
      variables: { id, input: p },
      refetchQueries: [{ query: GET_PROGRAMS }],
    });
  }

  deleteProgram(id: number) {
    return this.apollo.mutate({
      mutation: DELETE_PROGRAM,
      variables: { id },
      refetchQueries: [{ query: GET_PROGRAMS }],
    });
  }

  loadCourses() {
    this.apollo
      .watchQuery<{ courses: Course[] }>({ query: GET_COURSES })
      .valueChanges.subscribe(({ data }) => this.courses.set((data?.courses ?? []) as Course[]));
  }

  deleteCourse(id: number) {
    return this.apollo.mutate({
      mutation: DELETE_COURSE,
      variables: { id },
      refetchQueries: [{ query: GET_COURSES }],
    });
  }

  loadAcademicYears() {
    this.apollo
      .watchQuery<{ academicYears: AcademicYear[] }>({ query: GET_ACADEMIC_YEARS })
      .valueChanges.subscribe(({ data }) => this.academicYears.set((data?.academicYears ?? []) as AcademicYear[]));
  }

  deleteAcademicYear(id: number) {
    return this.apollo.mutate({
      mutation: DELETE_ACADEMIC_YEAR,
      variables: { id },
      refetchQueries: [{ query: GET_ACADEMIC_YEARS }],
    });
  }

  loadSemesters() {
    this.apollo
      .watchQuery<{ semesters: Semester[] }>({ query: GET_SEMESTERS })
      .valueChanges.subscribe(({ data }) => this.semesters.set((data?.semesters ?? []) as Semester[]));
  }

  deleteSemester(id: number) {
    return this.apollo.mutate({
      mutation: DELETE_SEMESTER,
      variables: { id },
      refetchQueries: [{ query: GET_SEMESTERS }],
    });
  }

  loadCalendarEvents() {
    this.apollo
      .watchQuery<{ calendarEvents: CalendarEvent[] }>({ query: GET_CALENDAR_EVENTS })
      .valueChanges.subscribe(({ data }) => this.calendarEvents.set((data?.calendarEvents ?? []) as CalendarEvent[]));
  }

  deleteCalendarEvent(id: number) {
    return this.apollo.mutate({
      mutation: DELETE_CALENDAR_EVENT,
      variables: { id },
      refetchQueries: [{ query: GET_CALENDAR_EVENTS }],
    });
  }
}
