package com.elevana_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Generated;

@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "material_id_generator"
    )
    @SequenceGenerator(
        name = "material_id_generator",
        sequenceName = "material_id_seq",
        allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String s3Url;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private String mentorId;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(updatable = false)
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    @JsonBackReference
    private Classroom classroom;

    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }

    // --- Getters ---
    @Generated
    public Long getId() {
        return id;
    }

    @Generated
    public String getDisplayName() {
        return displayName;
    }

    @Generated
    public String getS3Url() {
        return s3Url;
    }

    @Generated
    public String getFileType() {
        return fileType;
    }

    @Generated
    public String getMentorId() {
        return mentorId;
    }

    @Generated
    public Long getFileSize() {
        return fileSize;
    }

    @Generated
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    @Generated
    public Classroom getClassroom() {
        return classroom;
    }

    // --- Setters ---
    @Generated
    public void setId(final Long id) {
        this.id = id;
    }

    @Generated
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    @Generated
    public void setS3Url(final String s3Url) {
        this.s3Url = s3Url;
    }

    @Generated
    public void setFileType(final String fileType) {
        this.fileType = fileType;
    }

    @Generated
    public void setMentorId(final String mentorId) {
        this.mentorId = mentorId;
    }

    @Generated
    public void setFileSize(final Long fileSize) {
        this.fileSize = fileSize;
    }

    @Generated
    public void setUploadedAt(final LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Generated
    public void setClassroom(final Classroom classroom) {
        this.classroom = classroom;
    }

    // --- Equality ---
    @Generated
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Material)) return false;
        Material other = (Material) o;
        if (!other.canEqual(this)) return false;

        return java.util.Objects.equals(this.id, other.id)
            && java.util.Objects.equals(this.displayName, other.displayName)
            && java.util.Objects.equals(this.s3Url, other.s3Url)
            && java.util.Objects.equals(this.fileType, other.fileType)
            && java.util.Objects.equals(this.mentorId, other.mentorId)
            && java.util.Objects.equals(this.fileSize, other.fileSize)
            && java.util.Objects.equals(this.uploadedAt, other.uploadedAt)
            && java.util.Objects.equals(this.classroom, other.classroom);
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof Material;
    }

    @Generated
    public int hashCode() {
        return java.util.Objects.hash(id, displayName, s3Url, fileType, mentorId, fileSize, uploadedAt, classroom);
    }

    @Generated
    public String toString() {
        return "Material(id=" + id +
               ", displayName=" + displayName +
               ", s3Url=" + s3Url +
               ", fileType=" + fileType +
               ", mentorId=" + mentorId +
               ", fileSize=" + fileSize +
               ", uploadedAt=" + uploadedAt +
               ", classroom=" + classroom + ")";
    }
}
