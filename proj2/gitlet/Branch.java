package gitlet;

import java.io.Serializable;
import java.io.File;

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
     * The id of the commit that is a split point
     * A split point is created if a new branch is created.
     */
    String splitPoint;


    /**
     * Creates a branch with a reference to the latest commit
     */
    public Branch(String name, String ref) {
        this.name = name;
        this.ref = ref;
        this.splitPoint = ref;
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
     * Gets the file version of the branch
     */
    public File getBranchFile() {
        return branchFile;
    }


    /**
     * Given a split point id, make the current split point equal to this id.
     */
    public void setSplitPoint(String splitPointId) {
        splitPoint = splitPointId;
    }


    /** Change the latest commit in the branch */
    public void setRef(String commitId) {
        ref = commitId;
    }

}
