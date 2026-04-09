package com.cms.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.LibraryMember;
import com.cms.model.BookIssue;
import com.cms.repository.LibraryMemberRepository;
import com.cms.repository.BookIssueRepository;

@Service
public class LibraryMemberService {

    private final LibraryMemberRepository memberRepository;
    private final BookIssueRepository bookIssueRepository;

    public LibraryMemberService(LibraryMemberRepository memberRepository,
                                BookIssueRepository bookIssueRepository) {
        this.memberRepository = memberRepository;
        this.bookIssueRepository = bookIssueRepository;
    }

    public LibraryMember findById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("LibraryMember", id));
    }

    public LibraryMember create(LibraryMember member) {
        return memberRepository.save(member);
    }

    public List<BookIssue> findIssuesByMemberId(Long memberId) {
        return bookIssueRepository.findByMemberId(memberId);
    }

    public List<BookIssue> findActiveIssuesByMemberId(Long memberId) {
        return bookIssueRepository.findByMemberIdAndStatus(memberId, "ISSUED");
    }

    public BookIssue issueBook(BookIssue bookIssue) {
        return bookIssueRepository.save(bookIssue);
    }

    public BookIssue returnBook(Long issueId) {
        BookIssue bookIssue = bookIssueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("BookIssue", issueId));
        bookIssue.setStatus("RETURNED");
        bookIssue.setReturnDate(LocalDate.now());
        return bookIssueRepository.save(bookIssue);
    }

    public List<LibraryMember> findAll() {
        return memberRepository.findAll();
    }

    public LibraryMember findByMembershipNumber(String membershipNumber) {
        return memberRepository.findByMembershipNumber(membershipNumber)
            .orElseThrow(() -> new ResourceNotFoundException("LibraryMember with membership " + membershipNumber + " not found"));
    }

    public LibraryMember update(Long id, LibraryMember data) {
        LibraryMember member = findById(id);
        if (data.getMemberType() != null) member.setMemberType(data.getMemberType());
        if (data.getMaxBooksAllowed() != null) member.setMaxBooksAllowed(data.getMaxBooksAllowed());
        if (data.getStatus() != null) member.setStatus(data.getStatus());
        if (data.getExpiryDate() != null) member.setExpiryDate(data.getExpiryDate());
        return memberRepository.save(member);
    }

    public List<BookIssue> findIssuesByBookId(Long bookId) {
        return bookIssueRepository.findByBookId(bookId);
    }
}
