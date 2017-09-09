package com.kdwong.ccglogger;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class that represents the student who's also the current user.
 */
public class Student {

    private static Student instance;

    private DatabaseReference database;
    private FirebaseUser user;

    private String name;
    private String email;
    private long practiceStreak;
    private Date lastPracticeDate;

    static void initialize(FirebaseUser user) {
        instance = new Student(user);
    }

    public static Student me() {
        return instance;
    }

    // This is needed for Firebase to write student as a single transaction.
    public Student() {
    }

    public Student(FirebaseUser user) {
        database = FirebaseDatabase.getInstance().getReference();
        this.user = user;
        this.name = usernameFromEmail(user.getEmail());
        this.email = user.getEmail();

        DatabaseReference streakReference =
                database.child("students").child(user.getUid()).child("practiceStreak");
        streakReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long practiceStreakData = (Long) dataSnapshot.getValue();
                if (practiceStreakData != null) {
                    practiceStreak = (Long) practiceStreakData;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("An unexpected error occurred");
            }
        });

        DatabaseReference lastPracticeDateReference =
                database.child("students").child(user.getUid()).child("lastPracticeDate");
        lastPracticeDateReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dateString = (String) dataSnapshot.getValue();
                if (dateString == null) {
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    lastPracticeDate = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    System.err.println("An unexpected error occurred");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("An unexpected error occurred");
            }
        });
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getPracticeStreak() {
        return practiceStreak;
    }

    public Date getLastPracticeDate() {
        return lastPracticeDate;
    }

    public void setPracticeStreak(int practiceStreak) {
        this.practiceStreak = practiceStreak;
    }

    public void setLastPracticeDate(Date lastPracticeDate) {
        this.lastPracticeDate = lastPracticeDate;
    }

    public void incrementPracticeStreak() {
        practiceStreak++;
    }

    public void writeToDatabase() {
        DatabaseReference studentRef = database.child("students").child(user.getUid());
        studentRef.child("name").setValue(name);
        studentRef.child("email").setValue(email);
        studentRef.child("practiceStreak").setValue(practiceStreak);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        studentRef.child("lastPracticeDate").setValue(dateFormat.format(lastPracticeDate));
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
}
