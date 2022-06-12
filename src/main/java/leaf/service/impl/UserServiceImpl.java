package leaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import leaf.mapper.UserMapper;
import leaf.pojo.User;
import leaf.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author leaf
 * @create 2022-04-19 0:15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User login(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("user_name",userName)
                .eq("password",password);

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean isExistUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("user_name",userName);
        return userMapper.selectOne(queryWrapper) != null;
    }

    @Override
    public void register(User user) {
        userMapper.insert(user);
    }
}
