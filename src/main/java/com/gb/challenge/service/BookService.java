package com.gb.challenge.service;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.validation.Valid;

import com.gb.challenge.dto.book.BookCrawlerListDTO;
import com.gb.challenge.dto.book.BookDTO;
import com.gb.challenge.dto.book.BookNoIdDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

public interface BookService {

    public BookDTO create(BookNoIdDTO book);

    public BookDTO read(Long id);

    public BookDTO update(@Valid BookDTO book);

    public void delete(Long id);

    public Page<BookDTO> list(Integer pageNumber, Integer pageSize, Direction direction, String orderBy);

    public Page<BookDTO> search(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction,
            String orderBy);

    public Page<BookDTO> find(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction, String orderBy);

    public BookCrawlerListDTO listKotlinPage() throws MalformedURLException, IOException;

}