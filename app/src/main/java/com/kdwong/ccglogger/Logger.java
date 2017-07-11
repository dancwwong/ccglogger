package com.kdwong.ccglogger;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by danwong on 6/25/17.
 */

public class Logger {

    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private static Logger instance;

    static void initialize(FirebaseUser user) {
        instance = new Logger(user);
    }

    public static Logger getInstance() {
        return instance;
    }

    Logger(FirebaseUser user) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.user = user;
    }

    public void logStudent() {
        String username = usernameFromEmail(user.getEmail());
        Student student = new Student(username, user.getEmail());
        mDatabase.child("students").child(user.getUid()).setValue(student);
    }

    public void logHours(int hours) {
        mDatabase.child("students").child(user.getUid()).child("practiceHours").setValue(hours);
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
}
