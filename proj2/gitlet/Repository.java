package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;


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

    /**
     * A directory that stores all the commits that have been made
     */
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits");

    /**
     * This is a directory that stores all the committed files
     */
    public static final File COMMIT_FILES = join(GITLET_DIR, "commit_files");

    /**
     * This object map each sha-1 id to each file
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
        COMMIT_FILES.mkdirs();

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
    public static void add(String fileName) {
        // Gets the file in the Current Working Directory with the given name
        File file = Utils.join(CWD, fileName);

        if (!file.exists()) {
            throw Utils.error("File does not exist.");
        }

        // Get file's SHA-1 ID
        String fileKey = Utils.getFileId(file);

        File stageFile = Utils.join(COMMITS_DIR, fileName);

        // If the content of this file is already in the commit, remove it from the staging area.
        if (commitFiles.containsKey(fileKey)) {
            stageFile.delete();
            return;
        }

        // Copies the contents of file to stageFile
        Utils.copyFile(file, stageFile);
    }

    /**
     * Saves all the files in the staging area into COMMIT_FILES directory
     * A new commit is created
     */
    public static void commit(String message) {
        Commit prevCommit = Commit.fromFile(HEAD);
        // A tree map with keys as name and values as sha-1 id
        // We get tracked files from the previous commit
        TreeMap<String, String> fileMap = prevCommit.fileMap;
        // Names of the files that are staged for addition
        String[] toAddFiles = TO_ADD_DIR.list();

        // Adds all the files in the staging directory to fileMap
        if (toAddFiles != null) {
            for (String fileName : toAddFiles) {
                File file = Utils.join(TO_ADD_DIR, fileName);
                File commitFile = Utils.join(COMMITS_DIR, Utils.getFileId(file));
                fileMap.put(fileName, Utils.sha1(Utils.readContents(file)));
            }
        }

        Commit newCommit = new Commit(message, fileMap, prevCommit.id);

        // Clear all the files in the staging area
        clearDirectory(TO_ADD_DIR);
        clearDirectory(TO_REMOVE_DIR);
        // Points HEAD to the latest commit in the branch
        HEAD = newCommit.id;
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
