package com.gb.challenge.service;

import javax.validation.Valid;

import com.gb.challenge.dto.BookDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

public class BookService {

    public BookDTO create(BookDTO book) {
        return null;
    }

    public BookDTO read( Long id) {
        return null;
    }

    public BookDTO update(@Valid BookDTO book) {
        return null;
    }

    public void delete(Long id) {
    }

    public Page<BookDTO> list(Integer pageNumber, Integer pageSize, Direction direction, String orderBy) {
        return null;
    }

    public Page<BookDTO> search(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction,
            String orderBy) {
        return null;
    }

    public Page<BookDTO> find(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction, String orderBy) {
        return null;
    }

}