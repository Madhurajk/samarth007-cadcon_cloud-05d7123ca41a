package com.cadcon.cloud.dbimpl.insertions;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.jdbc.core.JdbcTemplate;

public class MockTestResultInsertions {

    private static String QUERY =
            "insert into student_test_progress(student,test,marks,max,created_at,time_taken) values(?,?,?,?,?,?)";

    private long studentId;
    private long testId;
    private int marksRecieved;
    private int maxMarks;
    private long createdAt;
    private long timeTaken;
    private JdbcTemplate template;

    public MockTestResultInsertions(long studentId, long testId, int marksRecieved, int maxMarks, long timeTaken,
            JdbcTemplate template) {
        this.studentId = studentId;
        this.testId = testId;
        this.marksRecieved = marksRecieved;
        this.maxMarks = maxMarks;
        this.createdAt = Instant.now().toEpochMilli();
        this.timeTaken = timeTaken;
        this.template = template;
    }

    public void insert() {
        template.update(QUERY,
                Arrays.asList(studentId, testId, marksRecieved, maxMarks, createdAt, timeTaken).toArray());
    }
}
