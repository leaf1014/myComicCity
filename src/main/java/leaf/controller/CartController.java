package leaf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import leaf.mapper.CartItemMapper;
import leaf.pojo.Book;
import leaf.pojo.Cart;
import leaf.pojo.CartItem;
import leaf.pojo.User;
import leaf.service.BookService;
import leaf.service.CartItemService;
import leaf.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * @author leaf
 * @create 2022-04-18 17:47
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Resource
    CartService cartService;

    @Resource
    CartItemService cartItemService;

    @Resource
    BookService bookService;

    @GetMapping("")
    public String cart() {
        return "redirect:/cart/page/1";
    }

    @GetMapping({"/page/{pageNum}"})
    public String page(@PathVariable("pageNum") int pageNum, Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            return "cart/cart";
        }
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<CartItem>().eq("cart_id", cart.getCartId());
        Page<CartItem> cartItemPage = cartItemService.page(new Page<>(pageNum, 4), queryWrapper);
        model.addAttribute("pageResult", cartItemPage);
        model.addAttribute("url", "/cart/page/");
        return "cart/cart";
    }

    @GetMapping("/{bookId}/{pageNum}")
    public String addToCart(@PathVariable("bookId") Integer bookId,
                            @PathVariable("pageNum") Integer pageNum,
                            Model model,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        // 查询当前用户是否为空
        if (!cartService.isExistCart(userId)) {
            cartService.creatNewCart(userId);
        }
        // 查询用户的购物车
        Cart cart = cartService.getCartByUserId(userId);
        // 查询书籍信息
        Book book = bookService.getById(bookId);
        // 查询当前商品是否为该用户的第一件
        if (cartItemService.isExistCartItem(book.getTitle(), cart.getCartId())) {
            // 已经存在 添加进商品项
            CartItem cartItem = cartItemService.getCartItemByTitleAndCartId(book.getTitle(), cart.getCartId());
            cartItem.setCount(cartItem.getCount() + 1);
            cartItemService.updateCartItem(cartItem);
        } else {
            cartItemService.save(new CartItem(null, book.getTitle(), 1, book.getPrice(), 0, cart.getCartId()));
        }
        // 添加入购物车
        cart.setTotalCount(cart.getTotalCount() + 1);
        cart.setTotalPrice(cart.getTotalPrice().add(book.getPrice()));
        cartService.updateCart(cart);

        session.setAttribute("cart", cart);
        session.setAttribute("lastName", book.getTitle());
        return "redirect:/page/" + pageNum;
    }

    @GetMapping("/addCount/{bookTitle}/{pageNum}")
    public String addCount(@PathVariable("bookTitle") String title,
                           @PathVariable("pageNum") Integer pageNum,
                           HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        Book book = bookService.getBookByTitle(title);
        CartItem cartItem = cartItemService.getCartItemByTitleAndCartId(title, cart.getCartId());
        if (cartItem.getCount() <= book.getStock()) {
            cartItem.setCount(cartItem.getCount() + 1);
            cart.setTotalPrice(cart.getTotalPrice().add(cartItem.getPrice()));
            cart.setTotalCount(cart.getTotalCount() + 1);
            cartService.updateCart(cart);
        }
        session.setAttribute("cart", cart);
        cartItemService.updateById(cartItem);
        return "redirect:/cart/page/" + pageNum;
    }

    @GetMapping("/subCount/{bookTitle}/{pageNum}")
    public String subCount(@PathVariable("bookTitle") String title,
                           @PathVariable("pageNum") Integer pageNum,
                           HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        CartItem cartItem = cartItemService.getCartItemByTitleAndCartId(title, cart.getCartId());
        if (cartItem.getCount() > 1) {
            cartItem.setCount(cartItem.getCount() - 1);
            cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getPrice()));
            cart.setTotalCount(cart.getTotalCount() - 1);
            cartService.updateCart(cart);
        }
        cartItemService.updateById(cartItem);
        session.setAttribute("cart", cart);
        return "redirect:/cart/page/" + pageNum;
    }

    @GetMapping("/delete/{cartItemId}/{pageNum}")
    public String delete(@PathVariable("cartItemId") Integer cartItemId,
                         @PathVariable("pageNum") Integer pageNum,
                         HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        CartItem cartItem = cartItemService.getById(cartItemId);
        cart.setTotalCount(cart.getTotalCount() - cartItem.getCount());
        cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount()))));
        cartService.updateCart(cart);
        session.setAttribute("cart", cart);
        cartItemService.removeById(cartItemId);
        return "redirect:/cart/page/" + pageNum;
    }

    @GetMapping("/clear")
    public String clear(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        cartItemService.clear(cart.getCartId());
        session.removeAttribute("cart");
        return "redirect:/cart/page/1";
    }
}
