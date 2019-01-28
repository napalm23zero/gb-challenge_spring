package com.gb.challenge.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import javax.transaction.Transactional;

import com.gb.challenge.crawler.*;
import com.gb.challenge.dto.book.*;
import com.gb.challenge.model.Book;
import com.gb.challenge.repository.BookRepository;
import com.gb.challenge.service.BookService;
import com.gb.challenge.utils.RegexUtil;
import com.gb.challenge.utils.TextCleanupUtil;

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

/**
 * Simples CRUD operarions
 * Advanced Search and List
 * WebCrawling implementation for KotlingLang Book Site
 * 
 * @author Rodrigo Dantas - rodrigodantas.91@gmail.com
 * @since 2019.01.26
 * 
 */
@Service("bookService")
@Transactional
public class BookServiceImpl extends GenericServiceImpl<Book, Long> implements BookService {

    @Autowired
    private BookRepository repository;

    /** MODEL MAPPER */
    private final ModelMapper mapper = new ModelMapper();
    private final Type pageableTypeBookDTO = new TypeToken<Page<BookDTO>>() {}.getType(); // getPage Type for BookDTO
    private final Type listableTypeBookDTO = new TypeToken<List<BookDTO>>() {}.getType(); // Get List Type for BookDTO
    private final Type listableTypeBook = new TypeToken<List<Book>>() {}.getType();       // Get List Type for Book

    /**
     * Create a new single book on Database
     * 
     * @param book - A Single Book Object with basic info of a Book
     * @return A Single Book Object that was just created
     * @see BookRepository - Persistence Interface
     * 
     */
    @Override
    public BookDTO create(BookNoIdDTO book) {
        Book newBook = mapper.map(book, Book.class);
        return mapper.map(repository.save(newBook), BookDTO.class);
    }

    /**
     * Get single book from Database
     * 
     * @param id - Unique Identifier of a Book
     * @return A Single Book Object that was requested by id
     * @see BookRepository - Persistence Interface
     * 
     */
    @Override
    public BookDTO read(Long id) {
        return mapper.map(repository.getOne(id), BookDTO.class);
    }

    /**
     * Updates a single book on Database
     * 
     * @param book - A Single Book Object the id of existing Object and new data
     *             tobe applied
     * @return A Single Book Object that was just updated
     * @see BookRepository - Persistence Interface
     * 
     */
    @Override
    public BookDTO update(BookDTO book) {
        BookDTO theBook = mapper.map(repository.getOne(book.getId()), BookDTO.class);
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);
        mapper.map(book, theBook);
        Book newBook = mapper.map(theBook, Book.class);
        return mapper.map(repository.save(newBook), BookDTO.class);
    }

    /**
     * Delete a single book from Database
     * 
     * @param id - Unique Identifier of a Book
     * @see BookRepository - Persistence Interface
     * 
     */
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * List all Books from Database
     * 
     * @param pageNumber - Page that will be shown on Response
     * @param pageSize   - Number of items to display on requested Page
     * @param direction  - Order of items ASC / DESC
     * @param orderBy    - Attribute that will be used as Order Index
     * @return A pageable element with a Books Collection
     * @see BookRepository - Persistence Interface
     * 
     */
    @Override
    public Page<BookDTO> list(Integer pageNumber, Integer pageSize, Direction direction, String orderBy) {
        Pageable pagination = PageRequest.of(pageNumber, pageSize, direction, orderBy);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("id");
        Book book = new Book();
        Example<Book> query = Example.of(book, matcher);
        return (mapper.map(repository.findAll(query, pagination), pageableTypeBookDTO));

    }

    /**
     * Search all Books from Database matching with <b>CONTAINING</b> filters
     * 
     * @param book       - Object filled with attributes to be used as filters
     * @param pageNumber - Page that will be shown on Response
     * @param pageSize   - Number of items to display on requested Page
     * @param direction  - Order of items ASC / DESC
     * @param orderBy    - Attribute that will be used as Order Index
     * @return A pageable element with a Books Collection that matched with given
     *         Exemple
     * @see BookRepository - Persistence Interface
     * 
     */
    @Override
    public Page<BookDTO> search(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction,
            String orderBy) {
        Pageable pagination = PageRequest.of(pageNumber, pageSize, direction, orderBy);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("id");
        Example<Book> query = Example.of(mapper.map(book, Book.class), matcher);
        return (mapper.map(repository.findAll(query, pagination), pageableTypeBookDTO));
    }

    /**
     * Search all Books from Database matching with <b>EXACT</b> filters
     * 
     * @param book       - Object filled with attributes to be used as filters
     * @param pageNumber - Page that will be shown on Response
     * @param pageSize   - Number of items to display on requested Page
     * @param direction  - Order of items ASC / DESC
     * @param orderBy    - Attribute that will be used as Order Index
     * @return A pageable element with a Books Collection that matched with given
     *         Exemple
     * @see BookRepository - Persistence Interface
     * 
     */
    @Override
    public Page<BookDTO> find(BookDTO book, Integer pageNumber, Integer pageSize, Direction direction, String orderBy) {
        Pageable pagination = PageRequest.of(pageNumber, pageSize, direction, orderBy);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT).withIgnorePaths("id");
        Example<Book> query = Example.of(mapper.map(book, Book.class), matcher);
        return (mapper.map(repository.findAll(query, pagination), pageableTypeBookDTO));
    }

    /**
     * List all Books from page, directly from "Kotlinlang Books Site" using
     * "WebCrawling" techinique, this method runs through all page DOM comparing and
     * collecting data to build requested objects. This method save the found items
     * on Database too.
     * 
     * @return An Object containing a count of found items at the page and the books
     *         info collected.
     * @see BookRepository - Persistence Interface
     * @link https://kotlinlang.org/docs/books.html
     * @inheritDoc https://en.wikipedia.org/wiki/Web_crawler
     */
    @Override
    public BookCrawlerListDTO listKotlinPage() throws MalformedURLException, IOException {
        BookDTO book = new BookDTO();
        List<BookDTO> listBook = new ArrayList<>();
        URL kotlinURL = new URL("https://kotlinlang.org/docs/books.html");
        String desc = "";
        String inputLine;
        Integer bookCount = 0;

        BufferedReader kotlinBuffer = new BufferedReader(new InputStreamReader(kotlinURL.openStream()));

        while ((inputLine = kotlinBuffer.readLine()) != null) {
            if (inputLine.contains("<h2")) {
                if (bookIsFilled(book)) {
                    listBook.add(book);
                    book = new BookDTO();
                    desc = "";
                    bookCount++;
                }
                book.setTitle(getTitleFromImputLine(inputLine));
            }
            if (book.getLanguage() != null && !inputLine.contains("img") && !inputLine.contains("book-cover-image")) {
                desc = desc + TextCleanupUtil.paragraph(inputLine);
                book.setDescription(desc + "");
                if (inputLine.contains("<a")) {
                    Matcher matcher = RegexUtil.createPatternMatcher(RegexUtil.HREF, inputLine);
                    if (matcher.find() && book.getIsbn() == null) {
                        book.setIsbn(getIsbnFromInnerSite(matcher.group(1)));
                    }
                }
            }
            if (inputLine.contains("book-lang")) {
                book.setStringLanguage(getLanguageFromImputLine(inputLine));
            }
        }
        kotlinBuffer.close();
        List<Book> newBooks = repository.saveAll(mapper.map(listBook, listableTypeBook));
        BookCrawlerListDTO result = new BookCrawlerListDTO();
        result.setBooks(mapper.map(newBooks, listableTypeBookDTO));
        result.setNumberBooks(bookCount);
        return result;
    }

    /**
     * [HELPER] According the given URL, this method directs WebCrwaling Logic to
     * specific knowing Site Patterns, or, by default, uses a more costly but
     * generic mechanism to find ISBN number.
     * 
     * @param site - Given UEL that, may contain the ISBN number
     * @return ISBN number, or, if not found returns "Unavailable"
     * @see Crawler
     * @inheritDoc https://en.wikipedia.org/wiki/International_Standard_Book_Number
     * 
     */
    private String getIsbnFromInnerSite(String site) {
        Matcher matcher = RegexUtil.createPatternMatcher(RegexUtil.SITE, site);
        if (matcher.find()) {
            switch (matcher.group()) {
            case "https://manning.com":
            case "https://www.manning.com":
                return new ManningCrawler().getIsbn(site);
            case "https://www.packtpub.com":
                return new PacktPubCrawler().getIsbn(site);
            case "http://www.fundamental-kotlin.com":
                return new FundamentalKotlinCrawler().getIsbn(site);
            case "https://www.kuramkitap.com":
                return new KuramKitapCrawler().getIsbn(site);
            case "https://leanpub.com/":
                return new LeanPubCrawler().getIsbn(site);
            case "https://www.editions-eni.fr":
                return new EditionsEniCrawler().getIsbn(site);
            case "https://www.raywenderlich.com":
            case "https://store.raywenderlich.com":
                return new RayWenderlichCrawler().getIsbn(site);
            case "https://www.amazon.com":
                return new AmazonUSCrawler().getIsbn(site);
            case "https://www.amazon.de":
                return new AmazonDECrawler().getIsbn(site);
            default:
                return new DumbCrawler().getIsbn(site);
            }
        }
        return "Unavailable";
    }

    /**
     * Clean and Prepare given HTML element and turns it into Plain Text related
     * Book Language
     * 
     * @param inputLine - Given HTML element
     * @return Book Language Initials
     * 
     */
    public String getLanguageFromImputLine(String inputLine) {
        return TextCleanupUtil.divLang(inputLine);
    }

    /**
     * Clean and Prepare given HTML element and turns it into Plain Text related
     * Book Ttitle. This method s prepared more then one identified tittle type.
     * 
     * @param inputLine - Given HTML element
     * @return Book Title
     * 
     */
    public String getTitleFromImputLine(String inputLine) {
        if (inputLine.contains("<h2 style=\"clear: left\">")) {
            return TextCleanupUtil.h2FirstTittle(inputLine);
        } else {
            return TextCleanupUtil.h2SimpleTittle(inputLine);
        }
    }

    /**
     * This method checks if a given Book is completely filled, or has missing
     * attributes.
     * 
     * @param book - Given Book Object
     * @return Bollean informing if book is Filled or Not
     */
    public Boolean bookIsFilled(BookDTO book) {
        if (book.getDescription() != null && book.getIsbn() != null && book.getLanguage() != null
                && book.getTitle() != null) {
            return true;
        } else {
            return false;
        }
    }
}