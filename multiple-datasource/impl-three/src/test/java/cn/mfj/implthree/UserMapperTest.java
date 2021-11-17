package cn.mfj.implthree;

import cn.mfj.implthree.entity.User;
import cn.mfj.implthree.holder.DynamicDataSourceHolder;
import cn.mfj.implthree.mapper.UserMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author mfj on 2021/10/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    private static final Logger log = LoggerFactory.getLogger(UserMapperTest.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectListTest() {
        List<User> defaultMasterUsers = userMapper.selectList(Wrappers.lambdaQuery(User.class));
        DynamicDataSourceHolder.setDataSourceKey("SLAVE");
        List<User> slaveUsers = userMapper.selectList(Wrappers.lambdaQuery(User.class));
        log.info("主数据源查询到的信息为:{}", JSON.toJSONString(defaultMasterUsers));
        log.info("从数据源查询到的信息为:{}", JSON.toJSONString(slaveUsers));
    }
}
