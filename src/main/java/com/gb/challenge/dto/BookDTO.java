package com.gb.challenge.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.gb.challenge.enums.Language;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    private String title;

    private String description;

    private Long isbn;

    @Enumerated(EnumType.STRING)
    private Language language;

}