import { Injectable, inject, signal } from '@angular/core';
import { Apollo } from 'apollo-angular';
import {
  GET_BOOKS,
  GET_LIBRARY_MEMBERS,
  GET_BOOK_ISSUES,
} from '../../../core/graphql/operations';

export interface Book {
  id: number;
  isbn: string;
  title: string;
  author: string;
  publisher: string;
  edition: string;
  publicationYear: number;
  category: string;
  language: string;
  shelfLocation: string;
  totalCopies: number;
  availableCopies: number;
  status: string;
}

export interface LibraryMember {
  id: number;
  membershipNumber: string;
  memberType: string;
  maxBooksAllowed: number;
  expiryDate: string;
  status: string;
}

export interface BookIssue {
  id: number;
  book: { id: number; title: string; isbn: string };
  issueDate: string;
  dueDate: string;
  returnDate: string;
  fineAmount: number;
  status: string;
}

@Injectable({ providedIn: 'root' })
export class LibraryService {
  private readonly apollo = inject(Apollo);

  readonly books = signal<Book[]>([]);
  readonly members = signal<LibraryMember[]>([]);
  readonly bookIssues = signal<BookIssue[]>([]);

  loadBooks() {
    this.apollo
      .watchQuery<{ books: Book[] }>({ query: GET_BOOKS })
      .valueChanges.subscribe(({ data }) => this.books.set((data?.books ?? []) as Book[]));
  }

  loadMembers() {
    this.apollo
      .watchQuery<{ libraryMembers: LibraryMember[] }>({ query: GET_LIBRARY_MEMBERS })
      .valueChanges.subscribe(({ data }) => this.members.set((data?.libraryMembers ?? []) as LibraryMember[]));
  }

  loadBookIssues(memberId: number) {
    this.apollo
      .watchQuery<{ bookIssues: BookIssue[] }>({
        query: GET_BOOK_ISSUES,
        variables: { memberId },
      })
      .valueChanges.subscribe(({ data }) => this.bookIssues.set((data?.bookIssues ?? []) as BookIssue[]));
  }
}
