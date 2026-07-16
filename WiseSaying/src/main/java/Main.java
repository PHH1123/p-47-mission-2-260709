import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("== 명언 앱 ==");
        String input;

        List<String> contents = new ArrayList<>();
        List<String> authors = new ArrayList<>();

        List<WiseSaying> wiseSayings = new ArrayList<>();


        while (true) {
            System.out.print("명령) ");
            input = sc.nextLine();
            String prefix = input.contains("?") ? input.split("\\?")[0] : input;

            switch (prefix) {
                case "종료":
                    return;
                case "등록":
                    save(sc, wiseSayings, contents, authors);
                    break;
                case "목록":
                    getList(wiseSayings, contents, authors);
                    break;
                case "삭제":
                    delete(input, wiseSayings, contents, authors);
                    break;
                case "수정":
                    update(sc, input, wiseSayings, contents, authors);
                    break;
            }
        }
    }

    private static void save(Scanner sc, List<WiseSaying> wiseSayings, List<String> contents, List<String> authors) {
        System.out.print("명언 : ");
        String content = sc.nextLine();
//        contents.add(sc.nextLine());
        System.out.print("작가 : ");
        String author = sc.nextLine();
//        authors.add(sc.nextLine());
        WiseSaying wiseSaying = new WiseSaying(wiseSayings.size() + 1, author, content);
        wiseSayings.add(wiseSaying);

        System.out.println(wiseSaying.getNo() + "번 명언이 등록되었습니다.");
    }

    private static void getList(List<WiseSaying> wiseSayings, List<String> contents, List<String> authors) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("---------------------------");
        for (int i = wiseSayings.size(); i > 0; i--) {
            if (wiseSayings.get(i - 1) == null) {
                continue;
            }
            System.out.println(i + " / " +
                    wiseSayings.get(i - 1).getAuthor() + " / " +
                    wiseSayings.get(i - 1).getContent());
        }
    }

    private static void delete(String input, List<WiseSaying> wiseSayings, List<String> contents, List<String> authors) {
        try {
            int id = getId(input);
            validId(wiseSayings, contents, id);

//            wiseSayings.get(id - 1).setContent(null);
//            wiseSayings.get(id - 1).setAuthor(null);
            wiseSayings.set(id - 1, null);
//            contents.set(id - 1, null);
//            authors.set(id - 1, null);
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } catch (RuntimeException e) {
            System.out.println(input.split("=")[1] + "번 명언은 존재하지 않습니다.");
        }
    }

    private static void update(Scanner sc, String input, List<WiseSaying> wiseSayings, List<String> contents, List<String> authors) {
        try {
            int id = getId(input);
            validId(wiseSayings, contents, id);

            System.out.println("명언(기존) : " + wiseSayings.get(id - 1).getContent());
            System.out.print("명언 : ");
            String content = sc.nextLine();
            wiseSayings.get(id - 1).setContent(content);
//            contents.set(id - 1, sc.nextLine());
            System.out.println("작가(기존) : " + wiseSayings.get(id - 1).getAuthor());
            System.out.print("작가 : ");
            String author = sc.nextLine();
            wiseSayings.get(id - 1).setAuthor(author);
//            authors.set(id - 1, sc.nextLine());
        } catch (RuntimeException e) {
            System.out.println(getId(input) + "번 명언은 존재하지 않습니다.");
        }
    }

    private static void validId(List<WiseSaying> wiseSayings, List<String> contents, int id) {
        if (wiseSayings.size() < id || wiseSayings.get(id - 1) == null) {
            throw new RuntimeException();
        }
    }

    private static int getId(String input) {
        return Integer.parseInt(input.split("=")[1]);
    }
}
