package com.gb.challenge.builders;

import com.gb.challenge.model.Book;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookExampleBuilder {

    private static Book book = BookBuilder.oneBook().build();

    public static Example<Book> exempleContaining(){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("id");
        return  Example.of(book, matcher);
        
        
    }

    public static Example<Book> exempleExact(){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT).withIgnorePaths("id");
        return  Example.of(book, matcher);
    }


}