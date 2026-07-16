public class WiseSaying {
    private int no;
    private String author;
    private String content;

    public WiseSaying() {
    }

    public WiseSaying(int no, String author, String content) {
        this.no = no;
        this.author = author;
        this.content = content;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
