package leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import leaf.pojo.Cart;
import leaf.pojo.User;

/**
 * @author leaf
 * @create 2022-04-19 23:14
 */
public interface CartService extends IService<Cart> {

    boolean isExistCart(Integer userId);

    void creatNewCart(Integer userId);

    Cart getCartByUserId(Integer userId);

    void updateCart(Cart cart);

    void clear(Integer userId);
}
