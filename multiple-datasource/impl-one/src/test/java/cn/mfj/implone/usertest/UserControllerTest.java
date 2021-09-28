package cn.mfj.implone.usertest;

import cn.mfj.implone.entity.master.MasterUser;
import cn.mfj.implone.entity.slave.SlaveUser;
import cn.mfj.implone.mapper.master.MasterUserMapper;
import cn.mfj.implone.mapper.slave.SlaveUserMapper;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author favian.meng on 2021-09-28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private MasterUserMapper masterUserMapper;

    @Autowired
    private SlaveUserMapper slaveUserMapper;

    @Test
    public void createTest() {
        MasterUser masterUser = new MasterUser();
        masterUser.setId(1L);
        masterUser.setUsername("张三");
        masterUser.setEmail("199999@qq.com");
        masterUser.setSex(1);
        masterUserMapper.insert(masterUser);

        SlaveUser slaveUser = new SlaveUser();
        slaveUser.setId(2L);
        slaveUser.setUsername("李四");
        slaveUser.setEmail("19999989@qq.com");
        slaveUser.setSex(1);
        slaveUserMapper.insert(slaveUser);
        log.info("master user data:{} and slave user data:{}", JSON.toJSONString(masterUser), JSON.toJSONString(slaveUser));
    }

    @Test
    public void showTest() {
        MasterUser masterUser = masterUserMapper.selectById(1L);
        log.info("master user信息为：{}", JSON.toJSONString(masterUser));
        SlaveUser slaveUser = slaveUserMapper.selectById(2L);
        log.info("slave user信息为：{}", JSON.toJSONString(slaveUser));
    }
}
