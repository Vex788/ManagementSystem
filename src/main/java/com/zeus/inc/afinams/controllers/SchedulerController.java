package com.zeus.inc.afinams.controllers;

import com.zeus.inc.afinams.dto.TrainingScheduleDTO;
import com.zeus.inc.afinams.message.Message;
import com.zeus.inc.afinams.model.Client;
import com.zeus.inc.afinams.model.Trainer;
import com.zeus.inc.afinams.model.TrainingSchedule;
import com.zeus.inc.afinams.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * This method update training schedule object.
     *
     * @param scheduleDTO
     * @param request
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<Message> onUpdate(@RequestBody TrainingScheduleDTO scheduleDTO, HttpServletRequest request) {
        try {
            if (scheduleDTO != null && scheduleDTO.getId() != null) {
                if (userService.update(scheduleDTO.fromDTO())) {
                    return new ResponseEntity<>(new Message("Update", "Success"), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new Message("Update", "Failed. Something wrong. Check log's."), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new Message("Update", "Failed. ID is null."), HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method added client to a training schedule by id.
     *
     * @param clientId   client id
     * @param scheduleId training schedule
     * @param request
     * @return
     */
    @PostMapping("/add-client/{tsid}/{cid}")
    public ResponseEntity<Message> onAddClientById(
            @PathVariable(name = "tsid") Long scheduleId,
            @PathVariable(name = "cid") Long clientId,
            HttpServletRequest request) {
        try {
            Client client = userService.getClientById(clientId);
            TrainingSchedule schedule = userService.getTrainingScheduleById(scheduleId);

            if (client != null) {
                if (schedule != null) {
                    client.addSchedule(schedule);
                    userService.update(client);

                    return new ResponseEntity<>(new Message("Adding to training schedule", "Success"), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(
                            new Message("Adding to training schedule", "Failed. Training schedule not found."),
                            HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(
                        new Message("Adding to training schedule", "Failed. Client not found."),
                        HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method added trainer to a training schedule by id.
     *
     * @param trainerId  trainer id
     * @param scheduleId training schedule
     * @param request
     * @return
     */
    @PostMapping("/add-trainer/{tsid}/{tid}")
    public ResponseEntity<Message> onAddTrainerById(
            @PathVariable(name = "tsid") Long scheduleId,
            @PathVariable(name = "tid") Long trainerId,
            HttpServletRequest request) {
        try {
            Trainer trainer = userService.getTrainerById(trainerId);
            TrainingSchedule schedule = userService.getTrainingScheduleById(scheduleId);

            if (trainer != null) {
                if (schedule != null) {
                    trainer.addSchedule(schedule);
                    userService.update(trainer);

                    return new ResponseEntity<>(new Message("Adding to training schedule", "Success"), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(
                            new Message("Adding to training schedule", "Failed. Training schedule not found."),
                            HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(
                        new Message("Adding to training schedule", "Failed. Trainer not found."),
                        HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method added training schedule to client.
     *
     * @param schedule
     * @param clients
     * @return if client not found value is false
     */
    private boolean addClientToTrainingSchedule(java.util.List<Long> clients, TrainingSchedule schedule) {
        if (clients != null) {
            for (Long cId : clients) {
                Client client = userService.getClientById(cId);

                if (client != null) {
                    client.addSchedule(schedule);
                    userService.update(client);
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * This method added training schedule to trainer.
     *
     * @param trainers
     * @param schedule
     * @return
     */
    private boolean addTrainerToTrainingSchedule(java.util.List<Long> trainers, TrainingSchedule schedule) {
        if (trainers != null) {
            for (Long tId : trainers) {
                Trainer trainer = userService.getTrainerById(tId);

                if (trainer != null) {
                    trainer.addSchedule(schedule);
                    userService.update(trainer);
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * This method create training schedule.
     *
     * @param scheduleDTO
     * @param request
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Message> onAdd(@RequestBody TrainingScheduleDTO scheduleDTO, HttpServletRequest request) {
        try {
            if (scheduleDTO != null && scheduleDTO.getId() == null) {
                TrainingSchedule schedule = scheduleDTO.fromDTO();

                if (userService.save(schedule)) {
                    if (addClientToTrainingSchedule(scheduleDTO.getClients(), schedule)) {
                        if (addTrainerToTrainingSchedule(scheduleDTO.getTrainers(), schedule)) {
                            return new ResponseEntity<>(new Message("/scheduler/add", "Success."), HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(new Message("/scheduler/add", "Failed. Trainer not found."), HttpStatus.OK);
                        }
                    } else {
                        return new ResponseEntity<>(new Message("/scheduler/add", "Failed. Client not found."), HttpStatus.OK);
                    }
                }
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select training schedule by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> onGetById(@PathVariable Long id, HttpServletRequest request) {
        try {
            TrainingSchedule schedule = userService.getTrainingScheduleById(id);

            if (schedule != null) {
                return new ResponseEntity<>(schedule.toDTO(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new Message("/scheduler/get/" + id, "Training schedule by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select all training schedules.
     *
     * @param request
     * @return
     */
    @GetMapping("/get-all")
    public ResponseEntity<java.util.List<TrainingScheduleDTO>> onGetAll(HttpServletRequest request) {
        try {
            java.util.List<TrainingSchedule> schedules = userService.getAllTrainingSchedules();

            if (schedules != null) {
                java.util.List<TrainingScheduleDTO> schedulesDTO = new ArrayList<>();
                schedules.stream().forEach(s -> schedulesDTO.add(s.toDTO()));

                return new ResponseEntity<>(schedulesDTO, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method delete training schedule by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> onDeleteById(@PathVariable Long id, HttpServletRequest request) {
        try {
            TrainingSchedule schedule = userService.getTrainingScheduleById(id);

            if (schedule != null) {
                if (userService.delete(schedule)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(
                        new Message("/scheduler/delete/" + id, "Training schedule by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
