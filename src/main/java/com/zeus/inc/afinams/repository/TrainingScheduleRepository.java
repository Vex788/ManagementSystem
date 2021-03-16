package com.zeus.inc.afinams.repository;

import com.zeus.inc.afinams.model.TrainingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TrainingScheduleRepository extends JpaRepository<TrainingSchedule, Long> {

    ArrayList<TrainingSchedule> findAll();
}
