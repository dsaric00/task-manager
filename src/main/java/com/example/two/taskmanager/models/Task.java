package com.example.two.taskmanager.models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name ="task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

   //Povezati user tablicu s ovom tablicom Many to one
    @ManyToOne
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @Column(name ="title")
    private String title;

    @Column(name ="description")
    private String description;

    @Column(name ="start_date")
    private LocalDate StartDate;

    @Column(name="end_date")
    private LocalDate EndDate;

    @Enumerated(EnumType.STRING)
    @Column Status status;

    public Task() {

    }

    public enum Status{
        STARTED,
        SUSPENDED,
        IN_PROGRESS,
        COMPLETED
    }

    public Task(Long Id,String title, String description, LocalDate StartDate, LocalDate EndDate, Status status){
        this.Id=Id;
        this.title=title;
        this.description=description;
        this.StartDate=StartDate;
        this.EndDate=EndDate;
        this.status=status;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
