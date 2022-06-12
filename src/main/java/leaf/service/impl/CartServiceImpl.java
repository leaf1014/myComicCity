package leaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import leaf.mapper.CartMapper;
import leaf.pojo.Cart;
import leaf.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author leaf
 * @create 2022-04-19 23:15
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper,Cart> implements CartService {

    @Resource
    CartMapper cartMapper;

    @Override
    public boolean isExistCart(Integer userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>().eq("user_id",userId);
        return cartMapper.selectOne(queryWrapper) != null;
    }

    @Override
    public void creatNewCart(Integer userId) {
        cartMapper.insert(new Cart(null,new BigDecimal(0),0,userId,0));
    }

    @Override
    public Cart getCartByUserId(Integer userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>().eq("user_id",userId);
        return cartMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateCart(Cart cart) {
        cartMapper.updateById(cart);
    }

    @Override
    public void clear(Integer userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>().eq("user_id",userId);
        cartMapper.delete(queryWrapper);
    }
}
