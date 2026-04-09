import { Injectable, inject, signal } from '@angular/core';
import { Apollo } from 'apollo-angular';
import {
  GET_EXAM_SCHEDULES,
  GET_STUDENT_MARKS,
  GET_GRADE_SCALES,
  RECORD_STUDENT_MARK,
} from '../../../core/graphql/operations';

export interface ExamSchedule {
  id: number;
  course: { id: number; name: string };
  semester: { id: number; name: string };
  examType: string;
  date: string;
  startTime: string;
  duration: number;
  roomLocation: string;
  maxMarks: number;
  passingMarks: number;
  status: string;
}

export interface StudentMark {
  id: number;
  course: { id: number; name: string };
  internalMarks: number;
  externalMarks: number;
  totalMarks: number;
  maxMarks: number;
  grade: string;
  result: string;
}

export interface GradeScale {
  id: number;
  grade: string;
  minPercentage: number;
  maxPercentage: number;
  gradePoint: number;
}

@Injectable({ providedIn: 'root' })
export class ExamService {
  private readonly apollo = inject(Apollo);

  readonly examSchedules = signal<ExamSchedule[]>([]);
  readonly studentMarks = signal<StudentMark[]>([]);
  readonly gradeScales = signal<GradeScale[]>([]);

  loadExamSchedules(semesterId?: number) {
    this.apollo
      .watchQuery<{ examSchedules: ExamSchedule[] }>({
        query: GET_EXAM_SCHEDULES,
        variables: { semesterId },
      })
      .valueChanges.subscribe(({ data }) => this.examSchedules.set((data?.examSchedules ?? []) as ExamSchedule[]));
  }

  loadStudentMarks(studentId: number, semesterId?: number) {
    this.apollo
      .watchQuery<{ studentMarks: StudentMark[] }>({
        query: GET_STUDENT_MARKS,
        variables: { studentId, semesterId },
      })
      .valueChanges.subscribe(({ data }) => this.studentMarks.set((data?.studentMarks ?? []) as StudentMark[]));
  }

  loadGradeScales() {
    this.apollo
      .watchQuery<{ gradeScales: GradeScale[] }>({ query: GET_GRADE_SCALES })
      .valueChanges.subscribe(({ data }) => this.gradeScales.set(data?.gradeScales ?? []));
  }

  recordMark(input: {
    studentId: number;
    examId: number;
    courseId: number;
    semesterId: number;
    internalMarks?: number;
    externalMarks?: number;
    totalMarks: number;
    maxMarks: number;
    grade: string;
    result: string;
  }) {
    return this.apollo.mutate({
      mutation: RECORD_STUDENT_MARK,
      variables: { input },
    });
  }
}
