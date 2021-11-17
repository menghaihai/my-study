package cn.mfj.implthree.service.impl;

import cn.mfj.implthree.entity.User;
import cn.mfj.implthree.mapper.UserMapper;
import cn.mfj.implthree.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author favian.meng on 2021-10-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
