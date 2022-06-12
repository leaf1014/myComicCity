package leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import leaf.pojo.User;

/**
 * @author leaf
 * @create 2022-04-19 0:14
 */
public interface UserService extends IService<User> {

    User login(String userName,String password);

    boolean isExistUserName(String userName);

    void register(User user);
}
