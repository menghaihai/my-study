package cn.mfj.implthree.controller;

import cn.mfj.implthree.entity.User;
import cn.mfj.implthree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author favian.meng on 2021-10-13
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        User user = service.getById(id);
        return ResponseEntity.ok(user);
    }
}
