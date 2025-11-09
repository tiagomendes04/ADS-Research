public class Main {
    public static void main(String[] args) {
        SearchEngine searchEngine = new SearchEngine();

        Document document1 = new Document("doc1", "This is a sample document.");
        Document document2 = new Document("doc2", "This is another sample document.");
        Document document3 = new Document("doc3", "This is a different sample document.");

        searchEngine.addDocument(document1.getId(), document1.getContent());
        searchEngine.addDocument(document2.getId(), document2.getContent());
        searchEngine.addDocument(document3.getId(), document3.getContent());

        System.out.println(searchEngine.search("sample document")); // Output: [doc1, doc2, doc3]
        System.out.println(searchEngine.search("different sample")); // Output: [doc3]
    }
}