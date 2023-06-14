package gitlet;

import jdk.jshell.execution.Util;

import java.io.File;
import java.io.Serializable;
import java.util.*;


/**
 * Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Repository implements Serializable {
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
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");

    /**
     * A folder for storing references to branches
     */
    public static final File BRANCHES_DIR = Utils.join(GITLET_DIR, "branches");

    /**
     * The name of the branch that we are currently in
     */
    public String ACTIVE_BRANCH;

    /**
     * A reference to the current commit that we are at
     */
    public String HEAD;

    /**
     * The directory for TO_ADD and TO_REMOVE
     */
    public static final File STAGE_DIR = Utils.join(GITLET_DIR, "stage");

    /**
     * This directory keeps track of the files that are staged for commit
     */
    public static final File TO_ADD_DIR = Utils.join(STAGE_DIR, "to_add");

    /**
     * This directory keeps track of the files that are staged for removal
     */
    public static final File TO_REMOVE_DIR = Utils.join(STAGE_DIR, "to_remove");

    /**
     * A directory that stores all the commits that have been made
     */
    public static final File COMMITS_DIR = Utils.join(GITLET_DIR, "commits");

    /**
     * This is a directory that stores all the committed files
     */
    public static final File COMMIT_FILES_DIR = Utils.join(GITLET_DIR, "commit_files");

    // This file saves the Repository object that is created
    public static final File REPO = Utils.join(GITLET_DIR, "repo");

    /**
     * This object maps each sha-1 id to each file
     */
    private HashMap<String, File> commitFiles;

    /**
     * Keeps track of names of all the files that are being tracked
     */
    private HashSet<String> trackedFileNames;

    /**
     * This object keeps tracks of the names of the files that are being staged for addition
     */
    private HashSet<String> toAddNames;

    /**
     * This object keeps tracks of the names of the files that are being staged for removal
     */
    private HashSet<String> toRemoveNames;

    /**
     * Gets a repository object from a file
     * Returns null if the repository does not exist
     */
    public static Repository getRepo() {
        if (REPO.exists()) {
            return Utils.readObject(REPO, Repository.class);
        }
        return null;
    }

    /**
     * Saves the repository into a file
     */
    public void saveRepo() {
        Utils.writeObject(REPO, this);
    }

    public Repository() {
        // Create all the necessary folders
        GITLET_DIR.mkdir();
        BRANCHES_DIR.mkdir();
        STAGE_DIR.mkdir();
        TO_ADD_DIR.mkdir();
        TO_REMOVE_DIR.mkdir();
        COMMITS_DIR.mkdir();
        COMMIT_FILES_DIR.mkdirs();

        // Initialize all Hashsets and Hashmaps
        commitFiles = new HashMap<>();
        trackedFileNames = new HashSet<>();
        toAddNames = new HashSet<>();
        toRemoveNames = new HashSet<>();

        // Create an initial commit
        Commit initialCommit = new Commit();
        // Points HEAD to the initial commit
        HEAD = initialCommit.id;

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
    public void add(String fileName) {
        // Gets the file in the Current Working Directory with the given name
        File file = Utils.join(CWD, fileName);

        if (!file.exists()) {
            throw Utils.error("File does not exist.");
        }

        // Get file's SHA-1 ID
        String fileKey = Utils.getFileId(file);

        File stageFile = Utils.join(TO_ADD_DIR, fileName);

        // If the content of this file is already in the commit, remove it from the staging area.
        if (commitFiles.containsKey(fileKey)) {
            stageFile.delete();
            return;
        }

        // Copies the contents of file to stageFile
        Utils.copyFile(file, stageFile);
        // Add this file to toAddMap
        toAddNames.add(file.getName());
    }

    /**
     * Saves all the files in the staging area into COMMIT_FILES directory
     * A new commit is created
     */
    public void commit(String message) {
        // If there is no file in the staging area.
        if (Objects.requireNonNull(TO_ADD_DIR.list()).length == 0 && Objects.requireNonNull(TO_REMOVE_DIR.list()).length == 0) {
            throw Utils.error("No changes added to the commit");
        }

        Commit prevCommit = Commit.fromFile(HEAD);
        // A tree map with keys as name and values as sha-1 id
        // We get tracked files from the previous commit
        TreeMap<String, String> fileMap = prevCommit.fileMap;
        // Names of the files that are staged for addition
        File[] toAddFiles = TO_ADD_DIR.listFiles();
        File[] toRemoveFiles = TO_REMOVE_DIR.listFiles();

        // Adds all the files in the staging directory to fileMap
        for (File file : Objects.requireNonNull(toAddFiles)) {
            String fileId = Utils.getFileId(file);
            File commitFile = Utils.join(COMMIT_FILES_DIR, fileId);
            // Copies the file into COMMITS_DIR
            Utils.copyFile(file, commitFile);
            // Stores fileName as a key and fileId as a value
            fileMap.put(file.getName(), fileId);
            commitFiles.put(fileId, commitFile);
        }

        // Untrack all the files that are staged for removal
        for (File file : Objects.requireNonNull(toRemoveFiles)) {
            fileMap.remove(file.getName());
        }

        Commit newCommit = new Commit(message, fileMap, prevCommit.id);
        newCommit.saveCommit();

        // Clear all the files in the staging area
        Utils.clearDirectory(TO_ADD_DIR);
        Utils.clearDirectory(TO_REMOVE_DIR);

        // Clear all the names in the HashSets
        toAddNames.clear();
        toRemoveNames.clear();

        // Points HEAD to the latest commit in the branch
        HEAD = newCommit.id;
    }

    /**
     * Unstage the file if it is currently staged for addition
     * If it is tracked in the current commit, stage it for removal
     * and remove the file from the working directory
     */
    public void remove(String fileName) {
        // If the file is being staged for addition
        if (toAddNames.contains(fileName)) {
            File stagedFile = Utils.join(TO_ADD_DIR, fileName);
            // Remove the fileName from the list of file names that are being staged for addition
            toAddNames.remove(fileName);
            // Remove the file from the staging folder
            stagedFile.delete();
        }
        // If the file is tracked in the current commit
        else if (trackedFileNames.contains(fileName)) {
            File file = Utils.join(CWD, fileName);
            File toRemoveFile = Utils.join(TO_REMOVE_DIR, fileName);
            // Stage the file for removal
            Utils.copyFile(file, toRemoveFile);
            toRemoveNames.add(fileName);
            // Remove the file from the current working directory
            file.delete();
        } else {
            throw Utils.error("No reason to remove the file.");
        }
    }

    /**
     * A recursive method that ends when the current commit has no parent
     */
    private void log(String commitId) {
        File commitFile = Utils.join(COMMITS_DIR, commitId);
        // Turn the file into Commit object
        Commit commit = Utils.readObject(commitFile, Commit.class);
        commit.printInfo();

        // Base case (No more commit)
        if (commit.parent == null) {
            return;
        }
        // Continue recursion
        log(commit.parent);
    }

    /**
     * TODO: Implement log for merge
     * Starting from the current commit, displays info about each
     * commit backwards along the commit tree until the initial commit
     */
    public void log() {
        log(HEAD);
    }

    /**
     * Displays information of all the commits ever made
     */
    public void globalLog() {
        File[] commitFilesList = COMMIT_FILES_DIR.listFiles();
        for (File commitFile : Objects.requireNonNull(commitFilesList)) {
            Commit commit = Utils.readObject(commitFile, Commit.class);
            commit.printInfo();
        }
    }

    public void find() {

    }

    public void status() {
        // Header for branches
        System.out.println("=== Branches ===");

        String[] branchNames = BRANCHES_DIR.list();
        for (String branchName : branchNames) {
            // Add asterisk to the front if the branch is the active branch
            if (branchName.equals(ACTIVE_BRANCH)) {
                System.out.print("*");
            }
            System.out.println(branchName);
        }

        // Header for Staged Files
        System.out.println("=== Staged Files ===");
        for (String fileName : toAddNames) {
            System.out.println(fileName);
        }

        // Header for Removed Files
        System.out.println("=== Removed Files ===");
        for (String fileName : toRemoveNames) {
            System.out.println(fileName);
        }

        // Header for Modifications not Staged for commit
        System.out.println("=== Modifications Not Staged For Commit ===");
        // TODO: Bonus points

        // Header for Untracked Files
        System.out.println("=== Untracked Files ===");
        // TODO: Bonus points

    }

    /**
     * Takes the version of the file as it exists in the head commit,
     * and puts it in the working directory, overwriting the version of the file that’s already there if there is one.
     * The new version of the file is not staged.
     */
    public void checkoutFile(String fileName) {
        checkoutFile(HEAD, fileName);
    }

    /**
     * Takes the version of the file as it exists in the commit with the given id,
     * and puts it in the working directory, overwriting the version of the file that’s already there if there is one.
     * The new version of the file is not staged.
     */
    public void checkoutFile(String commitId, String fileName) {
        Commit commit = Commit.fromFile(commitId);
        String fileId = commit.fileMap.get(fileName);
        File commitFile = Utils.join(COMMIT_FILES_DIR, fileId);
        File CWDFile = Utils.join(CWD, fileName);
        Utils.copyFile(commitFile, CWDFile);
    }

    public void checkoutBranch(String branchName) {
    }

    public void branch(String branchName) {
        Branch newBranch = new Branch(branchName, HEAD);
        newBranch.saveBranch();
    }

    public void removeBranch() {

    }

    public void reset() {

    }

    public void merge() {

    }

    /**
     * Moves the HEAD pointer and change the ACTIVE_BRANCH
     */
    private void switchBranch(String branchName) {
        ACTIVE_BRANCH = branchName;
        HEAD = Branch.getBranchRef(branchName);
    }

}
