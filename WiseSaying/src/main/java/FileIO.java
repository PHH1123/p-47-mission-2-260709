import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileIO {

    private static final String path = System.getProperty("user.dir") + "/WiseSaying/src/main/java/db/wiseSaying/";

    /*
    이번 과제에서 알게 된 기능
    자바7 부터 도입된 기능 - try-with-resource
    자동으로 연결을 close() 해준다.

    AutoCloseable 인터페이스의 구현체여야 함

    JDBC 커넥션도 이렇게 사용 가능하다.

    javac 컴파일 시 try-catch-finally 형태로 바꿔준다.
    */
    private static void fileOutput(File file, String content) {
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

            bos.write(bytes, 0, bytes.length);

        } catch (IOException e) {
            System.out.println("***파일 저장에 실패했습니다.***");
            System.out.println(e.getMessage());
        }
    }

    /*
    기존에는 버퍼의 크기를 4096바이트로 하고 가져왔는데
    이렇게 하면 한글이 깨질 가능성이 있다고 해서
    메모리의 크기가 받쳐주는 안에서는 아래처럼 한꺼번에 가져와도 된다고 한다.
    그리고 가능하면 인코딩 방식도 맞춰주는 것이 좋다.
     */
    public static List<WiseSaying> fileInput() {
        String data = null;
        File dataFile = new File(path + "data.json");

        try (FileInputStream fis = new FileInputStream(dataFile)) {

            byte[] bufferArr = fis.readAllBytes();

            data = new String(bufferArr, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.out.println("***파일 읽기에 실패했습니다.***");
            System.out.println("에러: " + e.getMessage());
        }

        return jsonParsing(data);
    }

    public static void jsonOutput(WiseSaying wiseSaying) {
        File jsonFile = new File(path + wiseSaying.getNo() + ".json");

        String json = "{\n" +
                "\t\"id\": " + wiseSaying.getNo() + ",\n" +
                "\t\"content\": \"" + wiseSaying.getContent() + "\",\n" +
                "\t\"author\": \"" + wiseSaying.getAuthor() + "\"\n" +
                "}";

        fileOutput(jsonFile, json);
    }

    public static void txtOutput(int no) {
        File txtFile = new File(path + "lastId.txt");

        String txt = String.valueOf(no);

        fileOutput(txtFile, txt);
    }

    public static void saveAllJsonOutput(List<WiseSaying> wiseSayings) {
        File jsonFile = new File(path + "data.json");

        String json = "[\n";
        for (WiseSaying wiseSaying : wiseSayings) {
            json += "\t{\n" +
                    "\t\t\"id\": " + wiseSaying.getNo() + ",\n" +
                    "\t\t\"content\": \"" + wiseSaying.getContent() + "\",\n" +
                    "\t\t\"author\": \"" + wiseSaying.getAuthor() + "\"\n" +
                    "\t},\n";
        }
        json = json.substring(0, json.length() - 2);
        json += "\n]";

        fileOutput(jsonFile, json);
    }

    private static List<WiseSaying> jsonParsing(String data) {
        List<WiseSaying> wiseSayings = new ArrayList<>();

        if (data == null) {
            return wiseSayings;
        }

        String[] dataSplit = data.replaceAll("[\\[\t\n]", "").split("\\{");

        for (String s : dataSplit) {
            if (s.trim().isEmpty()) {
                continue;
            }

            String[] parsing = Arrays.stream(s.split(","))
                    .map(str ->
                            str.split(":")[1]
                                    .trim()
                                    .replaceAll("[\"}\\]]", ""))
                    .toArray(String[]::new);

            wiseSayings.add(new WiseSaying(Integer.parseInt(parsing[0]), parsing[1], parsing[2]));
        }

        return wiseSayings;
    }
}
