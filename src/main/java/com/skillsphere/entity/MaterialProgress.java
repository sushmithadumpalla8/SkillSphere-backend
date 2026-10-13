package com.skillsphere.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"enrollment_id", "material_id"})
})
public class MaterialProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private LearningMaterial material;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    public MaterialProgress() {}

    public MaterialProgress(Long id, Enrollment enrollment, LearningMaterial material, LocalDateTime completedAt) {
        this.id = id;
        this.enrollment = enrollment;
        this.material = material;
        this.completedAt = completedAt;
    }

    @PrePersist
    protected void onCreate() {
        if (this.completedAt == null) {
            this.completedAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }

    public LearningMaterial getMaterial() { return material; }
    public void setMaterial(LearningMaterial material) { this.material = material; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Enrollment enrollment;
        private LearningMaterial material;
        private LocalDateTime completedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder enrollment(Enrollment enrollment) { this.enrollment = enrollment; return this; }
        public Builder material(LearningMaterial material) { this.material = material; return this; }
        public Builder completedAt(LocalDateTime completedAt) { this.completedAt = completedAt; return this; }

        public MaterialProgress build() {
            return new MaterialProgress(id, enrollment, material, completedAt);
        }
    }
}
