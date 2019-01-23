package com.gb.challenge.controller;

import javax.validation.Valid;

import com.gb.challenge.dto.BookDTO;
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

@Api(value = "Book")
@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @ApiOperation(value = "Create new Book")
    @RequestMapping(method = RequestMethod.POST)
    public BookDTO create(@ApiParam(value = "Book", required = true) @RequestBody BookDTO Book) {
        return (service.create(Book));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Book by Id")
    public BookDTO read(@ApiParam(value = "Id", required = true) @PathVariable Long id) {
        return (service.read(id));
    }

    @ApiOperation(value = "Update Book")
    @RequestMapping(method = RequestMethod.PUT)
    public BookDTO update(@ApiParam(value = "Book", required = true) @RequestBody @Valid BookDTO Book) {
        return (service.update(Book));
    }

    @ApiOperation(value = "Delete Book by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "Id", required = true) @PathVariable Long id) {
        service.delete(id);
    }

    @ApiOperation(value = "list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<BookDTO> list(
            @RequestParam(value = "Page Number", defaultValue = "0", required = true) Integer pageNumber,
            @RequestParam(value = "Page Size", defaultValue = "20", required = true) Integer pageSize,
            @RequestParam(value = "Direction", defaultValue = "ASC", required = true) Direction direction,
            @RequestParam(value = "Ordered By", defaultValue = "id", required = true) String orderBy) {
        return (service.list(pageNumber, pageSize, direction, orderBy));
    }

    @ApiOperation(value = "search")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Page<BookDTO> search(@ApiParam(value = "Book", required = true) BookDTO Book,
            @RequestParam(value = "Page Number", defaultValue = "0", required = true) Integer pageNumber,
            @RequestParam(value = "Page Size", defaultValue = "20", required = true) Integer pageSize,
            @RequestParam(value = "Direction", defaultValue = "ASC", required = true) Direction direction,
            @RequestParam(value = "Ordered By", defaultValue = "id", required = true) String orderBy) {
        return (service.search(Book, pageNumber, pageSize, direction, orderBy));
    }

    @ApiOperation(value = "find")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public Page<BookDTO> find(@ApiParam(value = "Book", required = true) BookDTO Book,
            @RequestParam(value = "Page Number", defaultValue = "0", required = true) Integer pageNumber,
            @RequestParam(value = "Page Size", defaultValue = "20", required = true) Integer pageSize,
            @RequestParam(value = "Direction", defaultValue = "ASC", required = true) Direction direction,
            @RequestParam(value = "Ordered By", defaultValue = "id", required = true) String orderBy) {
        return (service.find(Book, pageNumber, pageSize, direction, orderBy));
    }

}