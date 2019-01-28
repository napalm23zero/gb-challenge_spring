
package com.gb.challenge.dto.book;

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
public class BookNoIdDTO {

    private String title;

    private String description;

    private String isbn;

    @Enumerated(EnumType.STRING)
    private Language language;

    public void setStringLanguage(String language) {
        for (Language l : Language.values()) {
            if (l.toString().equals(language)) {
                setLanguage(l);
            }
        }
    }

}