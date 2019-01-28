package com.gb.challenge.builders;

import java.util.Arrays;
import java.util.List;

import com.gb.challenge.enums.Language;
import com.gb.challenge.model.Book;
import com.gb.challenge.utils.RandomUtil;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookBuilder {

    private Book book;

    public static List<Book> someBooks(int length) {
        List<Book> books = Arrays.asList();
        for (long i = 0; i < length; i++) {
            books.add(oneBook(
                    i, 
                    "Book " + RandomUtil.integerRand(), 
                    RandomUtil.lorenIpsumRand(0, 500), 
                    RandomUtil.language(),
                    RandomUtil.isbnRand()
                ).build());
        }
        return books;
    }

    public static List<Book> someBooks(Book... produtos) {
        return Arrays.asList(produtos);
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

    private Book build() {
        return book;
    }

}