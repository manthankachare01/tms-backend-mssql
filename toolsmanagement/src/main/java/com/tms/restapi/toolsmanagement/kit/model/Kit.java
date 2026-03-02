package com.tms.restapi.toolsmanagement.kit.model;

import com.tms.restapi.toolsmanagement.tools.model.Tool;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kits")
public class Kit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kit_id", unique = true, length = 50)
    private String kitId;        // e.g. KIT-001

    @Column(name = "kit_name", length = 100)
    private String kitName;

    @Column(name = "qualification_level", length = 100)
    private String qualificationLevel;

    @Column(name = "training_name", length = 100)
    private String trainingName;

    @Column(name = "location", length = 100)
    private String location;

    // Remaining quantity for the kit (availability count)
    @Column(nullable = false)
    private Integer availability = 1;

    @Column(name = "last_borrowed_by", length = 100)
    private String lastBorrowedBy;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "kit_condition", length = 50)
    private String condition;

    @ManyToMany
    @JoinTable(
            name = "kit_tools",
            joinColumns = @JoinColumn(name = "kit_id_fk"),
            inverseJoinColumns = @JoinColumn(name = "tool_id_fk")
    )
    private List<Tool> tools = new ArrayList<>();

    @OneToMany(mappedBy = "kit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KitAggregate> aggregates = new ArrayList<>();

    // Track who created this kit and when
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKitId() {
        return kitId;
    }

    public void setKitId(String kitId) {
        this.kitId = kitId;
    }

    public String getKitName() {
        return kitName;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

    public String getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(String qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public String getLastBorrowedBy() {
        return lastBorrowedBy;
    }

    public void setLastBorrowedBy(String lastBorrowedBy) {
        this.lastBorrowedBy = lastBorrowedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public List<KitAggregate> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<KitAggregate> aggregates) {
        this.aggregates = aggregates;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
