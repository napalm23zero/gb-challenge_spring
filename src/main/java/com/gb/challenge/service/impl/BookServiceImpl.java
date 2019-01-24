package com.gb.challenge.service.impl;

import java.lang.reflect.Type;

import javax.transaction.Transactional;

import com.gb.challenge.dto.BookDTO;
import com.gb.challenge.model.Book;
import com.gb.challenge.repository.BookRepository;
import com.gb.challenge.service.BookService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("bookService")
@Transactional
public class BookServiceImpl extends GenericServiceImpl<Book, Long> implements BookService {

    @Autowired
    private BookRepository repository;
    private ModelMapper mapper = new ModelMapper();
    private Type pageableTypeBookDTO = new TypeToken<Page<BookDTO>>() {
    }.getType();

    public BookServiceImpl() {

    }

    @Override
    public BookDTO create(BookDTO book) {
        Book newBook = mapper.map(book, Book.class);
        newBook.setId(null);
        return mapper.map(repository.save(newBook), BookDTO.class);
    }

    @Override
    public BookDTO read(Long id) {
        return mapper.map(repository.getOne(id), BookDTO.class);
    }

    @Override
    public BookDTO update(BookDTO book) {
        BookDTO theBook = mapper.map(repository.getOne(book.getId()), BookDTO.class);
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);
        mapper.map(book, theBook);
        Book newBook = mapper.map(theBook, Book.class);
        return mapper.map(repository.save(newBook), BookDTO.class);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<BookDTO> list(Integer pageNumber, Integer pageSize, Direction direction, String orderBy) {
        Pageable pagination = PageRequest.of(pageNumber, pageSize, direction, orderBy);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("id");
        Book book = new Book();
        Example<Book> query = Example.of(book, matcher);
        return (mapper.map(repository.findAll(query, pagination), pageableTypeBookDTO));

    }

    @Override
    public Page<BookDTO> search(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction,
            String orderBy) {
        Pageable pagination = PageRequest.of(pageNumber, pageSize, direction, orderBy);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("id");
        Example<Book> query = Example.of(mapper.map(book, Book.class), matcher);
        return (mapper.map(repository.findAll(query, pagination), pageableTypeBookDTO));
    }

    @Override
    public Page<BookDTO> find(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction, String orderBy) {
        Pageable pagination = PageRequest.of(pageNumber, pageSize, direction, orderBy);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT).withIgnorePaths("id");
        Example<Book> query = Example.of(mapper.map(book, Book.class), matcher);
        return (mapper.map(repository.findAll(query, pagination), pageableTypeBookDTO));
    }

}