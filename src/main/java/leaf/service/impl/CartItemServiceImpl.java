package leaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import leaf.mapper.CartItemMapper;
import leaf.pojo.CartItem;
import leaf.service.CartItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author leaf
 * @create 2022-04-19 23:24
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {

    @Resource
    CartItemMapper cartItemMapper;

    @Override
    public boolean isExistCartItem(String title,Integer cartId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<CartItem>().eq("cart_item_title",title).eq("cart_id",cartId);
        return cartItemMapper.selectOne(queryWrapper) != null;
    }

    @Override
    public CartItem getCartItemByTitleAndCartId(String title,Integer cartId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<CartItem>().eq("cart_item_title",title).eq("cart_id",cartId);
        return cartItemMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemMapper.updateById(cartItem);
    }

    @Override
    public List<CartItem> getCartItemByCartId(Integer cartId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<CartItem>().eq("cart_id",cartId);
        return cartItemMapper.selectList(queryWrapper);
    }

    @Override
    public void clear(Integer cartId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<CartItem>().eq("cart_id",cartId);
        cartItemMapper.delete(queryWrapper);
    }
}
