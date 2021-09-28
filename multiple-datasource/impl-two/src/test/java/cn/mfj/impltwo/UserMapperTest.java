package cn.mfj.impltwo;

import cn.mfj.impltwo.common.DataSourceConstant;
import cn.mfj.impltwo.entity.User;
import cn.mfj.impltwo.holder.DynamicDataSourceHolder;
import cn.mfj.impltwo.mapper.UserMapper;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author mfj on 2021/9/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    private static final Logger log = LoggerFactory.getLogger(UserMapperTest.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void createTest() {
        User user = User.builder()
                .username("法外狂徒-张三")
                .email("1234444")
                .sex(1)
                .build();
        userMapper.insert(user);

        DynamicDataSourceHolder.set(DataSourceConstant.SLAVE_DATA_SOURCE);
        User targetUser = new User();
        BeanUtils.copyProperties(user, targetUser);
        targetUser.setUsername("我叫李四");
        userMapper.insert(targetUser);
        DynamicDataSourceHolder.remove();
        log.info("master datasource data:{}, slave datasource data:{}", JSON.toJSONString(user), JSON.toJSONString(targetUser));
    }

    @Test
    public void showTest() {
        User user = userMapper.selectById(1);
        log.info("获取到的user对象为:{}", JSON.toJSONString(user));
    }
}
