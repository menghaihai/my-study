package cn.mfj.implone.controller;

import cn.mfj.implone.entity.master.MasterUser;
import cn.mfj.implone.entity.slave.SlaveUser;
import cn.mfj.implone.mapper.master.MasterUserMapper;
import cn.mfj.implone.mapper.slave.SlaveUserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author favian.meng on 2021-09-28
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private MasterUserMapper masterUserMapper;

    @Resource
    private SlaveUserMapper slaveUserMapper;

    @PostMapping("/master")
    public ResponseEntity<MasterUser> masterCreate(@RequestBody MasterUser masterUser) {
        masterUserMapper.insert(masterUser);
        return ResponseEntity.ok(masterUser);
    }

    @GetMapping("/slave")
    public ResponseEntity<SlaveUser> slaveCreate(@RequestBody SlaveUser slaveUser) {
        slaveUserMapper.insert(slaveUser);
        return ResponseEntity.ok(slaveUser);
    }
}
