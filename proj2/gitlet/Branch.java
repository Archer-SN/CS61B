package gitlet;

import java.io.Serializable;
import java.io.File;

/** Represent gitlet branch object */
public class Branch implements Serializable {

    /** The branch's name */
    String name;
    /** A reference to the latest commit in the branch */
    String ref;
    public Branch(String name, String ref) {
        this.name = name;
        this.ref = ref;
    }

    public void saveBranch() {

    }

    public Branch getBranchObject() {

    }

    /** Get the latest commit in the branch id */
    public static String getBranchRef(String branchName) {
        File branchFile = Utils.join(Repository.BRANCHES_DIR, branchName);

    }


}
