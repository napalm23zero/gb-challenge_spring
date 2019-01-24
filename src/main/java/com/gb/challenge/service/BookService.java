package com.gb.challenge.service;

import java.util.List;

import javax.validation.Valid;

import com.gb.challenge.dto.BookDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

public interface BookService {

    public BookDTO create(BookDTO book);

    public BookDTO read(Long id);

    public BookDTO update(@Valid BookDTO book);

    public void delete(Long id);

    public Page<BookDTO> list(Integer pageNumber, Integer pageSize, Direction direction, String orderBy);

    public Page<BookDTO> search(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction,
            String orderBy);

    public Page<BookDTO> find(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction, String orderBy);

	public List<BookDTO> listKotlinPage();

}