package gitlet;

import java.io.File;

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
    public static final File BRANCHES = join(GITLET_DIR, "branches");

    /**
     * A reference to the latest commit in the active branch
     */
    public static String ACTIVE_BRANCH;

    /**
     * A reference to the current node that we are at
     */
    public static String HEAD;

    /**
     * The directory for TO_ADD and TO_REMOVE
     */
    public static final File STAGE_DIR = join(GITLET_DIR, "stage");

    /**
     * This directory keeps track of the files that are staged for commit
     */
    public static final File TO_ADD = join(STAGE_DIR, "to_add");

    /**
     * This directory keeps track of the files that are staged for removal
     */
    public static final File TO_REMOVE = join(STAGE_DIR, "to_remove");
    ;

    /**
     * A directory that stores all the commits that have been made
     */
    public static final File COMMITS = join(GITLET_DIR, "commits");

    /**
     * This directory stores all the versions of all the files that have been committed
     */
    public static final File FILES = join(GITLET_DIR, "files");

    /* TODO: fill in the rest of this class. */
    public static void setupPersistence() {
        // Creating folders
        GITLET_DIR.mkdir();
        BRANCHES.mkdir();
        STAGE_DIR.mkdir();
        TO_ADD.mkdir();
        TO_REMOVE.mkdir();
        COMMITS.mkdir();
        FILES.mkdir();
    }

    /**
     * Stage the file for addition
     */
    public static void add() {

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

    public static void status() {}

    public static void checkout() {}

    public static void branch() {

    }

    public static void removeBranch() {

    }

    public static void reset() {

    }

    public static void merge() {

    }
}
