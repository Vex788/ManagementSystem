package com.zeus.inc.afinams.controllers;

import com.zeus.inc.afinams.dt_repository.ClientDTRepository;
import com.zeus.inc.afinams.dto.PersonDTO;
import com.zeus.inc.afinams.message.Message;
import com.zeus.inc.afinams.model.Client;
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
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientDTRepository clientDTRepository;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/dt/clients", method = RequestMethod.GET)
    public DataTablesOutput<Client> getDTClients(@Valid DataTablesInput input) {
        return clientDTRepository.findAll(input);
    }

    /**
     * This method update client.
     *
     * @param personDTO
     * @param request
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<Message> onUpdate(@RequestBody PersonDTO personDTO, HttpServletRequest request) {
        try {
            Client client = userService.getClientById(personDTO.getId());

            if (client != null) {
                if (userService.update(personDTO.fromClientDTO())) {
                    return new ResponseEntity<>(new Message("/client/update", "Success"), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new Message("/client/update", "ID not found"), HttpStatus.NOT_FOUND);
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
                    if (userService.save(personDTO.fromClientDTO())) {
                        return new ResponseEntity<>(new Message("/admin/add", "Success"), HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>(new Message("/client/add", "Login is exists."), HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select client by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> onGetById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Client client = userService.getClientById(id);

            if (client != null) {
                return new ResponseEntity<>(client, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new Message("/client/get/" + id, "Client by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select client by id and return him subscription.
     *
     * @param request
     * @return
     */
    @GetMapping("/get-subscription/{id}")
    public ResponseEntity<Object> onGetSubscriptionById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Client client = userService.getClientById(id);

            if (client != null) {
                return new ResponseEntity<>(client.getSubscription(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new Message("/client/get-subscription/" + id, "Client by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select client by id and delete him subscription.
     *
     * @param request
     * @return
     */
    @GetMapping("/delete-subscription/{id}")
    public ResponseEntity<Object> onDeleteSubscriptionById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Client client = userService.getClientById(id);

            if (client != null) {
                client.setSubscription(null);
                userService.update(client);

                return new ResponseEntity<>(client.getSubscription(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new Message("/client/delete-subscription/" + id, "Client by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select client by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/get/{login}")
    public ResponseEntity<Object> onGetById(@PathVariable String login, HttpServletRequest request) {
        try {
            Client client = userService.getClientByLogin(login);

            if (client != null) {
                return new ResponseEntity<>(client, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new Message("/client/get/login", "Client by " + login + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select client by login.
     *
     * @param request
     * @return
     */
    @GetMapping("/get-all")
    public ResponseEntity<java.util.List<PersonDTO>> onGetAll(HttpServletRequest request) {
        try {
            java.util.List<Client> clients = userService.getAllClients();

            if (clients != null) {
                java.util.List<PersonDTO> clientsDTO = new ArrayList<>();
                clients.stream().forEach(c -> clientsDTO.add(c.toDTO()));

                return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select training schedules by client id.
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get-schedules/{id}")
    public ResponseEntity<java.util.Set<TrainingSchedule>> onGetSchedules(@PathVariable Long id, HttpServletRequest request) {
        try {
            Client client = userService.getClientById(id);

            if (client != null) {
                return new ResponseEntity<>(client.getSchedules(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method delete client by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> onDeleteById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Client client = userService.getClientById(id);

            if (client != null) {
                if (userService.delete(client)) {
                    return new ResponseEntity<>(client, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(
                        new Message("/client/delete/" + id, "Client by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
