package com.zeus.inc.afinams.controllers;

import com.zeus.inc.afinams.dt_repository.AdminDTRepository;
import com.zeus.inc.afinams.dto.PersonDTO;
import com.zeus.inc.afinams.message.Message;
import com.zeus.inc.afinams.model.Admin;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDTRepository adminDTRepository;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/dt/admins", method = RequestMethod.GET)
    public DataTablesOutput<Admin> getUsers(@Valid DataTablesInput input) {
        return adminDTRepository.findAll(input);
    }

    /**
     * This method update admin.
     *
     * @param personDTO
     * @param request
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<Message> onUpdate(@RequestBody PersonDTO personDTO, HttpServletRequest request) {
        try {
            Admin admin = userService.getAdminById(personDTO.getId());

            if (admin != null) {
                if (userService.update(personDTO.fromAdminDTO())) {
                    return new ResponseEntity<>(new Message("/admin/update", "Success"), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new Message("/admin/update", "ID not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method save new admin to a database.
     *
     * @param personDTO
     * @param request
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Message> onAdd(@RequestBody PersonDTO personDTO, HttpServletRequest request) {
        try {
            if (personDTO != null && personDTO.getId() == null) {
                if (!userService.existsAuthUserByLogin(personDTO.getLogin())) {
                    if (userService.save(personDTO.fromAdminDTO())) {
                        return new ResponseEntity<>(new Message("/admin/add", "Success"), HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>(new Message("/admin/add", "Login is exists."), HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select admin by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> onGetById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Admin admin = userService.getAdminById(id);

            if (admin != null) {
                return new ResponseEntity<>(admin, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new Message("/admin/get/" + id, "Admin by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select admin by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/get/{login}")
    public ResponseEntity<Object> onGetById(@PathVariable String login, HttpServletRequest request) {
        try {
            Admin admin = userService.getAdminByLogin(login);

            if (admin != null) {
                return new ResponseEntity<>(admin, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new Message("/admin/get/login", "Admin by " + login + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select all admins.
     *
     * @param request
     * @return
     */
    @GetMapping("/get-all")
    public ResponseEntity<java.util.List<PersonDTO>> onGetAll(HttpServletRequest request) {
        try {
            java.util.List<Admin> admins = userService.getAllAdmins();

            if (admins != null) {
                java.util.List<PersonDTO> adminsDTO = new ArrayList<>();
                admins.stream().forEach(a -> adminsDTO.add(a.toDTO()));

                return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method delete admin by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> onDeleteById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Admin admin = userService.getAdminById(id);

            if (admin != null) {
                if (userService.delete(admin)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(
                        new Message("/admin/delete/" + id, "Admin by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
