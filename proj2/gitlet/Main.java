package gitlet;


import javax.sound.midi.SysexMessage;

import static gitlet.Repository.*;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author TODO
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            throw Utils.error("Please enter a command");
        }

        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                init();
                break;
            case "add":
                String fileName = args[1];
                add(fileName);
                break;
            case "commit":
                String message = args[1];
                // Throws an error if no message is given
                if (message.isEmpty()) {
                    throw Utils.error("Please enter a commit message");
                }
                commit(message);
                break;
            case "rm":
                // TODO
                break;
            case "log":
                // TODO
                break;
            case "global-log":
                // TODO
                break;
            case "find":
                // TODO
                break;
            case "status":
                // TODO
                break;
            case "checkout":
                // TODO
                break;
            case "branch":
                // TODO
                break;
            case "rm-branch":
                // TODO
                break;
            case "reset":
                // TODO
                break;
            case "merge":
                // TODO
                break;
            default:
                throw Utils.error("No command with that name exists");
        }
    }
}
