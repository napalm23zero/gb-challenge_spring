package com.gb.challenge.utils;

import java.util.Random;

import com.gb.challenge.enums.Language;

public class RandomUtil {

    private static final String LORENIPSUNM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce fringilla egestas ipsum, at posuere turpis. In id nisl eget purus eleifend semper. Nunc pellentesque auctor facilisis. Nullam cursus sagittis dictum. Proin nibh lacus, efficitur in cursus vitae, suscipit vel magna. Praesent sit amet erat placerat risus ultricies aliquam. Etiam aliquam tincidunt massa in viverra. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam tempor mauris sed sem porttitor, et tempor felis mattis. Interdum et malesuada fames ac ante ipsum primis in faucibus.";

    public static final Language language() {
        return Language.values()[(new Random().nextInt(Language.values().length))];
    }

    public static final String isbnRand(){
        String isbn = "978";
        for(int i = 0; i<=10; i++){
            isbn = isbn + new Random().nextInt(9 - 0 + 1) + 0;
        }
        return isbn;
    }

    public static final String lorenIpsumRand(int min, int max) {
        return LORENIPSUNM.substring(min, max);
    }

    public static final Integer integerRand() {
        return new Random().nextInt(200 - 1 + 1) + 1;
    }

    public static final Long longRand() {
        return Long.valueOf(new Random().nextInt(200 - 1 + 1) + 1);
    }
}