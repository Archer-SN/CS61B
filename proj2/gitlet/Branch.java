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
     * Creates a branch with a reference to the latest commit
     */
    public Branch(String name, String ref) {
        this.name = name;
        this.ref = ref;
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
     * Find the latest commit that is both in the current and the given branch
     */
    public String findLatestCommonAncestor(Branch branch) {
        return Commit.findLatestCommonAncestor(this.ref, branch.ref);
    }


    /**
     * Gets the file version of the branch
     */
    public File getBranchFile() {
        return branchFile;
    }


}
