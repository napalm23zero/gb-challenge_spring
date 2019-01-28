package com.gb.challenge.builders;

import java.util.ArrayList;
import java.util.List;

import com.gb.challenge.enums.Language;
import com.gb.challenge.model.Book;
import com.gb.challenge.utils.RandomUtil;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookBuilder {


    private Book book;

    public static List<Book> someBooks(int length) {
        List<Book> books = new ArrayList<>();
        for (long i = 0; i < length; i++) {
            books.add(oneBook().build());
        }
        return books;
    }

    public static BookBuilder oneBook() {
        BookBuilder builder = new BookBuilder();
        builder.book = new Book();
        builder.book.setId(RandomUtil.longRand());
        builder.book.setTitle("Book " + RandomUtil.integerRand());
        builder.book.setDescription(RandomUtil.lorenIpsumRand(0, 500));
        builder.book.setLanguage(RandomUtil.language());
        builder.book.setIsbn(RandomUtil.isbnRand());
        return builder;
    }

    public static BookBuilder oneBook(Long id, String title, String description, Language language, String isbn) {
        BookBuilder builder = new BookBuilder();
        builder.book = new Book();
        builder.book.setId(id);
        builder.book.setTitle(title);
        builder.book.setDescription(description);
        builder.book.setLanguage(language);
        builder.book.setIsbn(isbn);
        return builder;
    }

    public Book build() {
        return book;
    }

}