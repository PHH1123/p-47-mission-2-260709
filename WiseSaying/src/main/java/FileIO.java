import java.io.*;
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

            byte[] bytes = content.getBytes();

            bos.write(bytes, 0, bytes.length);

        } catch (IOException e) {
            System.out.println("***파일 저장에 실패했습니다.***");
            System.out.println(e.getMessage());
        }
    }

    public static List<WiseSaying> fileInput() {
        String data = null;
        File dataFile = new File(path + "data.json");

        try (FileInputStream fis = new FileInputStream(dataFile);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            byte[] bufferArr = new byte[4096];
            StringBuilder sb = new StringBuilder();

            while (bis.read(bufferArr) != -1) {
                sb.append(new String(bufferArr));
            }

            data = sb.toString();

        } catch (IOException e) {
            System.out.println("***파일 읽기에 실패했습니다.***");
            System.out.println(e.getMessage());
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

        String[] dataSplit = data.replaceAll("[\\[\\s]", "").split("\\{");

        for (String s : dataSplit) {
            if (s.isEmpty()) {
                continue;
            }

            String[] parsing = Arrays.stream(s.split(","))
                    .map(str ->
                            str.split(":")[1]
                                    .replaceAll("[\"}\\]]", ""))
                    .toArray(String[]::new);

            wiseSayings.add(new WiseSaying(Integer.parseInt(parsing[0]), parsing[1], parsing[2]));
        }

        return wiseSayings;
    }
}
