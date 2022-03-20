package com.reflectoring.beginnersguide.services;

import com.reflectoring.beginnersguide.controllers.dto.BookRequest;
import com.reflectoring.beginnersguide.controllers.dto.BookResponse;
import com.reflectoring.beginnersguide.domain.Book;
import com.reflectoring.beginnersguide.domain.User;
import com.reflectoring.beginnersguide.repository.BookRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UpdateBookService {
    private final GetUserService getUserService;
    private final BookRepository bookRepository;

    public UpdateBookService(
            GetUserService getUserService,
            BookRepository bookRepository) {
        this.getUserService = getUserService;
        this.bookRepository = bookRepository;
    }

    public BookResponse updateBook(long id, BookRequest request){
        return null;
    }

    public void borrow(long bookId, long userId){
        User user = getUserService.getById(userId);
        if(user.getBorrowedBooks().stream().anyMatch(book -> book.getId()== bookId)){
            throw new IllegalStateException("User already borrowed " +
                    "the book");
        }
        Book book =
                bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException());

        if(book.getNumberOfInstances()-1 < 0){
            throw new IllegalStateException("There are no available" +
                    " books!");
        }
        book.getUsers().add(user);
        book.numberOfInstances(book.getNumberOfInstances()+1);
        bookRepository.save(book);
    }

}