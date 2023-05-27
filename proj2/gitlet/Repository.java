package gitlet;

import java.io.File;
import java.util.HashMap;

import static gitlet.Utils.*;

/**
 * Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */


    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /**
     * A folder for storing references to branches
     */
    public static final File BRANCHES_DIR = join(GITLET_DIR, "branches");

    /**
     * The name of the branch that we are currently in
     */
    public static String ACTIVE_BRANCH;

    /**
     * A reference to the current commit that we are at
     */
    public static String HEAD;

    /**
     * The directory for TO_ADD and TO_REMOVE
     */
    public static final File STAGE_DIR = join(GITLET_DIR, "stage");

    /**
     * This directory keeps track of the files that are staged for commit
     */
    public static final File TO_ADD_DIR = join(STAGE_DIR, "to_add");

    /**
     * This directory keeps track of the files that are staged for removal
     */
    public static final File TO_REMOVE_DIR = join(STAGE_DIR, "to_remove");
    ;

    /**
     * A directory that stores all the commits that have been made
     */
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits");

    /**
     * This a file that is an object that uses hashmap
     * stores all versions of all the files that have been committed
     */
    public static final File COMMIT_FILES = join(GITLET_DIR, "files");

    /**
     * This will read COMMIT_FILES as an object
     */
    private static HashMap<String, File> commitFiles;


    public static void init() {
        // Create all the necessary folders
        GITLET_DIR.mkdir();
        BRANCHES_DIR.mkdir();
        STAGE_DIR.mkdir();
        TO_ADD_DIR.mkdir();
        TO_REMOVE_DIR.mkdir();
        COMMITS_DIR.mkdir();
        FILES.mkdirs();

        // Create an initial commit
        Commit initialCommit = new Commit();
        // Create an initial branch
        Branch masterBranch = new Branch("master", initialCommit.id);
        // Save the branch data into an actual file
        masterBranch.saveBranch();
        switchBranch("master");
        initialCommit.saveCommit();
    }

    /**
     * Stage the file for addition
     */
    // TODO: Handle the case where the staged file is already in the commit
    public static void add(String fileName) {
        File file = Utils.join(CWD, fileName);
        File stageFile = Utils.join(COMMITS_DIR, fileName);
        // Copies the contents of file to stageFile
        Utils.writeContents(stageFile, Utils.readContents(file));
    }

    public static void commit() {

    }

    /**
     * Unstage the file if it is currently staged for addition
     * If it is tracked in the current commit, stage it for removal
     * and remove the file from the working directory
     */
    public static void remove() {

    }

    public static void log() {

    }

    public static void globalLog() {
    }

    public static void find() {

    }

    public static void status() {
    }

    public static void checkout() {
    }

    public static void branch(String branchName) {
        Branch newBranch = new Branch(branchName, HEAD);
        newBranch.saveBranch();
    }

    public static void removeBranch() {

    }

    public static void reset() {

    }

    public static void merge() {

    }

    /**
     * Moves the HEAD pointer and change the ACTIVE_BRANCH
     */
    private static void switchBranch(String branchName) {
        ACTIVE_BRANCH = branchName;
        HEAD = Branch.getBranchRef(branchName);
    }

}
