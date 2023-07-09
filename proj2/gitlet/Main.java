package gitlet;


import javax.sound.midi.SysexMessage;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author Archer
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
        Repository repo = Repository.getRepo();
        if (!Repository.GITLET_DIR.exists() && !firstArg.equals("init")) {
            throw Utils.error("Not in an initialized Gitlet directory.");
        }
        else if (Repository.GITLET_DIR.exists() && firstArg.equals("init")) {
            throw Utils.error("A Gitlet version-control system already exists in the current directory.");
        }
        switch (firstArg) {
            case "init":
                repo = new Repository();
                break;
            case "add":
                String fileName = args[1];
                repo.add(fileName);
                break;
            case "commit":
                String message = args[1];
                // Throws an error if no message is given
                if (message.isEmpty()) {
                    throw Utils.error("Please enter a commit message.");
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
                repo.find(args[1]);
                break;
            case "status":
                repo.status();
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
                    repo.checkoutBranch(args[1]);
                }
                break;
            case "branch":
                String branchName = args[1];
                repo.branch(branchName);
                break;
            case "rm-branch":
                repo.removeBranch(args[1]);
                break;
            case "reset":
                repo.reset(args[1]);
                break;
            case "merge":
                repo.merge(args[1]);
                break;
            default:
                throw Utils.error("No command with that name exists");
        }
        repo.saveRepo();
    }
}
