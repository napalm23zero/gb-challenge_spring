package com.gb.challenge.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public List<BookDTO> listKotlinPage() {
        BookDTO book = new BookDTO();
        List<BookDTO> listBook = new ArrayList<>();
        try {
            URL kotlin = new URL("https://kotlinlang.org/docs/books.html");
            BufferedReader in = new BufferedReader(new InputStreamReader(kotlin.openStream()));
            String desc = "";
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("<h2")) {
                    if (checkBook(book) == true) {
                        listBook.add(book);
                        book = new BookDTO();
                        desc = "";
                    }
                    if (inputLine.contains("<h2 style=\"clear: left\">")) {
                        book.setTitle(inputLine.replace("<h2 style=\"clear: left\">", "").replace("</h2>", "").trim());
                    } else {
                        book.setTitle(inputLine.replace("<h2>", "").replace("</h2>", "").trim());
                    }
                }
                if (book.getLanguage() != null && !inputLine.contains("img")
                        && !inputLine.contains("book-cover-image")) {
                    desc = desc + inputLine.replace("<p>", "").replace("</p>", "").trim().replaceAll("<[^>]*>", "");
                    book.setDescription(desc + "");
                    if (inputLine.contains("<a")) {
                        Pattern pattern = Pattern.compile("href=['\"]([^'\"]+?)['\"]");
                        Matcher matcher = pattern.matcher(inputLine);
                        if (matcher.find()) {
                            URL isbnExplorer = new URL(matcher.group().replace("href=", "").replace("\"", ""));
                            HttpURLConnection connection = (HttpURLConnection) isbnExplorer.openConnection();
                            connection.setRequestMethod("GET");
                            connection.connect();
                            if (connection.getResponseCode() == 200) {
                                BufferedReader isbnIn = new BufferedReader(
                                        new InputStreamReader(isbnExplorer.openStream()));
                                String isbnInputLine;
                                Boolean isbnFound = false;
                                while ((isbnInputLine = isbnIn.readLine()) != null && book.getIsbn() == null) {
                                    if (isbnInputLine.toLowerCase().contains("isbn")
                                            || isbnInputLine.toLowerCase().contains("isbn-13")
                                            || isbnInputLine.toLowerCase().contains("isbn-10")) {
                                        isbnFound = true;
                                    }
                                    if (isbnFound) {
                                        Pattern patternIsbn = Pattern.compile("978[0-9]{10,13}");
                                        Matcher matcherIsbn = patternIsbn.matcher(isbnInputLine);
                                        if (matcherIsbn.find()) {
                                            book.setIsbn(matcherIsbn.group().replace("-", ""));
                                            break;
                                        }
                                    }
                                }

                                if (book.getIsbn() == null) {
                                    book.setIsbn("Unavailable");
                                }
                                isbnIn.close();
                            }
                        }
                    }

                }
                if (inputLine.contains("book-lang")) {
                    book.setStringLanguage(inputLine.replace("<div class=\"book-lang\">", "").replace("</div>", "")
                            .trim().toUpperCase(Locale.ENGLISH));
                }

            }
            in.close();
        } catch (

        IOException e) {
            e.printStackTrace();
        }

        return listBook;

    }

    public Boolean checkBook(BookDTO book) {
        if (book.getDescription() != null && book.getIsbn() != null && book.getLanguage() != null
                && book.getTitle() != null) {
            return true;
        } else {
            return false;
        }

    }

}