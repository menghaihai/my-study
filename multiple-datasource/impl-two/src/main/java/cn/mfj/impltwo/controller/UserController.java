package cn.mfj.impltwo.controller;

import cn.mfj.impltwo.annotation.DS;
import cn.mfj.impltwo.common.DataSourceConstant;
import cn.mfj.impltwo.entity.User;
import cn.mfj.impltwo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mfj on 2021/9/29
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @DS(DataSourceConstant.SLAVE_DATA_SOURCE)
    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        User user = userMapper.selectById(id);
        return ResponseEntity.ok(user);
    }
}

