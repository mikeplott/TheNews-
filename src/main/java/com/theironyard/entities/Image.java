package com.theironyard.entities;

import org.springframework.stereotype.Controller;

import javax.persistence.*;

/**
 * Created by michaelplott on 10/29/16.
 */
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String origFilename;

    @ManyToOne
    User user;

    public Image() {
    }

    public Image(String filename, String origFilename, User user) {
        this.filename = filename;
        this.origFilename = origFilename;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOrigFilename() {
        return origFilename;
    }

    public void setOrigFilename(String origFilename) {
        this.origFilename = origFilename;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
