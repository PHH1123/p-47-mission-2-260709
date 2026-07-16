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

        while(true) {
            System.out.print("명령) ");
            input = sc.nextLine();
            String prefix = input.contains("?") ? input.split("\\?")[0] : input;

            switch (prefix) {
                case "종료":
                    return;
                case "등록":
                    save(sc, contents, authors);
                    break;
                case "목록":
                    getList(contents, authors);
                    break;
                case "삭제":
                    delete(input, contents, authors);
                    break;
                case "수정":
                    update(sc, input, contents, authors);
                    break;
            }
        }
    }

    private static void save(Scanner sc, List<String> contents, List<String> authors) {
        System.out.print("명언 : ");
        contents.add(sc.nextLine());
        System.out.print("작가 : ");
        authors.add(sc.nextLine());
        System.out.println(contents.size() + "번 명언이 등록되었습니다.");
    }

    private static void getList(List<String> contents, List<String> authors) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("---------------------------");
        for (int i = contents.size(); i > 0; i--) {
            if (contents.get(i - 1) == null) {
                continue;
            }
            System.out.println(i + " / " +
                    authors.get(i - 1) + " / " +
                    contents.get(i - 1));
        }
    }

    private static void delete(String input, List<String> contents, List<String> authors) {
        try {
            int id = getId(input);
            validId(contents, id);

            contents.set(id - 1, null);
            authors.set(id - 1, null);
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } catch (RuntimeException e) {
            System.out.println(input.split("=")[1] + "번 명언은 존재하지 않습니다.");
        }
    }

    private static void update(Scanner sc, String input, List<String> contents, List<String> authors) {
        try {
            int id = getId(input);
            validId(contents, id);

            System.out.println("명언(기존) : " + contents.get(id - 1));
            System.out.print("명언 : ");
            contents.set(id - 1, sc.nextLine());
            System.out.println("작가(기존) : " + authors.get(id - 1));
            System.out.print("작가 : ");
            authors.set(id - 1, sc.nextLine());
        } catch (RuntimeException e) {
            System.out.println(input.split("=")[1] + "번 명언은 존재하지 않습니다.");
        }
    }

    private static void validId(List<String> contents, int id) {
        if (contents.size() < id || contents.get(id -1) == null) {
            throw new RuntimeException();
        }
    }

    private static int getId(String input) {
        return Integer.parseInt(input.split("=")[1]);
    }
}
