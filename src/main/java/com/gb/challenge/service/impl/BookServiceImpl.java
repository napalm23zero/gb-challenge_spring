package com.gb.challenge.service.impl;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.gb.challenge.dto.BookDTO;
import com.gb.challenge.model.Book;
import com.gb.challenge.repository.BookRepository;
import com.gb.challenge.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("bookService")
@Transactional
public class BookServiceImpl extends GenericServiceImpl<Book, Long> implements BookService {

    @Autowired
    private BookRepository repository;

    @Override
    public BookDTO create(BookDTO book) {
        return null;
    }

    @Override
    public BookDTO read(Long id) {
        return null;
    }

    @Override
    public BookDTO update(@Valid BookDTO book) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<BookDTO> list(Integer pageNumber, Integer pageSize, Direction direction, String orderBy) {
        return null;
    }

    @Override
    public Page<BookDTO> search(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction,
            String orderBy) {
        return null;
    }

    @Override
    public Page<BookDTO> find(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction, String orderBy) {
        return null;
    }

}