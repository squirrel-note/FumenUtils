public class FumenUtils {
    public static void main(String[] args) throws Exception {
        if (args.length < 1 || args[0].toLowerCase().equals("gui")) {
            gui.EntryPoint.main();
        } else {
            commands.EntryPoint.main(args);
        }
    }
}
