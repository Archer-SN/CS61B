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
        Repository repo = getRepo();
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                repo.init();
                break;
            case "add":
                String fileName = args[1];
                repo.add(fileName);
                break;
            case "commit":
                String message = args[1];
                // Throws an error if no message is given
                if (message.isEmpty()) {
                    throw Utils.error("Please enter a commit message");
                }
                repo.commit(message);
                break;
            case "rm":
                // Passes file's name into the remove method
                repo.remove(args[1]);
                break;
            case "log":
                repo.log();
                break;
            case "global-log":
                repo.globalLog();
                break;
            case "find":
                // TODO
                break;
            case "status":
                // TODO
                break;
            case "checkout":
                // First case
                if (args.length == 3) {
                    repo.checkoutFile(args[2]);
                }
                // Second case
                if (args.length == 4) {
                    repo.checkoutFile(args[1], args[3]);
                }
                // Third case
                if (args.length == 2) {
                    repo.checkoutFile(args[1]);
                }
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
