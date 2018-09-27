package org.talend;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

//Launch WriteToLucene before testing this class!
public class SearchInLucene {

    private static final String INDEX_DIR = System.getProperty("java.io.tmpdir") + "/luceneKuromoji";
    private static final String I_WANT_SUSHI = "お寿司が食べたい。";
    private static final String KANSAI = "関西国際空港";

    public static void main(String[] args) throws Exception {
        IndexSearcher searcher = createSearcher();

        TopDocs foundDocs = searchByJapanese("寿司", searcher); //sushi

        System.out.println("Total Results :: " + foundDocs.totalHits);
        for (ScoreDoc sd : foundDocs.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format("\tdocument id : " + d.get("id")));
        }

        tokenize(I_WANT_SUSHI, JapaneseTokenizer.Mode.NORMAL);
        System.out.println("----------------");
        tokenize(KANSAI, JapaneseTokenizer.Mode.NORMAL);
        System.out.println("----------------");
        tokenize(KANSAI, JapaneseTokenizer.Mode.SEARCH);
        System.out.println("----------------");
        tokenize(KANSAI, JapaneseTokenizer.Mode.EXTENDED);
    }

    private static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }

    private static TopDocs searchByJapanese(String japanese, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("japanese", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(japanese);
        TopDocs hits = searcher.search(firstNameQuery, 10);
        return hits;
    }

    private static void tokenize(String stringToTokenize, JapaneseTokenizer.Mode mode) throws IOException {
        //mode not used for the moment and don't want to load another stopwords and stoptags
        JapaneseAnalyzer japaneseAnalyzer = new JapaneseAnalyzer();
        //JapaneseAnalyzer japaneseAnalyzer = new JapaneseAnalyzer(null, mode, null, null);
        TokenStream stream = japaneseAnalyzer.tokenStream("TEXT", stringToTokenize);

        CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
        PartOfSpeechAttribute pos = stream.getAttribute(PartOfSpeechAttribute.class);
        stream.reset();
        while (stream.incrementToken()) {
            System.out.print("[" + term.toString() + "]: ");
            System.out.println(pos.getPartOfSpeech());
        }
        stream.close();
    }
}
