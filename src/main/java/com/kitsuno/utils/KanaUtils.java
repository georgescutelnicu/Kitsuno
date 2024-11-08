package com.kitsuno.utils;

import com.kitsuno.entity.Hiragana;
import com.kitsuno.entity.Kana;
import com.kitsuno.entity.Katakana;

import java.util.ArrayList;
import java.util.List;

public class KanaUtils {

    public static <T extends Kana> List<T> formatKana(List<T> kanaList) {
        List<T> formattedKana = new ArrayList<>();

        for (T kana : kanaList) {
            formattedKana.add(kana);
            String romaji = kana.getRomaji();

            if ("yu".equals(romaji) || "ya".equals(romaji)) {

                formattedKana.add(createEmptyKana(kana));

            } else if ("wa".equals(romaji)) {

                for (int i = 0; i < 3; i++) {
                    formattedKana.add(createEmptyKana(kana));
                }
            }
        }
        return formattedKana;
    }

    private static <T extends Kana> T createEmptyKana(T kana) {
        if (kana instanceof Hiragana) {
            return (T) new Hiragana();
        } else {
            return (T) new Katakana();
        }
    }
}
