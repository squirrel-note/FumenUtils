package commands.common;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Input {
    public static List<String> inputAll(String path) throws Exception {
        return Files.readAllLines(Paths.get(new File(path).getAbsolutePath()));
    }
}
