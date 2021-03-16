package com.zeus.inc.afinams.controllers;

import com.zeus.inc.afinams.dt_repository.TrainerDTRepository;
import com.zeus.inc.afinams.dto.PersonDTO;
import com.zeus.inc.afinams.message.Message;
import com.zeus.inc.afinams.model.Trainer;
import com.zeus.inc.afinams.model.TrainingSchedule;
import com.zeus.inc.afinams.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private TrainerDTRepository trainerDTRepository;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/dt/trainers", method = RequestMethod.GET)
    public DataTablesOutput<Trainer> getDTClients(@Valid DataTablesInput input) {
        return trainerDTRepository.findAll(input);
    }

    @GetMapping("/trs")
    public ResponseEntity<Object> onGetTestTRS(HttpServletRequest request) {
        return new ResponseEntity<>(new Message("success", "success"), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method update trainer.
     *
     * @param personDTO
     * @param request
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<Message> onUpdate(@RequestBody PersonDTO personDTO, HttpServletRequest request) {
        try {
            if (personDTO != null && personDTO.getId() != null) {
                if (userService.existsAuthUserByLogin(personDTO.getLogin())) {
                    if (userService.update(personDTO.fromTrainerDTO())) {
                        return new ResponseEntity<>(new Message("/trainer/update", "Success"), HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>(new Message("/trainer/update", "Login is exists."), HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add")
    public ResponseEntity<Message> onAdd(@RequestBody PersonDTO personDTO, HttpServletRequest request) {
        try {
            if (personDTO != null && personDTO.getId() == null) {
                if (!userService.existsAuthUserByLogin(personDTO.getLogin())) {
                    if (userService.save(personDTO.fromTrainerDTO())) {
                        return new ResponseEntity<>(new Message("/trainer/add", "Success"), HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>(new Message("/trainer/add", "Login is exists."), HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select trainer by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> onGetById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Trainer trainer = userService.getTrainerById(id);

            if (trainer != null) {
                return new ResponseEntity<>(trainer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new Message("/trainer/get/" + id, "Trainer by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select trainer by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/get/{login}")
    public ResponseEntity<Object> onGetById(@PathVariable String login, HttpServletRequest request) {
        try {
            Trainer trainer = userService.getTrainerByLogin(login);

            if (trainer != null) {
                return new ResponseEntity<>(trainer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new Message("/trainer/get/login", "Trainer by " + login + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select all trainers.
     *
     * @param request
     * @return
     */
    @GetMapping("/get-all")
    public ResponseEntity<java.util.List<PersonDTO>> onGetAll(HttpServletRequest request) {
        try {
            java.util.List<Trainer> trainers = userService.getAllTrainers();

            if (trainers != null) {
                java.util.List<PersonDTO> trainersDTO = new ArrayList<>();
                trainers.stream().forEach(c -> trainersDTO.add(c.toDTO()));

                return new ResponseEntity<>(trainersDTO, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select training schedules by trainer id.
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get-schedules/{id}")
    public ResponseEntity<java.util.Set<TrainingSchedule>> onGetSchedules(@PathVariable Long id, HttpServletRequest request) {
        try {
            Trainer trainer = userService.getTrainerById(id);

            if (trainer != null) {
                return new ResponseEntity<>(trainer.getSchedules(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method delete trainer by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> onDeleteById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Trainer trainer = userService.getTrainerById(id);

            if (trainer != null) {
                if (userService.delete(trainer)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(
                        new Message("/trainer/delete/" + id, "Trainer by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
