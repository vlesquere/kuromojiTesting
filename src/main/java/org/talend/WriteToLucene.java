package org.talend;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriteToLucene {

    private static final String INDEX_DIR = System.getProperty("java.io.tmpdir") + "/luceneKuromoji";

    public static void main(String[] args) throws IOException {
        IndexWriter writer = createWriter();
        //Let's clean everything first
        writer.deleteAll();

        List<Document> documents = new ArrayList<>();

        Document document1 = createDocument(1, "je veux manger des sushis", "i want to eat sushi", "お寿司が食べたい。");
        Document document2 = createDocument(2, "il est dans la cuisine", "he is in the kitchen", "彼は台所にいる");
        Document document3 = createDocument(3, "aéroport international de Kansai", "Kansai international aiport", "関西国際空港");
        documents.add(document1);
        documents.add(document2);
        documents.add(document3);

        writer.addDocuments(documents);
        writer.commit();
        writer.close();
    }

    private static IndexWriter createWriter() throws IOException
    {
        FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }

    private static Document createDocument(Integer id, String french, String english, String japanese)
    {
        Document document = new Document();
        document.add(new StringField("id", id.toString() , Field.Store.YES));
        document.add(new TextField("french", french , Field.Store.YES));
        document.add(new TextField("english", english , Field.Store.YES));
        document.add(new TextField("japanese", japanese , Field.Store.YES));

        return document;
    }
}
