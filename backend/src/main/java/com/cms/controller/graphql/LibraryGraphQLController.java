package com.cms.controller.graphql;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Book;
import com.cms.model.BookIssue;
import com.cms.model.LibraryMember;
import com.cms.repository.BookRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.StudentProfileRepository;
import com.cms.service.BookService;
import com.cms.service.LibraryEnhancedService;
import com.cms.service.LibraryMemberService;
import com.cms.model.BookReservation;
import com.cms.model.DigitalResource;
import com.cms.model.LibraryFine;
import com.cms.repository.LibraryMemberRepository;

@Controller
public class LibraryGraphQLController {

    private final BookService bookService;
    private final LibraryMemberService libraryMemberService;
    private final LibraryEnhancedService libraryEnhancedService;
    private final BookRepository bookRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final FacultyProfileRepository facultyProfileRepository;
    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryGraphQLController(BookService bookService,
                                     LibraryMemberService libraryMemberService,
                                     LibraryEnhancedService libraryEnhancedService,
                                     BookRepository bookRepository,
                                     StudentProfileRepository studentProfileRepository,
                                     FacultyProfileRepository facultyProfileRepository,
                                     LibraryMemberRepository libraryMemberRepository) {
        this.bookService = bookService;
        this.libraryMemberService = libraryMemberService;
        this.libraryEnhancedService = libraryEnhancedService;
        this.bookRepository = bookRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.facultyProfileRepository = facultyProfileRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    // Book queries
    @QueryMapping
    public List<Book> books() {
        return bookService.findAll();
    }

    @QueryMapping
    public Book book(@Argument Long id) {
        return bookService.findById(id);
    }

    @QueryMapping
    public List<Book> booksByCategory(@Argument String category) {
        return bookService.findByCategory(category);
    }

    @QueryMapping
    public List<Book> searchBooks(@Argument String query) {
        return bookService.search(query);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public Book createBook(@Argument Map<String, Object> input) {
        Book book = new Book();
        book.setIsbn((String) input.get("isbn"));
        book.setTitle((String) input.get("title"));
        book.setAuthor((String) input.get("author"));
        book.setPublisher((String) input.get("publisher"));
        book.setEdition((String) input.get("edition"));
        if (input.get("publicationYear") != null) book.setPublicationYear((Integer) input.get("publicationYear"));
        book.setCategory((String) input.get("category"));
        book.setSubCategory((String) input.get("subCategory"));
        book.setLanguage(input.get("language") != null ? (String) input.get("language") : "English");
        if (input.get("pages") != null) book.setPages((Integer) input.get("pages"));
        book.setShelfLocation((String) input.get("shelfLocation"));
        if (input.get("totalCopies") != null) book.setTotalCopies((Integer) input.get("totalCopies"));
        if (input.get("availableCopies") != null) book.setAvailableCopies((Integer) input.get("availableCopies"));
        book.setDescription((String) input.get("description"));
        book.setCoverImageUrl((String) input.get("coverImageUrl"));
        return bookService.create(book);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public Book updateBook(@Argument Long id, @Argument Map<String, Object> input) {
        Book book = bookService.findById(id);
        if (input.containsKey("title")) book.setTitle((String) input.get("title"));
        if (input.containsKey("author")) book.setAuthor((String) input.get("author"));
        if (input.containsKey("publisher")) book.setPublisher((String) input.get("publisher"));
        if (input.containsKey("edition")) book.setEdition((String) input.get("edition"));
        if (input.containsKey("publicationYear")) book.setPublicationYear((Integer) input.get("publicationYear"));
        if (input.containsKey("category")) book.setCategory((String) input.get("category"));
        if (input.containsKey("totalCopies")) book.setTotalCopies((Integer) input.get("totalCopies"));
        if (input.containsKey("availableCopies")) book.setAvailableCopies((Integer) input.get("availableCopies"));
        if (input.containsKey("description")) book.setDescription((String) input.get("description"));
        if (input.containsKey("shelfLocation")) book.setShelfLocation((String) input.get("shelfLocation"));
        return bookService.update(id, book);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteBook(@Argument Long id) {
        bookService.delete(id);
        return true;
    }

    // Library Member queries
    @QueryMapping
    public List<LibraryMember> libraryMembers() {
        return libraryMemberService.findAll();
    }

    @QueryMapping
    public LibraryMember libraryMember(@Argument Long id) {
        return libraryMemberService.findById(id);
    }

    @QueryMapping
    public LibraryMember libraryMemberByMembershipNumber(@Argument String membershipNumber) {
        return libraryMemberService.findByMembershipNumber(membershipNumber);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public LibraryMember createLibraryMember(@Argument Map<String, Object> input) {
        LibraryMember member = new LibraryMember();
        member.setMembershipNumber((String) input.get("membershipNumber"));
        member.setMemberType((String) input.get("memberType"));
        if (input.get("maxBooksAllowed") != null) member.setMaxBooksAllowed((Integer) input.get("maxBooksAllowed"));
        if (input.get("expiryDate") != null) member.setExpiryDate(LocalDate.parse((String) input.get("expiryDate")));
        if (input.get("studentProfileId") != null) {
            Long studentId = Long.valueOf(input.get("studentProfileId").toString());
            member.setStudentProfile(studentProfileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        }
        if (input.get("facultyProfileId") != null) {
            Long facultyId = Long.valueOf(input.get("facultyProfileId").toString());
            member.setFacultyProfile(facultyProfileRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", facultyId)));
        }
        return libraryMemberService.create(member);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public LibraryMember updateLibraryMember(@Argument Long id, @Argument Map<String, Object> input) {
        LibraryMember member = libraryMemberService.findById(id);
        if (input.containsKey("memberType")) member.setMemberType((String) input.get("memberType"));
        if (input.containsKey("maxBooksAllowed")) member.setMaxBooksAllowed((Integer) input.get("maxBooksAllowed"));
        if (input.containsKey("status")) member.setStatus((String) input.get("status"));
        if (input.containsKey("expiryDate")) member.setExpiryDate(LocalDate.parse((String) input.get("expiryDate")));
        return libraryMemberService.update(id, member);
    }

    // Book Issue queries
    @QueryMapping
    public List<BookIssue> bookIssues(@Argument Long memberId) {
        return libraryMemberService.findIssuesByMemberId(memberId);
    }

    @QueryMapping
    public List<BookIssue> bookIssuesByBook(@Argument Long bookId) {
        return libraryMemberService.findIssuesByBookId(bookId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public BookIssue issueBook(@Argument Map<String, Object> input) {
        BookIssue issue = new BookIssue();
        Long bookId = Long.valueOf(input.get("bookId").toString());
        issue.setBook(bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book", bookId)));
        Long memberId = Long.valueOf(input.get("memberId").toString());
        issue.setMember(libraryMemberService.findById(memberId));
        issue.setIssueDate(LocalDate.now());
        issue.setDueDate(LocalDate.parse((String) input.get("dueDate")));
        issue.setIssuedBy((String) input.get("issuedBy"));
        issue.setStatus("ISSUED");
        return libraryMemberService.issueBook(issue);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public BookIssue returnBook(@Argument Long id) {
        return libraryMemberService.returnBook(id);
    }

    // Book Reservation queries
    @QueryMapping
    public List<BookReservation> bookReservations(@Argument Long bookId) {
        return libraryEnhancedService.findAllReservations().stream()
            .filter(r -> bookId == null || r.getBook().getId().equals(bookId)).toList();
    }

    @MutationMapping
    public BookReservation reserveBook(@Argument Map<String, Object> input) {
        BookReservation r = new BookReservation();
        if (input.get("bookId") != null) r.setBook(bookRepository.findById(Long.valueOf(input.get("bookId").toString())).orElse(null));
        if (input.get("memberId") != null) r.setMember(libraryMemberRepository.findById(Long.valueOf(input.get("memberId").toString())).orElse(null));
        r.setReservationDate(LocalDate.now());
        r.setStatus("ACTIVE");
        return libraryEnhancedService.createReservation(r);
    }

    @MutationMapping
    public BookReservation cancelReservation(@Argument Long id) {
        return libraryEnhancedService.cancelReservation(id);
    }

    // Digital Resources
    @QueryMapping
    public List<DigitalResource> digitalResources(@Argument String category) {
        List<DigitalResource> all = libraryEnhancedService.findAllDigitalResources();
        if (category != null) return all.stream().filter(r -> category.equals(r.getType())).toList();
        return all;
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public DigitalResource createDigitalResource(@Argument Map<String, Object> input) {
        DigitalResource r = new DigitalResource();
        r.setTitle((String) input.get("title"));
        r.setType((String) input.get("resourceType"));
        r.setAuthor((String) input.get("author"));
        r.setPublisher((String) input.get("publisher"));
        r.setUrl((String) input.get("url"));
        r.setAccessLevel((String) input.get("accessLevel"));
        r.setDescription((String) input.get("description"));
        r.setStatus("ACTIVE");
        return libraryEnhancedService.createDigitalResource(r);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public DigitalResource updateDigitalResource(@Argument Long id, @Argument Map<String, Object> input) {
        DigitalResource r = libraryEnhancedService.findAllDigitalResources().stream().filter(d -> d.getId().equals(id)).findFirst().orElse(null);
        if (r == null) return null;
        if (input.containsKey("title")) r.setTitle((String) input.get("title"));
        if (input.containsKey("url")) r.setUrl((String) input.get("url"));
        if (input.containsKey("status")) r.setStatus((String) input.get("status"));
        return libraryEnhancedService.updateDigitalResource(r);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteDigitalResource(@Argument Long id) {
        libraryEnhancedService.deleteDigitalResource(id);
        return true;
    }

    // Library Fines
    @QueryMapping
    public List<LibraryFine> libraryFines(@Argument Long memberId, @Argument String status) {
        List<LibraryFine> all = libraryEnhancedService.findAllFines();
        return all.stream()
            .filter(f -> (memberId == null || f.getMember().getId().equals(memberId)) && (status == null || f.getStatus().equals(status)))
            .toList();
    }

    @MutationMapping
    public LibraryFine payLibraryFine(@Argument Long fineId) {
        return libraryEnhancedService.payFine(fineId);
    }
}
