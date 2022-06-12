package leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import leaf.pojo.CartItem;
import leaf.pojo.OrderItem;

/**
 * @author leaf
 * @create 2022-04-20 16:20
 */
public interface OrderItemService extends IService<OrderItem> {

    void addOrderItemByCartItem(Long orderId,CartItem cartItem);
}
