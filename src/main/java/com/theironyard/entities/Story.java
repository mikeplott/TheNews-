package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by michaelplott on 10/29/16.
 */
@Entity
@Table(name = "stories")
public class Story {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String headline;

    @Column(nullable = false)
    private String link;

    @Column
    private int real;

    @Column
    private int fake;

    @ManyToOne
    private User user;

    @Transient
    public boolean isMine;

    public Story() {
    }

    public Story(String headline, String link, int real, int fake, User user) {
        this.headline = headline;
        this.link = link;
        this.real = real;
        this.fake = fake;
        this.user = user;
    }

    public Story(String headline, String link, int real, int fake) {
        this.headline = headline;
        this.link = link;
        this.real = real;
        this.fake = fake;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getReal() {
        return real;
    }

    public void setReal(int real) {
        this.real = real;
    }

    public int getFake() {
        return fake;
    }

    public void setFake(int fake) {
        this.fake = fake;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
