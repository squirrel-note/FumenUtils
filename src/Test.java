import commands.EntryPoint;

public class Test {
    public static void main(String[] args) throws Exception {
        String commands = "";

//        commands = "move -t v115@vhM2OJvEJULJdMJpHJTNJKDJUBJu6IX9I6+ITvIJwI -p t -op all -h 8";
//        commands = "concat -li knew -tp test/fumens.txt -o test/outout/concat.txt";
//        commands = "sup -p litizj -b i! -op lack -o a.txt";
        commands = "seq -p [^ijls]! -o test/output/a.txt";

        EntryPoint.main(commands.split(" "));
    }
}
