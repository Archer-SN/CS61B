package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;


/**
 * Represents a gitlet repository.
 * does at a high level.
 *
 * @author Archer-SN
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
    private String ACTIVE_BRANCH;

    /**
     * A reference to the current commit that we are at
     */
    private String HEAD;

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
     * This object contains SHA-1 id of all the files in the COMMIT_FILES_FOLDER
     */
    private HashSet<String> committedFilesId;


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
        committedFilesId = new HashSet<>();
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
        switchBranch(masterBranch);
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
        if (committedFilesId.contains(fileKey)) {
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
    public void commit(String message, TreeMap<String, String> fileIdMap) {
        // If there is no file in the staging area.
        if (Objects.requireNonNull(TO_ADD_DIR.list()).length == 0 &&
                Objects.requireNonNull(TO_REMOVE_DIR.list()).length == 0) {
            System.out.println("No changes added to the commit.");
            return;
        }

        Commit prevCommit = Commit.fromFile(HEAD);
        // A tree map with keys as name and values as sha-1 id
        // We get tracked files from the previous commit
        TreeMap<String, String> fileMap = prevCommit.fileIdMap;
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
            committedFilesId.add(fileId);
        }

        // Untrack all the files that are staged for removal
        for (File file : Objects.requireNonNull(toRemoveFiles)) {
            fileMap.remove(file.getName());
        }

        Commit newCommit = new Commit(message, fileMap, prevCommit.id);
        newCommit.saveCommit();

        clearStagingArea();

        // Points HEAD to this new commit
        HEAD = newCommit.id;

        // Adds this new commit to the branch history and set the new latest commit
        Branch activeBranch = Branch.getBranch(ACTIVE_BRANCH);
        activeBranch.addCommit(newCommit.id);
    }

    public Commit commit(String message) {
        Commit prevCommit = Commit.fromFile(HEAD);
        return commit(message, prevCommit.fileIdMap);
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
        File[] commitList = COMMITS_DIR.listFiles();
        for (File commitFile : Objects.requireNonNull(commitList)) {
            Commit commit = Utils.readObject(commitFile, Commit.class);
            commit.printInfo();
        }
    }

    /**
     * Prints out the ids of all commits that have the given commit message, one per line.
     * If there are multiple such commits, it prints the ids out on separate lines.
     */
    public void find(String message) {
        // Get the list of all commits
        File[] commitList = COMMITS_DIR.listFiles();
        // looping through all the commits
        for (File commitFile : Objects.requireNonNull(commitList)) {
            Commit commit = Utils.readObject(commitFile, Commit.class);
            // Prints the commit id if its message match with the given message
            if (commit.message.equals(message)) {
                System.out.println(commit.id);
            }
        }
    }

    public void status() {
        // Header for branches
        System.out.println("=== Branches ===");

        for (String branchName : Branch.branchNames()) {
            // Add asterisk to the front if the branch is the active branch
            if (branchName.equals(ACTIVE_BRANCH)) {
                System.out.print("*");
            }
            System.out.println(branchName);
        }
        // Empty Line
        System.out.println();
        // Header for Staged Files
        System.out.println("=== Staged Files ===");
        for (String fileName : toAddNames) {
            System.out.println(fileName);
        }
        // Empty Line
        System.out.println();

        // Header for Removed Files
        System.out.println("=== Removed Files ===");
        for (String fileName : toRemoveNames) {
            System.out.println(fileName);
        }
        // Empty Line
        System.out.println();

        // Header for Modifications not Staged for commit
        System.out.println("=== Modifications Not Staged For Commit ===");

        // Empty Line
        System.out.println();

        // Header for Untracked Files
        System.out.println("=== Untracked Files ===");

        // Empty Line
        System.out.println();
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
        String fileId = commit.fileIdMap.get(fileName);
        File commitFile = Utils.join(COMMIT_FILES_DIR, fileId);
        File CWDFile = Utils.join(CWD, fileName);
        Utils.copyFile(commitFile, CWDFile);
    }

    /**
     * Takes all files in the new commit, and puts them in the working directory,
     * overwriting the versions of the files that are already there if they exist
     */
    public void checkoutCommit(String commitId) {
        Commit newCommit = Commit.fromFile(commitId);
        // Replace files in CWD with files in commit_files
        for (Map.Entry<String, String> entry : newCommit.fileIdMap.entrySet()) {
            File CWDFile = Utils.join(CWD, entry.getKey());
            File committedFile = Utils.join(COMMIT_FILES_DIR, entry.getValue());
            Utils.copyFile(CWDFile, committedFile);
        }

        // Removing files that are tracked in the current commit, but not tracked in the new commit.
        Commit headCommit = Commit.fromFile(HEAD);
        for (String fileName : headCommit.fileIdMap.keySet()) {
            if (!newCommit.fileIdMap.containsKey(fileName)) {
                File CWDFile = Utils.join(CWD, fileName);
                CWDFile.delete();
            }
        }
        clearStagingArea();

        HEAD = commitId;
    }

    public void checkoutBranch(String branchName) {
        // The latest commit in the branch
        Branch targetBranch = Branch.getBranch(branchName);
        if (targetBranch.ref == null) {
            System.out.println("No such branch exists.");
            return;
        }
        checkoutCommit(targetBranch.ref);
        switchBranch(targetBranch);
    }

    public void branch(String branchName) {
        Branch newBranch = new Branch(branchName, HEAD);
        newBranch.saveBranch();

        File branchFile = newBranch.getBranchFile();
    }

    public void removeBranch(String branchName) {
        if (branchName.equals(ACTIVE_BRANCH)) {
            throw Utils.error("Cannot remove the current branch.");
        }
        File branchFile = Utils.join(BRANCHES_DIR, branchName);
        if (Branch.exists(branchName)) {
            branchFile.delete();
        } else {
            throw Utils.error("A branch with that name does not exist.");
        }
    }

    /**
     * Checks out all the files tracked by the given commit.
     * Removes tracked files that are not present in that commit.
     * Also moves the current branch’s head to that commit node
     */
    public void reset(String commitId) {
        Branch branch = Branch.getBranch(ACTIVE_BRANCH);
        checkoutCommit(commitId);
        branch.setRef(commitId);
    }

    /**
     * Merges files from the given branch into the current branch.
     */
    public void merge(String branchName) {
        Branch givenBranch = Branch.getBranch(branchName);
        Branch activeBranch = Branch.getBranch(ACTIVE_BRANCH);
        String splitPoint = Branch.findLatestCommonAncestor(givenBranch, activeBranch);
        // Do nothing if the split point is equal to the latest commit in the given branch
        if (givenBranch.ref.equals(splitPoint)) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        }
        // If the split point is the current active branch, checkout to the given branch
        else if (activeBranch.ref.equals(splitPoint)) {
            checkoutBranch(branchName);
            System.out.println("Current branch fast-forwarded.");
            return;
        }

        Commit givenCommit = Commit.fromFile(givenBranch.ref);
        Commit currentCommit = Commit.fromFile(activeBranch.ref);
        Commit splitPointCommit = Commit.fromFile(splitPoint);

        // A set of name of files that will be checked
        HashSet<String> toCheckFileNames = new HashSet<String>(givenCommit.fileIdMap.keySet());
        toCheckFileNames.addAll(currentCommit.fileIdMap.keySet());
        toCheckFileNames.addAll(splitPointCommit.fileIdMap.keySet());

        // Will be passed into commit()
        TreeMap<String, String> currentCommitFileIdMap = (TreeMap<String, String>) currentCommit.fileIdMap;

        boolean hasConflict = false;

        for (String fileName : toCheckFileNames) {
            // Get the sha-1 id of each file
            String givenCommitFileId = givenCommit.fileIdMap.get(fileName);
            String currentCommitFileId = currentCommit.fileIdMap.get(fileName);
            String splitPointCommitFileId = splitPointCommit.fileIdMap.get(fileName);
            // Getting different versions of the file from 3 different commits
            File givenCommitFile = Utils.join(COMMIT_FILES_DIR, givenCommitFileId);
            File currentCommitFile = Utils.join(COMMIT_FILES_DIR, currentCommitFileId);
            File splitPointCommitFile = Utils.join(COMMIT_FILES_DIR, splitPointCommitFileId);
            // Case 1
            if (!givenCommitFileId.equals(splitPointCommitFileId) && splitPointCommitFileId.equals(currentCommitFileId)) {
                checkoutFile(givenCommit.id, fileName);
                add(fileName);
            }
            // Case 2
            else if (!currentCommitFileId.equals(splitPointCommitFileId) && givenCommitFileId.equals(splitPointCommitFileId)) {
                continue;
            }
            // Case 3
            else if (givenCommitFileId.equals(currentCommitFileId)) {
                continue;
            }
            // Case 4
            else if (!splitPointCommitFile.exists() && !givenCommitFile.exists() && currentCommitFile.exists()) {
                continue;
            }
            // Case 5
            else if (!splitPointCommitFile.exists() && givenCommitFile.exists() && !currentCommitFile.exists()) {
                checkoutFile(fileName, givenCommit.id);
                add(fileName);
            }
            // Case 6
            else if (splitPointCommitFile.exists() && currentCommitFileId.equals(splitPointCommitFileId) && !givenCommitFile.exists()) {
                remove(fileName);
                currentCommitFileIdMap.remove(fileName);
            }
            // Case 7
            else if (splitPointCommitFile.exists() && givenCommitFileId.equals(splitPointCommitFileId) && !currentCommitFile.exists()) {
                continue;
            }
            // Case 8
            else if (!givenCommitFileId.equals(currentCommitFileId)) {
                hasConflict = true;
                File cwdFile = Utils.join(CWD, fileName);
                Utils.writeContents(cwdFile, "<<<<<<< HEAD\n", Utils.readContents(currentCommitFile), "=======\n", Utils.readContents(givenCommitFile), ">>>>>>>\n");
                add(fileName);
            }
        }
        commit(String.format("Merged %s into %s.", givenBranch.name, activeBranch.name), currentCommitFileIdMap);
        if (hasConflict) {
            System.out.println("Encountered a merge conflict.");
        }
    }

    /**
     * Moves the HEAD pointer and change the ACTIVE_BRANCH
     */
    private void switchBranch(Branch newBranch) {
        ACTIVE_BRANCH = newBranch.name;
        HEAD = newBranch.ref;
    }

    /**
     * Remove all the files in the staging area
     */
    private void clearStagingArea() {
        Utils.clearDirectory(TO_ADD_DIR);
        Utils.clearDirectory(TO_REMOVE_DIR);
        toAddNames.clear();
        toRemoveNames.clear();
    }
}
