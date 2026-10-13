package com.skillsphere.dto;

public class StudentDto {
    private Long id;
    private Long userId;
    private String fullName;
    private String email;
    private String bio;
    private String qualification;
    private String avatarUrl;

    public StudentDto() {}

    public StudentDto(Long id, Long userId, String fullName, String email, String bio, String qualification, String avatarUrl) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.bio = bio;
        this.qualification = qualification;
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

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long userId;
        private String fullName;
        private String email;
        private String bio;
        private String qualification;
        private String avatarUrl;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder bio(String bio) { this.bio = bio; return this; }
        public Builder qualification(String qualification) { this.qualification = qualification; return this; }
        public Builder avatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; return this; }

        public StudentDto build() {
            return new StudentDto(id, userId, fullName, email, bio, qualification, avatarUrl);
        }
    }
}
