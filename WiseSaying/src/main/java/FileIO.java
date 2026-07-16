import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileIO {

    private static final String path = System.getProperty("user.dir") + "/WiseSaying/src/main/java/db/wiseSaying/";

    private static void fileOutput(File file, String content) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte[] bytes = content.getBytes();

            bos.write(bytes, 0, bytes.length);

        } catch (IOException e) {
            System.out.println("***파일 저장에 실패했습니다.***");
            System.out.println(e.getMessage());
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
        json = json.substring(0, json.length()-2);
        json += "\n]";

        fileOutput(jsonFile, json);
    }
}
