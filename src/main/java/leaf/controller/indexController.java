package leaf.controller;

import leaf.pojo.Cart;
import leaf.pojo.CartItem;
import leaf.pojo.User;
import leaf.service.CartItemService;
import leaf.service.CartService;
import leaf.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @author leaf
 * @create 2022-04-18 16:48
 */
@Controller
public class indexController {

    @Resource
    UserService userService;

    @Resource
    CartService cartService;

    @Resource
    CartItemService cartItemService;

    @GetMapping({"/", "/index.html","/index","leaf"})
    public String index() {
        return "redirect:/page/1";
    }

    @GetMapping({"/login","/login.html"})
    public String login() {
        return "user/login";
    }

    /**
     * 登陆方法
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session) {
        User user = userService.login(userName,password);
        if (user == null) {
            model.addAttribute("errMessage","您的用户名或者密码错误！");
            model.addAttribute("userName",userName);
            return "user/login";
        } else {
            session.setAttribute("user",user);
            // 根据用户查询购物车
            Cart cart = cartService.getCartByUserId(user.getUserId());
            session.setAttribute("cart",cart);
//            // 根据购物车查询购物车项
//            List<CartItem> items = cartItemService.getCartItemByCartId(cart.getCartId());
//            session.setAttribute("cartItems",items);
            return "user/login_success";
        }
    }

    @GetMapping({"/register","/register.html"})
    public String register() {
        return "user/register";
    }


    @PostMapping("/register")
    public String register(@RequestParam("userName") String userName,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email,
                           @RequestParam("code") String code,
                           Model model,
                           HttpSession session) {
        String token = session.getAttribute("code") + "";

        // 验证验证码是否正确
        if (token.equalsIgnoreCase(code)) {
            // 检查用户名是否可用
            if (userService.isExistUserName(userName)) {
                // 用户名已经存在
                model.addAttribute("errMessage", "用户名已经存在！");
                model.addAttribute("email", email);
                return "user/register";
            } else {
                userService.register(new User(null, userName, password, email,0));
                return "user/login";
            }
        } else {
            model.addAttribute("errMessage", "验证码错误！");
            model.addAttribute("userName", userName);
            model.addAttribute("email", email);

            return "user/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.removeAttribute("cart");
        return "redirect:/page/1";
    }
}
