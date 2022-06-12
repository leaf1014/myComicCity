package leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import leaf.pojo.CartItem;

import java.util.List;

/**
 * @author leaf
 * @create 2022-04-19 23:23
 */
public interface CartItemService extends IService<CartItem> {

    boolean isExistCartItem(String title,Integer cartId);

    CartItem getCartItemByTitleAndCartId(String title,Integer cartId);

    void updateCartItem(CartItem cartItem);

    List<CartItem> getCartItemByCartId(Integer cartId);

    void clear(Integer cartId);
}
