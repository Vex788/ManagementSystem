package com.zeus.inc.afinams.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zeus.inc.afinams.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date subcriptionUpdated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;

    private Double profit;

    private Integer numberOfPaidWorkouts;

    private Subscription subscription;

    private String address;

    private String about;

    private String avatar;

    public PersonDTO(Long id,
                     String login, String password, String firstName, String lastName, String phoneNumber, Double profit,
                     Integer numberOfPaidWorkouts, String address, String about, String avatar, Subscription subscription,
                     Date subscriptionUpdated, Date created, Date updated) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.subcriptionUpdated = subscriptionUpdated;
        this.profit = profit;
        this.numberOfPaidWorkouts = numberOfPaidWorkouts;
        this.about = about;
        this.avatar = avatar;
        this.address = address;
        this.subscription = subscription;
        this.created = created;
        this.updated = updated;
    }

    public Client fromClientDTO() {
        return new Client(id, firstName, lastName, phoneNumber, login, password, numberOfPaidWorkouts, address, about, avatar, subscription);
    }

    public Admin fromAdminDTO() {
        return new Admin(id, firstName, lastName, phoneNumber, login, password, profit, address, about, avatar, null);
    }

    public Trainer fromTrainerDTO() {
        return new Trainer(id, firstName, lastName, phoneNumber, login, password, profit, address, about, avatar);
    }

    public TrainingSchedule fromTrainingSchuduleDTO() {
        return new TrainingSchedule();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreated() {
        return created != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(created) : null;
    }

    public void setCreated(Date created) {
        //this.created = created;
        // ignore
    }

    public String getUpdated() {
        return updated != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(updated) : null;
    }

    public void setUpdated(Date updated) {
        //this.updated = updated;
        // ignore
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Integer getNumberOfPaidWorkouts() {
        return numberOfPaidWorkouts;
    }

    public void setNumberOfPaidWorkouts(Integer numberOfPaidWorkouts) {
        this.numberOfPaidWorkouts = numberOfPaidWorkouts;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Date getSubcriptionUpdated() {
        return subcriptionUpdated;
    }

    public void setSubcriptionUpdated(Date subcriptionUpdated) {
        this.subcriptionUpdated = subcriptionUpdated;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
