package com.skillsphere.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String expertise;

    private String organization;

    private String avatarUrl;

    public Instructor() {}

    public Instructor(Long id, User user, String title, String bio, String expertise, String organization, String avatarUrl) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.bio = bio;
        this.expertise = expertise;
        this.organization = organization;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getExpertise() { return expertise; }
    public void setExpertise(String expertise) { this.expertise = expertise; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private User user;
        private String title;
        private String bio;
        private String expertise;
        private String organization;
        private String avatarUrl;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder user(User user) { this.user = user; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder bio(String bio) { this.bio = bio; return this; }
        public Builder expertise(String expertise) { this.expertise = expertise; return this; }
        public Builder organization(String organization) { this.organization = organization; return this; }
        public Builder avatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; return this; }

        public Instructor build() {
            return new Instructor(id, user, title, bio, expertise, organization, avatarUrl);
        }
    }
}
