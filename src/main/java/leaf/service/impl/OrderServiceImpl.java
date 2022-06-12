package leaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import leaf.mapper.CartMapper;
import leaf.mapper.OrderItemMapper;
import leaf.mapper.OrderMapper;
import leaf.pojo.*;
import leaf.service.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author leaf
 * @create 2022-04-20 16:06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    CartItemService cartItemService;

    @Resource
    OrderItemService orderItemService;

    @Resource
    CartService cartService;

    @Resource
    BookService bookService;

    @Override
    public Long createOrder(Cart cart,Integer userId) {
        Date date = new Date();
        // 订单号(唯一性)
        Long orderId = Long.parseLong(System.currentTimeMillis() + "" + userId);
        // 创建一个订单对象
        orderMapper.insert(new Order(orderId,date,cart.getTotalPrice(),0,userId,0));

        List<CartItem> cartItems = cartItemService.getCartItemByCartId(cart.getCartId());

        for (CartItem cartItem : cartItems) {
            orderItemService.addOrderItemByCartItem(orderId, cartItem);
            Book book = bookService.getBookByTitle(cartItem.getCartItemTitle());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookService.updateById(book);
        }

        this.clear(userId,cart.getCartId());
        return orderId;
    }


    public void clear(Integer userId,Integer cartId) {
        cartItemService.clear(cartId);
        cartService.clear(userId);
    }

    @GetMapping("/clear")
    public String clear(HttpSession session) {

        return "";
    }
}
