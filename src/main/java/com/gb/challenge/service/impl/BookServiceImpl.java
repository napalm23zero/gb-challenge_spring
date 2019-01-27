package com.gb.challenge.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

import javax.transaction.Transactional;

import com.gb.challenge.crawler.AmazonDECrawler;
import com.gb.challenge.crawler.AmazonUSCrawler;
import com.gb.challenge.crawler.DumbCrawler;
import com.gb.challenge.crawler.FundamentalKotlinCrawler;
import com.gb.challenge.crawler.KuramKitapCrawler;
import com.gb.challenge.crawler.ManningCrawler;
import com.gb.challenge.crawler.PacktPubCrawler;
import com.gb.challenge.crawler.RayWenderlichCrawler;
import com.gb.challenge.dto.BookDTO;
import com.gb.challenge.model.Book;
import com.gb.challenge.repository.BookRepository;
import com.gb.challenge.service.BookService;
import com.gb.challenge.utils.RegexUtils;

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
    private RegexUtils regexUtils = new RegexUtils();
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
    public List<BookDTO> listKotlinPage() throws MalformedURLException, IOException {
        BookDTO temBook = new BookDTO();
        List<BookDTO> listBook = new ArrayList<>();
        URL kotlinURL = new URL("https://kotlinlang.org/docs/books.html");
        BufferedReader kotlinBuffer = new BufferedReader(new InputStreamReader(kotlinURL.openStream()));
        String desc = "";
        String inputLine;

        while ((inputLine = kotlinBuffer.readLine()) != null) {
            if (inputLine.contains("<h2")) {
                if (checkBook(temBook) == true) { // Reset TempObject
                    listBook.add(temBook);
                    temBook = new BookDTO();
                    desc = "";
                }
                temBook.setTitle(getTitleFromImputLine(inputLine));
            }
            if (temBook.getLanguage() != null && !inputLine.contains("img")
                    && !inputLine.contains("book-cover-image")) {
                desc = desc + inputLine.replace("<p>", "").replace("</p>", "").trim().replaceAll("<[^>]*>", "");
                temBook.setDescription(desc + "");
                if (inputLine.contains("<a")) {
                    Matcher matcher = regexUtils.createPatternMatcher(RegexUtils.hrefRegex, inputLine);
                    if (matcher.find() && temBook.getIsbn() == null) {
                        temBook.setIsbn(getIsbnFromInnerSite(matcher.group(1)));
                    }
                }
            }
            if (inputLine.contains("book-lang")) {
                temBook.setStringLanguage(getLanguageFromImputLine(inputLine));
            }
        }
        kotlinBuffer.close();

        return listBook;

    }

    private String getIsbnFromInnerSite(String site) {
        Matcher matcher = regexUtils.createPatternMatcher(RegexUtils.siteHomeRegex, site);
        if (matcher.find()) {
            switch (matcher.group()) {
            case "https://manning.com":
            case "https://www.manning.com":
                ManningCrawler manningCrawler = new ManningCrawler();
                return manningCrawler.getIsbn(site);
            case "https://www.packtpub.com":
                PacktPubCrawler packtPubCrawler = new PacktPubCrawler();
                return packtPubCrawler.getIsbn(site);
            case "http://www.fundamental-kotlin.co":
                FundamentalKotlinCrawler fundamentalKotlinCrawler = new FundamentalKotlinCrawler();
                return fundamentalKotlinCrawler.getIsbn(site);
            case "https://www.kuramkitap.com":
                KuramKitapCrawler kuramKitapCrawler = new KuramKitapCrawler();
                return kuramKitapCrawler.getIsbn(site);
            case "https://store.raywenderlich.com":
                RayWenderlichCrawler rayWenderlichCrawler = new RayWenderlichCrawler();
                return rayWenderlichCrawler.getIsbn(site);
            case "https://www.amazon.com":
                AmazonUSCrawler amazonUSCrawler = new AmazonUSCrawler();
                return amazonUSCrawler.getIsbn(site);
            case "https://www.amazon.de":
                AmazonDECrawler amazonDECrawler = new AmazonDECrawler();
                return amazonDECrawler.getIsbn(site);
            default:
                DumbCrawler dumbCrawler = new DumbCrawler();
                return dumbCrawler.getIsbn(site);
            }
        }
        return "Unavailable";
    }

    public Boolean checkBook(BookDTO book) {
        if (book.getDescription() != null && book.getIsbn() != null && book.getLanguage() != null
                && book.getTitle() != null) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkSite(URL url) {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String getLanguageFromImputLine(String inputLine) {
        return inputLine.replace("<div class=\"book-lang\">", "").replace("</div>", "").trim()
                .toUpperCase(Locale.ENGLISH);
    }

    public String getTitleFromImputLine(String inputLine) {
        if (inputLine.contains("<h2 style=\"clear: left\">")) {
            return inputLine.replace("<h2 style=\"clear: left\">", "").replace("</h2>", "").trim();
        } else {
            return inputLine.replace("<h2>", "").replace("</h2>", "").trim();
        }

    }

}