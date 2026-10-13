package com.skillsphere.dto;

public class InstructorDto {
    private Long id;
    private Long userId;
    private String fullName;
    private String email;
    private String title;
    private String bio;
    private String expertise;
    private String organization;
    private String avatarUrl;

    public InstructorDto() {}

    public InstructorDto(Long id, Long userId, String fullName, String email, String title, String bio, String expertise, String organization, String avatarUrl) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.title = title;
        this.bio = bio;
        this.expertise = expertise;
        this.organization = organization;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

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
        private Long userId;
        private String fullName;
        private String email;
        private String title;
        private String bio;
        private String expertise;
        private String organization;
        private String avatarUrl;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder bio(String bio) { this.bio = bio; return this; }
        public Builder expertise(String expertise) { this.expertise = expertise; return this; }
        public Builder organization(String organization) { this.organization = organization; return this; }
        public Builder avatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; return this; }

        public InstructorDto build() {
            return new InstructorDto(id, userId, fullName, email, title, bio, expertise, organization, avatarUrl);
        }
    }
}
