package com.example.demo.entities;

import javax.persistence.*;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @Column(nullable = false)
    private String noteText;

    public Note(AppUser user, String noteText) {
        this.user = user;
        this.noteText = noteText;
    }

    public Note() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppUser getUser() {
        return null;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}
