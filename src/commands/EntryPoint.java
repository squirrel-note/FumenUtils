package commands;

import java.util.Arrays;
import java.util.List;

import commands.clearcomment.ClearCommentEntryPoint;
import commands.clearline.ClearLineEntryPoint;
import commands.concat.ConcatEntryPoint;
import commands.dry.DryEntryPoint;
import commands.getpiece.GetPieceEntryPoint;
import commands.getpiececode.GetPieceCodeEntryPoint;
import commands.height.HeightEntryPoint;
import commands.hole.HoleEntryPoint;
import commands.move.MoveEntryPoint;
import commands.pcable.PCableEntryPoint;
import commands.reduce.ReduceEntryPoint;
import commands.sequence.SequenceEntryPoint;
import commands.substractpieces.SubstractPiecesEntryPoint;

public class EntryPoint {
    public static final String VERSION = "v0.10";

    private static final List<String> COMMANDS_LIST = Arrays.asList(
        "clearcomment",
        "clearline",
        "concat",
        "dry",
        "getpiece",
        "getpiececode",
        "height",
        "hole",
        "move",
        "pcable",
        "reduce",
        "sequence",
        "substractpieces"
    );

    public static void main(String[] args) throws Exception {
        if (args.length < 1) printHelp();

        String command = args[0].toLowerCase();
        List<String> argsList = Arrays.asList(args).subList(1, args.length);

        if (command.matches("help|-help|version") || argsList.size() < 1) printHelp();

        switch (command) {
            case "cc":
                command = "clearcomment";
                break;

            case "cl":
                command = "clearline";
                break;

            case "gpc":
            case "getcode":
            case "gc":
                command = "getpiececode";
                break;

            case "piece":
            case "gp":
                command = "getpiece";
                break;

            case "seq":
                command = "sequence";
                break;

            case "sup":
                command = "substractpieces";
                break;

            default: break;
        }

        if (!COMMANDS_LIST.contains(command)) throw new IllegalArgumentException("Invailed command: " + command);

        commands.common.EntryPoint entryPoint = new commands.common.EntryPoint() {
            @Override
            public void run() throws Exception {
                throw new Exception("Unsupported command.");
            }

            @Override
            public String getName() throws Exception {
                throw new Exception("Unsupported command.");
            }
        };

        switch (command) {
            case "clearcomment":
                entryPoint = new ClearCommentEntryPoint(argsList);
                break;
            case "clearline":
                entryPoint = new ClearLineEntryPoint(argsList);
                break;
            case "dry":
                entryPoint = new DryEntryPoint(argsList);
                break;

            case "height":
                entryPoint = new HeightEntryPoint(argsList);
                break;
            case "hole":
                entryPoint = new HoleEntryPoint(argsList);
                break;
            case "pcable":
                entryPoint = new PCableEntryPoint(argsList);
                break;

            case "getpiececode":
                entryPoint = new GetPieceCodeEntryPoint(argsList);
                break;
            case "getpiece":
                entryPoint = new GetPieceEntryPoint(argsList);
                break;
            case "reduce":
                entryPoint = new ReduceEntryPoint(argsList);
                break;

            case "concat":
                entryPoint = new ConcatEntryPoint(argsList);
                break;
            case "move":
                entryPoint = new MoveEntryPoint(argsList);
                break;

            case "sequence":
                entryPoint = new SequenceEntryPoint(argsList);
                break;
            case "substractpieces":
                entryPoint = new SubstractPiecesEntryPoint(argsList);
                break;

            default: throw new Exception();
        }

        System.out.printf("FumenUtils %s / %s : processing......%n", VERSION, entryPoint.getName());
        entryPoint.run();
        System.out.printf("FumenUtils %s / %s : finish%n", VERSION, entryPoint.getName());
    }

    private static void printHelp() {
        System.out.printf("FumenUtils %s%n", VERSION);
        System.out.println("useage: <command> [options]");
        System.out.println("commands:");
        for (String command : COMMANDS_LIST) System.out.println("    - " + command);
        System.exit(0);
    }
}
