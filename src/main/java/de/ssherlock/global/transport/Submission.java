package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Represents a Submission DTO.
 *
 * @author Victor Vollmann
 */
public class Submission implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The id of the submission.
     */
    private long id;

    /**
     * The id of the exercise associated with this submission.
     */
    private long exerciseId;

    /**
     * The user who created the submission.
     */
    private String user;

    /**
     * The tutor associated with this submission.
     */
    private String tutor;

    /**
     * The CheckerResults of the submission.
     */
    private List<CheckerResult> checkerResults;

    /**
     * The files of the submission.
     */
    private List<SubmissionFile> submissionFiles;

    /**
     * The time of submission.
     */
    private Timestamp timestamp;

    /**
     * Whether submission passed all mandatory checkers.
     */
    private boolean passedCheckers;

    /**
     * Whether testate has been created.
     */
    private boolean testateCreated;

    /**
     * Instantiates a new Submission.
     */
    public Submission() {}

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets exercise id.
     *
     * @return the exercise id
     */
    public long getExerciseId() {
        return exerciseId;
    }

    /**
     * Sets exercise id.
     *
     * @param exerciseId the exercise id
     */
    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets tutor.
     *
     * @return the tutor
     */
    public String getTutor() {
        return tutor;
    }

    /**
     * Sets tutor.
     *
     * @param tutor the tutor
     */
    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    /**
     * Gets checker results.
     *
     * @return the checker results
     */
    public List<CheckerResult> getCheckerResults() {
        return checkerResults;
    }

    /**
     * Sets checker results.
     *
     * @param checkerResults the checker results
     */
    public void setCheckerResults(List<CheckerResult> checkerResults) {
        this.checkerResults = checkerResults;
    }

    /**
     * Gets submission files.
     *
     * @return the submission files
     */
    public List<SubmissionFile> getSubmissionFiles() {
        return submissionFiles;
    }

    /**
     * Sets submission files.
     *
     * @param submissionFiles the submission files
     */
    public void setSubmissionFiles(List<SubmissionFile> submissionFiles) {
        this.submissionFiles = submissionFiles;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Is passed checkers boolean.
     *
     * @return the boolean
     */
    public boolean isPassedCheckers() {
        return passedCheckers;
    }

    /**
     * Sets passed checkers.
     *
     * @param passedCheckers the passed checkers
     */
    public void setPassedCheckers(boolean passedCheckers) {
        this.passedCheckers = passedCheckers;
    }

    /**
     * Is testate created boolean.
     *
     * @return the boolean
     */
    public boolean isTestateCreated() {
        return testateCreated;
    }

    /**
     * Sets testate created.
     *
     * @param testateCreated the testate created
     */
    public void setTestateCreated(boolean testateCreated) {
        this.testateCreated = testateCreated;
    }
}
