package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.TreeMap;

import static gitlet.Utils.join;

/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */


    /**
     * A pointer that points to the parent commit
     */
    String parent;

    /**
     * The id of this comment in the form of a strong from SHA-1 hash
     */
    String id;

    /**
     * The time this commit was created
     */
    Date timestamp;

    /**
     * The message of this Commit.
     */
    String message;

    /** A map of file names to ids */
    TreeMap<String, String> fileMap;

    // Initial commit
    public Commit() {
        // Creates a new id for this commit
        id = Utils.sha1(Utils.serialize(this));
        timestamp = new Date(0);
    }

    public Commit(String message, TreeMap<String, String> fileMap, String parentCommit) {
        // Creates a new id for this commit
        this.id = Utils.sha1(Utils.serialize(this));
        this.timestamp = new Date();
        this.message = message;
        this.fileMap = fileMap;
        this.parent = parentCommit;
    }

    /**
     * Given a commit id,  reads the commit object from a file
     * Getting a commit file does not need an instance, so this is a static method
     */
    public static Commit fromFile(String id) {
        File commitFile = Utils.join(Repository.COMMITS_DIR, id);
        return Utils.readObject(commitFile, Commit.class);
    }

    /**
     * Saves the current commit into a file
     */
    public void saveCommit() {
        File commitFile = Utils.join(Repository.COMMITS_DIR, id);
        Utils.writeObject(commitFile, this);
    }

    /** Prints the commit info with the format like below  */
    public void printInfo() {
        System.out.println("===");
        System.out.println("commit: " + id);
        System.out.println("Date: " + timestamp);
        System.out.println(message);
        System.out.println();
    }
}
