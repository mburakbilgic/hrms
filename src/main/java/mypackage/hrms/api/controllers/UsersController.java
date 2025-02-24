package mypackage.hrms.api.controllers;

import mypackage.hrms.business.abstracts.UsersService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/getall")
    public ResponseEntity<DataNotification<List<Users>>> getAll() {
        return ResponseEntity.ok(usersService.getAll());
    }

    @PostMapping("/add")
    public Notification add(@RequestBody Users users) {
        return usersService.add(users);
    }

    @PutMapping("/update")
    public ResponseEntity<Notification> update(@RequestBody Users users) {
        return ResponseEntity.ok(usersService.update(users));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Notification> delete(@PathVariable int id) {
        return ResponseEntity.ok(usersService.delete(id));
    }

}