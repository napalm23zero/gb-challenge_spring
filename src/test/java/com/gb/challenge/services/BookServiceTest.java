package com.gb.challenge.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.gb.challenge.builders.BookBuilder;
import com.gb.challenge.builders.BookDTOBuilder;
import com.gb.challenge.builders.BookExampleBuilder;
import com.gb.challenge.builders.BookNoIdDTOBuilder;
import com.gb.challenge.builders.PageableBuilder;
import com.gb.challenge.dto.book.BookDTO;
import com.gb.challenge.dto.book.BookNoIdDTO;
import com.gb.challenge.model.Book;
import com.gb.challenge.repository.BookRepository;
import com.gb.challenge.service.impl.BookServiceImpl;
import com.gb.challenge.utils.RandomUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {

    /** MODEL MAPPER */
    private final ModelMapper mapper = new ModelMapper();

    Book book = new Book();
    BookDTO bookDTO = new BookDTO();
    BookNoIdDTO bookNoIdDTO = new BookNoIdDTO();
    Pageable pagination = PageableBuilder.build();
    Example<Book> exampleContaining = BookExampleBuilder.exempleContaining();
    Example<Book> exampleExactg = BookExampleBuilder.exempleExact();
    List<Book> books = new ArrayList<>();

    @InjectMocks
    public BookServiceImpl service;

    @Mock
    public BookRepository repository;

    @Before
    public void initialize() {
        MockitoAnnotations.initMocks(this);
        book = BookBuilder.oneBook().build();
        bookDTO = BookDTOBuilder.oneBook().build();
        bookNoIdDTO = BookNoIdDTOBuilder.oneBook().build();
        books = BookBuilder.someBooks(10);
    }

    /** [Repository Comunication] **/
    @Test
    public void shouldCallRepositorySaveMethod() {
        when(repository.save(mapper.map(bookNoIdDTO, Book.class))).thenReturn(mapper.map(bookNoIdDTO, Book.class));
        service.create(bookNoIdDTO);
        verify(repository).save(mapper.map(bookNoIdDTO, Book.class));
    }

    @Test
    public void shouldCallRepositoryGetOneMethod() {
        Long id = RandomUtil.longRand();
        book.setId(id);
        when(repository.getOne(id)).thenReturn(book);
        service.read(id);
        verify(repository).getOne(id);
    }

    @Test
    public void shouldCallRepositoryDeleteByIdMethod() {
        BookServiceImpl bookServiceImpl = mock(BookServiceImpl.class);
        Long id = RandomUtil.longRand();
        book.setId(id);
        doNothing().when(bookServiceImpl).delete(id);
        service.delete(id);
        verify(repository).deleteById(id);
    }

    @Test
    public void shouldCallRepositoryFindAllMethod() {
        when(repository.findAll(pagination)).thenReturn(new PageImpl<>(books));
        service.list(pagination.getPageNumber(), pagination.getPageSize(), Direction.ASC, "id");
        verify(repository).findAll(pagination);
    }
}