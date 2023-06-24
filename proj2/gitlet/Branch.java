package gitlet;

import java.io.Serializable;
import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;

/**
 * Represent gitlet branch object
 */
public class Branch implements Serializable {

    /**
     * The branch's name
     */
    String name;
    /**
     * A reference to the latest commit in the branch
     */
    String ref;

    /**
     * The branch file
     */
    File branchFile;


    /**
     * An array of all the commits that are made in this branch
     */
    ArrayDeque<String> commitHistory;

    /**
     * Number of total commits in the branch
     */
    int branchSize;

    /**
     * Creates a branch with a reference to the latest commit
     */
    public Branch(String name, String ref) {
        this.name = name;
        this.ref = ref;
        this.branchFile = Utils.join(Repository.BRANCHES_DIR, name);
    }

    /**
     * Creates a branch with a reference to the latest commit
     * Copies the commit history from the previous branch
     */
    public Branch(String name, String ref, Branch previousBranch) {
        this.name = name;
        this.ref = ref;
        this.commitHistory = previousBranch.commitHistory.clone();
        this.branchFile = Utils.join(Repository.BRANCHES_DIR, name);
    }

    // Saves the current branch into branches directory
    public void saveBranch() {
        File branchFile = Utils.join(Repository.BRANCHES_DIR, name);
        Utils.writeObject(branchFile, this);
    }

    /**
     * Given a branch name return a Branch object
     */
    public static Branch getBranch(String branchName) {
        File branchFile = Utils.join(Repository.BRANCHES_DIR, branchName);
        return Utils.readObject(branchFile, Branch.class);
    }

    /**
     * Get the latest commit in the branch id
     */
    public static String getBranchRef(String branchName) {
        Branch branch = getBranch(branchName);
        return branch.ref;
    }


    /**
     * Finds the latest common ancestor between two branches
     */
    public static String findLatestCommonAncestor(String branch1Name, String branch2Name) {
        Branch branch1 = getBranch(branch1Name);
        Branch branch2 = getBranch(branch2Name);
    }

    /**
     * Gets the file version of the branch
     */
    public File getBranchFile() {
        return branchFile;
    }


    /**
     * Add the commit to the commit history
     * Set the latest commit in the branch to be this new commit
     */
    public void addCommit(String commitId) {
        commitHistory.put(commitId, commitHistory.size());
        setRef(commitId);
    }


    /**
     * Change the latest commit in the branch
     */
    public void setRef(String commitId) {
        ref = commitId;
        int newRefIndex = commitHistory.get(commitId);
    }


}
