package com.zeus.inc.afinams.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zeus.inc.afinams.dto.PersonDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String login;

    private String password;

    private Double profit;

    private String address;

    private String about;

    private String avatar; // path to photo

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "t_id_ts_id",
            joinColumns = {@JoinColumn(name = "t_id")},
            inverseJoinColumns = {@JoinColumn(name = "ts_id")}
    )
    private Set<TrainingSchedule> schedules = new HashSet<>();

    public void setTime() {
        this.created = created == null ? new Date(System.currentTimeMillis()) : created;
        this.updated = new Date(System.currentTimeMillis());
    }

    public Trainer() {
        setTime();
    }

    public Trainer(Long id) {
        setTime();
        this.id = id;
    }

    public Trainer(Long id, String firstName, String lastName, String phoneNumber,
                   String login, String password, Double profit, String address, String about, String avatar) {
        setTime();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
        this.profit = profit;
        this.address = address;
        this.about = about;
        this.avatar = avatar;
    }

    public PersonDTO toDTO() {
        return new PersonDTO(id, login, password, firstName, lastName, phoneNumber, profit, null, address, about, avatar, null, null, created, updated);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<TrainingSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<TrainingSchedule> schedules) {
        this.schedules = schedules;
    }

    public void addSchedule(TrainingSchedule schedule) {
        schedules.add(schedule);
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
