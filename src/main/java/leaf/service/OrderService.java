package leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import leaf.pojo.Cart;
import leaf.pojo.Order;

/**
 * @author leaf
 * @create 2022-04-20 16:05
 */
public interface OrderService extends IService<Order> {

    Long createOrder(Cart cart,Integer userId);

}
