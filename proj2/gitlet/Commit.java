package gitlet;

// TODO: any imports you need here

import java.util.Date; // TODO: You'll likely use this in this class
import java.util.Formatter;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** A pointer that points to the parent commit */
    private Commit parent;

    /** The id of this comment in the form of a strong from SHA-1 hash */
    public String id;

    /** The time this commit was created */
    private Date timestamp;

    /** The message of this Commit. */
    private String message;

    /** A collection of file ids in the commit */
    private String[] fileIds;

    // Initial commit
    public Commit() {
        // Creates a new id for this commit
        id = Utils.sha1(this);
        timestamp = new Date(0);
    }

    public Commit(String msg, String[] fIds) {
        // Creates a new id for this commit
        id = Utils.sha1(this);
        timestamp = new Date();
        message = msg;
        fileIds = fIds;
    }
}
