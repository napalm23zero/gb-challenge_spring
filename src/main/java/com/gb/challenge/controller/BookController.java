package com.gb.challenge.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.validation.Valid;

import com.gb.challenge.dto.book.BookCrawlerListDTO;
import com.gb.challenge.dto.book.BookDTO;
import com.gb.challenge.dto.book.BookNoIdDTO;
import com.gb.challenge.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Simples CRUD operarions
 * Advanced Search and List
 * WebCrawling implementation for KotlingLang Book Site
 * 
 * @author Rodrigo Dantas - rodrigodantas.91@gmail.com
 * @since 2019.01.26
 * 
 */
@Api(value = "Book")
@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }
    
    /**
     * Create a new single book on Database
     * 
     * @param book - A Single Book Object with basic info of a Book
     * @return A Single Book Object that was just created
     * 
     */
    @ApiOperation(value = "Create new Book")
    @RequestMapping(method = RequestMethod.POST)
    public BookDTO create(@ApiParam(value = "Book", required = true) @RequestBody BookNoIdDTO Book) {
        return (service.create(Book));
    }

    /**
     * Get single book from Database
     * 
     * @param id - Unique Identifier of a Book
     * @return A Single Book Object that was requested by id
     * 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Book by Id")
    public BookDTO read(@ApiParam(value = "Id", required = true) @PathVariable Long id) {
        return (service.read(id));
    }

    /**
     * Updates a single book on Database
     * 
     * @param book - A Single Book Object the id of existing Object and new data
     *             tobe applied
     * @return A Single Book Object that was just updated
     * 
     */
    @ApiOperation(value = "Update Book")
    @RequestMapping(method = RequestMethod.PUT)
    public BookDTO update(@ApiParam(value = "Book", required = true) @RequestBody @Valid BookDTO Book) {
        return (service.update(Book));
    }

    /**
     * Delete a single book from Database
     * 
     * @param id - Unique Identifier of a Book
     * 
     */
    @ApiOperation(value = "Delete Book by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "Id", required = true) @PathVariable Long id) {
        service.delete(id);
    }

    /**
     * List all Books from Database
     * 
     * @param pageNumber - Page that will be shown on Response
     * @param pageSize   - Number of items to display on requested Page
     * @param direction  - Order of items ASC / DESC
     * @param orderBy    - Attribute that will be used as Order Index
     * @return A pageable element with a Books Collection
     * 
     */
    @ApiOperation(value = "list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<BookDTO> list(
            @RequestParam(value = "Page Number", defaultValue = "0", required = true) Integer pageNumber,
            @RequestParam(value = "Page Size", defaultValue = "20", required = true) Integer pageSize,
            @RequestParam(value = "Direction", defaultValue = "ASC", required = true) Direction direction,
            @RequestParam(value = "Ordered By", defaultValue = "id", required = true) String orderBy) {
        return (service.list(pageNumber, pageSize, direction, orderBy));
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
     * 
     */
    @ApiOperation(value = "search")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Page<BookDTO> search(@ApiParam(value = "Book", required = true) BookDTO Book,
            @RequestParam(value = "Page Number", defaultValue = "0", required = true) Integer pageNumber,
            @RequestParam(value = "Page Size", defaultValue = "20", required = true) Integer pageSize,
            @RequestParam(value = "Direction", defaultValue = "ASC", required = true) Direction direction,
            @RequestParam(value = "Ordered By", defaultValue = "id", required = true) String orderBy) {
        return (service.search(Book, pageNumber, pageSize, direction, orderBy));
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
     * 
     */
    @ApiOperation(value = "find")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public Page<BookDTO> find(@ApiParam(value = "Book", required = true) BookDTO Book,
            @RequestParam(value = "Page Number", defaultValue = "0", required = true) Integer pageNumber,
            @RequestParam(value = "Page Size", defaultValue = "20", required = true) Integer pageSize,
            @RequestParam(value = "Direction", defaultValue = "ASC", required = true) Direction direction,
            @RequestParam(value = "Ordered By", defaultValue = "id", required = true) String orderBy) {
        return (service.find(Book, pageNumber, pageSize, direction, orderBy));
    }

    /**
     * List all Books from page, directly from "Kotlinlang Books Site" using
     * "WebCrawling" techinique, this method runs through all page DOM comparing and
     * collecting data to build requested objects. This method also saves the founding items
     * on Database.
     * 
     * @return An Object containing a count of found items at the page and the books
     *         info collected.
     * @link https://kotlinlang.org/docs/books.html
     */
    @ApiOperation(value = "list kotlin")
    @RequestMapping(value = "/list/kotlin", method = RequestMethod.GET)
    public BookCrawlerListDTO listKotlinPage() throws MalformedURLException, IOException {
        return (service.listKotlinPage());
    }

}