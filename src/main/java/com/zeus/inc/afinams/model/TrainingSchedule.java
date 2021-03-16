package com.zeus.inc.afinams.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zeus.inc.afinams.dto.TrainingScheduleDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class TrainingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @JsonIgnore
    @ManyToMany(mappedBy = "schedules")
    private Set<Client> clients = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "schedules")
    private Set<Trainer> trainers = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    public void setTime() {
        this.created = created == null ? new Date(System.currentTimeMillis()) : created;
        this.updated = new Date(System.currentTimeMillis());
    }

    public TrainingSchedule() {
        setTime();
    }

    public TrainingSchedule(Long id, Date time, Set<Client> clients, Set<Trainer> trainers) {
        setTime();
        this.id = id;
        this.time = time;
        this.clients = clients;
        this.trainers = trainers;
    }

    public TrainingScheduleDTO toDTO() {
        return new TrainingScheduleDTO(id, time, clients, trainers, created, updated);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public Set<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(Set<Trainer> trainers) {
        this.trainers = trainers;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }
}
