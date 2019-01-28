package com.gb.challenge.builders;

import com.gb.challenge.utils.RandomUtil;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PageableBuilder {

    public static Pageable build() {
        return PageRequest.of(
            RandomUtil.integerRand(), 
            RandomUtil.integerRand(), 
            Direction.ASC, 
            "id"
        );
    }

}