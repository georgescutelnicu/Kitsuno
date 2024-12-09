package com.kitsuno.utils;

import com.kitsuno.entity.Hiragana;
import com.kitsuno.entity.Kana;
import com.kitsuno.entity.Katakana;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<String, List<Map<String, String>>> getHiraganaVariants() {
        Map<String, List<Map<String, String>>> hiraganaVariantsMap = new LinkedHashMap<>();

        List<Map<String, String>> dakutenList = new ArrayList<>();
        addVariants(dakutenList, "が", "ga");
        addVariants(dakutenList, "ぎ", "gi");
        addVariants(dakutenList, "ぐ", "gu");
        addVariants(dakutenList, "げ", "ge");
        addVariants(dakutenList, "ご", "go");
        addVariants(dakutenList, "ざ", "za");
        addVariants(dakutenList, "じ", "ji");
        addVariants(dakutenList, "ず", "zu");
        addVariants(dakutenList, "ぜ", "ze");
        addVariants(dakutenList, "ぞ", "zo");
        addVariants(dakutenList, "だ", "da");
        addVariants(dakutenList, "ぢ", "ji");
        addVariants(dakutenList, "づ", "zu");
        addVariants(dakutenList, "で", "de");
        addVariants(dakutenList, "ど", "do");
        addVariants(dakutenList, "ば", "ba");
        addVariants(dakutenList, "び", "bi");
        addVariants(dakutenList, "ぶ", "bu");
        addVariants(dakutenList, "べ", "be");
        addVariants(dakutenList, "ぼ", "bo");
        addVariants(dakutenList, "ぱ", "pa");
        addVariants(dakutenList, "ぴ", "pi");
        addVariants(dakutenList, "ぷ", "pu");
        addVariants(dakutenList, "ぺ", "pe");
        addVariants(dakutenList, "ぽ", "po");

        List<Map<String, String>> yoonList = new ArrayList<>();
        addVariants(yoonList, "きゃ", "kya");
        addVariants(yoonList, "きゅ", "kyu");
        addVariants(yoonList, "きょ", "kyo");
        addVariants(yoonList, "しゃ", "sha");
        addVariants(yoonList, "しゅ", "shu");
        addVariants(yoonList, "しょ", "sho");
        addVariants(yoonList, "ちゃ", "cha");
        addVariants(yoonList, "ちゅ", "chu");
        addVariants(yoonList, "ちょ", "cho");
        addVariants(yoonList, "にゃ", "nya");
        addVariants(yoonList, "にゅ", "nyu");
        addVariants(yoonList, "にょ", "nyo");
        addVariants(yoonList, "ひゃ", "hya");
        addVariants(yoonList, "ひゅ", "hyu");
        addVariants(yoonList, "ひょ", "hyo");
        addVariants(yoonList, "みゃ", "mya");
        addVariants(yoonList, "みゅ", "myu");
        addVariants(yoonList, "みょ", "myo");
        addVariants(yoonList, "りゃ", "rya");
        addVariants(yoonList, "りゅ", "ryu");
        addVariants(yoonList, "りょ", "ryo");
        addVariants(yoonList, "ぎゃ", "gya");
        addVariants(yoonList, "ぎゅ", "gyu");
        addVariants(yoonList, "ぎょ", "gyo");
        addVariants(yoonList, "じゃ", "ja");
        addVariants(yoonList, "じゅ", "ju");
        addVariants(yoonList, "じょ", "jo");
        addVariants(yoonList, "びゃ", "bya");
        addVariants(yoonList, "びゅ", "byu");
        addVariants(yoonList, "びょ", "byo");
        addVariants(yoonList, "ぴゃ", "pya");
        addVariants(yoonList, "ぴゅ", "pyu");
        addVariants(yoonList, "ぴょ", "pyo");

        hiraganaVariantsMap.put("dakuten", dakutenList);
        hiraganaVariantsMap.put("yoon", yoonList);

        return hiraganaVariantsMap;
    }

    public static Map<String, List<Map<String, String>>> getKatakanaVariants() {
        Map<String, List<Map<String, String>>> katakanaVariantsMap = new LinkedHashMap<>();

        List<Map<String, String>> dakutenList = new ArrayList<>();
        addVariants(dakutenList, "ガ", "ga");
        addVariants(dakutenList, "ギ", "gi");
        addVariants(dakutenList, "グ", "gu");
        addVariants(dakutenList, "ゲ", "ge");
        addVariants(dakutenList, "ゴ", "go");
        addVariants(dakutenList, "ザ", "za");
        addVariants(dakutenList, "ジ", "ji");
        addVariants(dakutenList, "ズ", "zu");
        addVariants(dakutenList, "ゼ", "ze");
        addVariants(dakutenList, "ゾ", "zo");
        addVariants(dakutenList, "ダ", "da");
        addVariants(dakutenList, "ヂ", "ji");
        addVariants(dakutenList, "ヅ", "zu");
        addVariants(dakutenList, "デ", "de");
        addVariants(dakutenList, "ド", "do");
        addVariants(dakutenList, "バ", "ba");
        addVariants(dakutenList, "ビ", "bi");
        addVariants(dakutenList, "ブ", "bu");
        addVariants(dakutenList, "ベ", "be");
        addVariants(dakutenList, "ボ", "bo");
        addVariants(dakutenList, "パ", "pa");
        addVariants(dakutenList, "ピ", "pi");
        addVariants(dakutenList, "プ", "pu");
        addVariants(dakutenList, "ペ", "pe");
        addVariants(dakutenList, "ポ", "po");

        List<Map<String, String>> yoonList = new ArrayList<>();
        addVariants(yoonList, "キャ", "kya");
        addVariants(yoonList, "キュ", "kyu");
        addVariants(yoonList, "キョ", "kyo");
        addVariants(yoonList, "シャ", "sha");
        addVariants(yoonList, "シュ", "shu");
        addVariants(yoonList, "ショ", "sho");
        addVariants(yoonList, "チャ", "cha");
        addVariants(yoonList, "チュ", "chu");
        addVariants(yoonList, "チョ", "cho");
        addVariants(yoonList, "ニャ", "nya");
        addVariants(yoonList, "ニュ", "nyu");
        addVariants(yoonList, "ニョ", "nyo");
        addVariants(yoonList, "ヒャ", "hya");
        addVariants(yoonList, "ヒュ", "hyu");
        addVariants(yoonList, "ヒョ", "hyo");
        addVariants(yoonList, "ミャ", "mya");
        addVariants(yoonList, "ミュ", "myu");
        addVariants(yoonList, "ミョ", "myo");
        addVariants(yoonList, "リャ", "rya");
        addVariants(yoonList, "リュ", "ryu");
        addVariants(yoonList, "リョ", "ryo");
        addVariants(yoonList, "ギャ", "gya");
        addVariants(yoonList, "ギュ", "gyu");
        addVariants(yoonList, "ギョ", "gyo");
        addVariants(yoonList, "ジャ", "ja");
        addVariants(yoonList, "ジュ", "ju");
        addVariants(yoonList, "ジョ", "jo");
        addVariants(yoonList, "ビャ", "bya");
        addVariants(yoonList, "ビュ", "byu");
        addVariants(yoonList, "ビョ", "byo");
        addVariants(yoonList, "ピャ", "pya");
        addVariants(yoonList, "ピュ", "pyu");
        addVariants(yoonList, "ピョ", "pyo");

        katakanaVariantsMap.put("dakuten", dakutenList);
        katakanaVariantsMap.put("yoon", yoonList);

        return katakanaVariantsMap;
    }

    private static void addVariants(List<Map<String, String>> list, String character, String romaji) {
        Map<String, String> entry = new LinkedHashMap<>();
        entry.put("character", character);
        entry.put("romaji", romaji);
        list.add(entry);
    }
}
