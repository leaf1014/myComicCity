package leaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import leaf.mapper.OrderItemMapper;
import leaf.pojo.CartItem;
import leaf.pojo.OrderItem;
import leaf.service.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author leaf
 * @create 2022-04-20 16:20
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Resource
    OrderItemMapper orderItemMapper;

    @Override
    public void addOrderItemByCartItem(Long orderId,CartItem cartItem) {
        orderItemMapper.insert(new OrderItem(null,cartItem.getCartItemTitle(),cartItem.getCount(),cartItem.getPrice(),orderId,0));
    }
}
