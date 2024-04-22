import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileComparator {

    public static boolean compareFiles(String path1, String path2) {
        try {
            BufferedReader buff1 = new BufferedReader(new FileReader(path1));
            BufferedReader buff2 = new BufferedReader(new FileReader(path2));
            List<String> file1 = buff1.lines().toList();
            List<String> file2 = buff2.lines().toList();
            buff1.close();
            buff2.close();
            return file1.equals(file2);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
