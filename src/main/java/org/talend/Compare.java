package org.talend;

import com.atilika.kuromoji.TokenizerBase;
import com.atilika.kuromoji.ipadic.Tokenizer;

import java.util.List;

import static com.atilika.kuromoji.TokenizerBase.Mode.EXTENDED;
import static com.atilika.kuromoji.TokenizerBase.Mode.SEARCH;

public class Compare {

    public static void main(String[] args) {
        final String JAPANESE = "寿司が食べたい。"; //i want to eat sushis
        
        // 寿司 : sushi
        // が
        // 食べ : to eat
        // たい : i want
        // 。 : period

        //final String JAPANESE = "上総本一揆（かずさのほんいっき）とは、応永25年（1418年）から翌年にかけて発生した上総国の武士達による一揆。";
        //Kazushimoto Ikki (Kazusa no kokori) is a revolt by samurai warriors in Kazusagi that occurred from 25 years of age (1418) to the following year.


        System.out.println("--- Common parts ---");
        com.atilika.kuromoji.ipadic.Tokenizer commonTokenizer = new com.atilika.kuromoji.ipadic.Tokenizer();
        List<com.atilika.kuromoji.ipadic.Token> commonTokens = commonTokenizer.tokenize(JAPANESE);
        for (com.atilika.kuromoji.ipadic.Token token : commonTokens) {
            System.out.print(token.getSurface() + "\t" + token.getAllFeatures());
            System.out.print("| " + token.getPartOfSpeechLevel1());
            System.out.print(" | " + token.getPartOfSpeechLevel2());
            System.out.print(" | " + token.getPartOfSpeechLevel3());
            System.out.print(" | " + token.getPartOfSpeechLevel4());
            System.out.print(" | " + token.getReading());
            System.out.println(" |" + token.getBaseForm());
        }

        System.out.println("--- IPADIC ---");
        com.atilika.kuromoji.ipadic.Tokenizer ipadicTokenizer = new com.atilika.kuromoji.ipadic.Tokenizer();
        List<com.atilika.kuromoji.ipadic.Token> ipadicTokens = ipadicTokenizer.tokenize(JAPANESE);
        System.out.println(String.format("%-5s|%-20s|%-20s|%-20s", "Token", "ConjugationType", "ConjugationForm", "Pronunciation"));
        for (com.atilika.kuromoji.ipadic.Token token : ipadicTokens) {
            System.out.println(String.format("%-5s|%-20s|%-20s|%-20s", token.getSurface(), token.getConjugationType(), token.getConjugationForm(), token.getPronunciation()));
        }

        System.out.println("--- IPADIC SEARCH MODE---");
        Tokenizer.Builder builder = new Tokenizer.Builder();
        builder.mode(SEARCH);
        com.atilika.kuromoji.ipadic.Tokenizer ipadicSearchTokenizer = builder.build();
        List<com.atilika.kuromoji.ipadic.Token> ipadicSearchTokens = ipadicSearchTokenizer.tokenize(JAPANESE);
        System.out.println(String.format("%-5s|%-20s|%-20s|%-20s", "Token", "ConjugationType", "ConjugationForm", "Pronunciation"));
        for (com.atilika.kuromoji.ipadic.Token token : ipadicSearchTokens) {
            System.out.println(String.format("%-5s|%-20s|%-20s|%-20s", token.getSurface(), token.getConjugationType(), token.getConjugationForm(), token.getPronunciation()));
        }

        System.out.println("--- IPADIC EXTENDED MODE---");
        Tokenizer.Builder builderExtended = new Tokenizer.Builder();
        builderExtended.mode(EXTENDED);
        com.atilika.kuromoji.ipadic.Tokenizer ipadicExtendedTokenizer = builderExtended.build();
        List<com.atilika.kuromoji.ipadic.Token> ipadicExtendedTokens = ipadicExtendedTokenizer.tokenize(JAPANESE);
        System.out.println(String.format("%-5s|%-20s|%-20s|%-20s", "Token", "ConjugationType", "ConjugationForm", "Pronunciation"));
        for (com.atilika.kuromoji.ipadic.Token token : ipadicExtendedTokens) {
            System.out.println(String.format("%-5s|%-20s|%-20s|%-20s", token.getSurface(), token.getConjugationType(), token.getConjugationForm(), token.getPronunciation()));
        }

        System.out.println("--- JUMANDIC ---");
        com.atilika.kuromoji.jumandic.Tokenizer jumandicTokenizer = new com.atilika.kuromoji.jumandic.Tokenizer();
        List<com.atilika.kuromoji.jumandic.Token> jumandicTokens = jumandicTokenizer.tokenize(JAPANESE);
        System.out.println(String.format("%-5s|%-20s|%-20s", "Token", "SemanticInformation", "Reading"));
        for (com.atilika.kuromoji.jumandic.Token token : jumandicTokens) {
            System.out.println(String.format("%-5s|%-20s|%-20s", token.getSurface(), token.getSemanticInformation(), token.getReading()));
        }

        System.out.println("--- NAIST-JDIC ---");
        com.atilika.kuromoji.naist.jdic.Tokenizer naistJDicTokenizer = new com.atilika.kuromoji.naist.jdic.Tokenizer();
        List<com.atilika.kuromoji.naist.jdic.Token> naistJDicTokens = naistJDicTokenizer.tokenize(JAPANESE);
        System.out.println(String.format("%-5s|%-20s|%-20s|%-20s|%-20s|%-20s", "Token", "ConjugationType", "ConjugationForm", "CompoundInformation", "Pronunciation", "TranscriptionVariation"));
        for (com.atilika.kuromoji.naist.jdic.Token token : naistJDicTokens) {
            System.out.println(String.format("%-5s|%-20s|%-20s|%-20s|%-20s|%-20s", token.getSurface(), token.getConjugationType(), token.getConjugationForm(), token.getCompoundInformation(), token.getPronunciation(), token.getTranscriptionVariation()));
        }

        System.out.println("--- UNIDIC KANAACCENT---");
        com.atilika.kuromoji.unidic.kanaaccent.Tokenizer unidicKanaTokenizer = new com.atilika.kuromoji.unidic.kanaaccent.Tokenizer();
        List<com.atilika.kuromoji.unidic.kanaaccent.Token> unidicKanaTokens = unidicKanaTokenizer.tokenize(JAPANESE);
        System.out.println(String.format("%-5s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s", "Token", "Lemma", "KanaBase", "Kana", "ConjugationType", "ConjugationForm", "LanguageType", "AccentConnectionType", "AccentModificationType"));
        for (com.atilika.kuromoji.unidic.kanaaccent.Token token : unidicKanaTokens) {
            System.out.println(String.format("%-5s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s", token.getSurface(), token.getLemma(), token.getKanaBase(), token.getKana(), token.getConjugationType(), token.getConjugationForm(), token.getLanguageType(), token.getAccentConnectionType(), token.getAccentModificationType()));
        }

        System.out.println("--- UNIDIC ---");
        com.atilika.kuromoji.unidic.Tokenizer unidicTokenizer = new com.atilika.kuromoji.unidic.Tokenizer();
        List<com.atilika.kuromoji.unidic.Token> unidicTokens = unidicTokenizer.tokenize(JAPANESE);
        System.out.println(String.format("%-5s|%-20s|%-20s|%-20s", "Token", "LanguageType", "WrittenBaseForm", "WrittenForm"));
        for (com.atilika.kuromoji.unidic.Token token : unidicTokens) {
            System.out.println(String.format("%-5s|%-20s|%-20s|%-20s", token.getSurface(), token.getLanguageType(), token.getWrittenBaseForm(), token.getWrittenForm()));
        }
    }
}
