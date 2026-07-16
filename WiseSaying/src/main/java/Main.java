import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final String path = System.getProperty("user.dir") + "/WiseSaying/src/main/java/db/wiseSaying/";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("== 명언 앱 ==");
        String input;

        List<WiseSaying> wiseSayings = new ArrayList<>();

        while (true) {
            System.out.print("명령) ");
            input = sc.nextLine();
            String prefix = input.contains("?") ? input.split("\\?")[0] : input;

            switch (prefix) {
                case "종료":
                    return;
                case "등록":
                    save(sc, wiseSayings);
                    break;
                case "목록":
                    getList(wiseSayings);
                    break;
                case "삭제":
                    delete(input, wiseSayings);
                    break;
                case "수정":
                    update(sc, input, wiseSayings);
                    break;
                case "빌드":
                    FileIO.saveAllJsonOutput(wiseSayings);
                    System.out.println("data.json 파일의 내용이 갱신되었습니다.");
                    break;
            }
        }
    }

    private static void save(Scanner sc, List<WiseSaying> wiseSayings) {
        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.print("작가 : ");
        String author = sc.nextLine();

        WiseSaying wiseSaying = new WiseSaying(wiseSayings.size() + 1, author, content);
        wiseSayings.add(wiseSaying);

        FileIO.jsonOutput(wiseSaying);
        FileIO.txtOutput(wiseSaying.getNo());

        System.out.println(wiseSaying.getNo() + "번 명언이 등록되었습니다.");
    }

    private static void getList(List<WiseSaying> wiseSayings) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("---------------------------");
        for (int i = wiseSayings.size() - 1; i >= 0; i--) {
            WiseSaying wiseSaying = wiseSayings.get(i);

            if (wiseSaying == null) {
                continue;
            }

            System.out.println(wiseSaying.getNo() + " / " +
                    wiseSaying.getAuthor() + " / " +
                    wiseSaying.getContent()
            );
        }
    }

    private static void delete(String input, List<WiseSaying> wiseSayings) {
        try {
            int id = getId(input);
            validId(wiseSayings, id);

            wiseSayings.set(id - 1, null);

            System.out.println(id + "번 명언이 삭제되었습니다.");

        } catch (RuntimeException e) {
            System.out.println(input.split("=")[1] + "번 명언은 존재하지 않습니다.");
        }
    }

    private static void update(Scanner sc, String input, List<WiseSaying> wiseSayings) {
        try {
            int id = getId(input);
            validId(wiseSayings, id);

            WiseSaying wiseSaying = wiseSayings.get(id - 1);

            System.out.println("명언(기존) : " + wiseSaying.getContent());
            System.out.print("명언 : ");
            String content = sc.nextLine();
            wiseSaying.setContent(content);

            System.out.println("작가(기존) : " + wiseSaying.getAuthor());
            System.out.print("작가 : ");
            String author = sc.nextLine();
            wiseSaying.setAuthor(author);

            FileIO.jsonOutput(wiseSaying);

        } catch (RuntimeException e) {
            System.out.println(getId(input) + "번 명언은 존재하지 않습니다.");
        }
    }

    private static void validId(List<WiseSaying> wiseSayings, int id) {
        if (wiseSayings.size() < id || wiseSayings.get(id - 1) == null) {
            throw new RuntimeException();
        }
    }

    private static int getId(String input) {
        return Integer.parseInt(input.split("=")[1]);
    }
}
