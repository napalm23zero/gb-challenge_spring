package com.gb.challenge.builders;

import java.util.Arrays;
import java.util.List;

import com.gb.challenge.enums.Language;
import com.gb.challenge.dto.book.BookNoIdDTO;
import com.gb.challenge.utils.RandomUtil;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookNoIdDTOBuilder {

    private BookNoIdDTO bookNoIdDTO;

    public static List<BookNoIdDTO> someBooks(int length) {
        List<BookNoIdDTO> books = Arrays.asList();
        for (long i = 0; i < length; i++) {
            books.add(oneBook().build());
        }
        return books;
    }

    public static List<BookNoIdDTO> someBooks(BookNoIdDTO... bookNoIdDTO) {
        return Arrays.asList(bookNoIdDTO);
    }

    public static BookNoIdDTOBuilder oneBook() {
        BookNoIdDTOBuilder builder = new BookNoIdDTOBuilder();
        builder.bookNoIdDTO = new BookNoIdDTO();
        builder.bookNoIdDTO.setTitle("BookNoIdDTO " + RandomUtil.integerRand());
        builder.bookNoIdDTO.setDescription(RandomUtil.lorenIpsumRand(0, 500));
        builder.bookNoIdDTO.setLanguage(RandomUtil.language());
        builder.bookNoIdDTO.setIsbn( RandomUtil.isbnRand());
        return builder;
    }


    public static BookNoIdDTOBuilder oneBook(String title, String description, Language language, String isbn) {
        BookNoIdDTOBuilder builder = new BookNoIdDTOBuilder();
        builder.bookNoIdDTO = new BookNoIdDTO();
        builder.bookNoIdDTO.setTitle(title);
        builder.bookNoIdDTO.setDescription(description);
        builder.bookNoIdDTO.setLanguage(language);
        builder.bookNoIdDTO.setIsbn(isbn);
        return builder;
    }

    public BookNoIdDTO build() {
        return bookNoIdDTO;
    }

}