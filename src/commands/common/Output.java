package commands.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class Output {
    public static void output(Object output, String path) throws Exception {
        if (path.equals("")) {
            System.out.println(output);
        } else {
            File file = new File(path).getParentFile();
            if (file != null) file.mkdirs();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path)));
            pw.println(output);
            pw.close();
        }
    }

    public static void listOutput(List<String> output, String path) throws Exception {
        if (path.equals("")) {
            for (String a : output) System.out.println(a);
        } else {
            File file = new File(path).getParentFile();
            if (file != null) file.mkdirs();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path)));
            for (String a : output) pw.println(a);
            pw.close();
        }
    }
}
