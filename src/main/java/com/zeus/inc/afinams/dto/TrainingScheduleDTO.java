package com.zeus.inc.afinams.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zeus.inc.afinams.model.Client;
import com.zeus.inc.afinams.model.Trainer;
import com.zeus.inc.afinams.model.TrainingSchedule;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class TrainingScheduleDTO {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private List<Long> clients = new ArrayList<>();

    private List<Long> trainers = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;

    public TrainingScheduleDTO(Long id, Date time, Set<Client> clients, Set<Trainer> trainers, Date created, Date updated) {
        this.id = id;
        this.time = time;
        clients.stream().forEach(c -> this.clients.add(c.getId()));
        trainers.stream().forEach(t -> this.trainers.add(t.getId()));
        this.created = created;
        this.updated = updated;
    }

    public TrainingSchedule fromDTO() {
        Set<Client> clients = new HashSet<>();
        Set<Trainer> trainers = new HashSet<>();

        this.clients.stream().forEach(id -> clients.add(new Client(id)));
        this.trainers.stream().forEach(id -> trainers.add(new Trainer(id)));

        return new TrainingSchedule(id, time, clients, trainers);
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

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Long> getClients() {
        return clients;
    }

    public void setClients(List<Long> clients) {
        this.clients = clients;
    }

    public List<Long> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Long> trainers) {
        this.trainers = trainers;
    }
}
