package com.gb.challenge.builders;

import java.util.Arrays;
import java.util.List;

import com.gb.challenge.dto.book.BookDTO;
import com.gb.challenge.enums.Language;
import com.gb.challenge.utils.RandomUtil;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookDTOBuilder {


    private BookDTO bookDTO;

    public static List<BookDTO> someBooks(int length) {
        List<BookDTO> books = Arrays.asList();
        for (long i = 0; i < length; i++) {
            books.add(oneBook().build());
        }
        return books;
    }

    public static List<BookDTO> someBooks(BookDTO... bookDTO) {
        return Arrays.asList(bookDTO);
    }

    public static BookDTOBuilder oneBook() {
        BookDTOBuilder builder = new BookDTOBuilder();
        builder.bookDTO = new BookDTO();
        builder.bookDTO.setId(RandomUtil.longRand());
        builder.bookDTO.setTitle("BookDTO " + RandomUtil.integerRand());
        builder.bookDTO.setDescription(RandomUtil.lorenIpsumRand(0, 500));
        builder.bookDTO.setLanguage(RandomUtil.language());
        builder.bookDTO.setIsbn(RandomUtil.isbnRand());
        return builder;
    }

    public static BookDTOBuilder oneBook(Long id, String title, String description, Language language, String isbn) {
        BookDTOBuilder builder = new BookDTOBuilder();
        builder.bookDTO = new BookDTO();
        builder.bookDTO.setId(id);
        builder.bookDTO.setTitle(title);
        builder.bookDTO.setDescription(description);
        builder.bookDTO.setLanguage(language);
        builder.bookDTO.setIsbn(isbn);
        return builder;
    }

    public BookDTO build() {
        return bookDTO;
    }

}