package com.gb.challenge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "title", unique = false, nullable = false, length = 256)
    private String title;

    @Column(name = "description", unique = false, nullable = false, length = 512)
    private String description;

    @Column(name = "isbn", unique = true, nullable = false)
    private Long isbn;

    @Column(name = "Language", unique = false, nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    private Language language;

}