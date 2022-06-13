package leaf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Mod;
import leaf.pojo.*;
import leaf.service.OrderItemService;
import leaf.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author leaf
 * @create 2022-04-18 17:43
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @Resource
    OrderItemService orderItemService;

    @GetMapping("")
    public String order() {
        return "redirect:/order/page/1";
    }

    @GetMapping("/page/{pageNum}")
    public String page(@PathVariable("pageNum") int pageNum, Model model,HttpSession session) {
        System.out.println(1);
        System.out.println(2);
        System.out.println("master commit");
        User user = (User) session.getAttribute("user");
        QueryWrapper<Order> queryWrapper = new QueryWrapper<Order>().eq("user_id",user.getUserId());
        Page<Order> orderPage = orderService.page(new Page<>(pageNum,4),queryWrapper);
        model.addAttribute("pageResult",orderPage);
        model.addAttribute("url","/order/page/");
        return "order/order";
    }

    @GetMapping("/detail/{orderId}")
    public String detail(@PathVariable("orderId") Long orderId,HttpSession session) {
        session.setAttribute("orderId",orderId);
        return "redirect:/order/detailPage/1";
    }

    @GetMapping("/detailPage/{pageNum}")
    public String detailPage(@PathVariable("pageNum") int pageNum,Model model,HttpSession session) {
        Long orderId = (Long) session.getAttribute("orderId");
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<OrderItem>().eq("order_id",orderId);
        Page<OrderItem> orderItemPage = orderItemService.page(new Page<>(pageNum,4),queryWrapper);
        model.addAttribute("pageResult",orderItemPage);
        model.addAttribute("url","/order/detailPage/");
        return "order/order_detail";
    }

    @GetMapping("/checkOut")
    public String checkOut(Model model, HttpSession session) {
        // 先获取Cart购物车对象
        Cart cart = (Cart) session.getAttribute("cart");
        // 获取Userid
        User user = (User) session.getAttribute("user");
        Integer userId = user.getUserId();
        // 调用OrderService生成订单和订单项
        Long orderId = orderService.createOrder(cart,userId);
        model.addAttribute("orderId",orderId);
        session.removeAttribute("cart");
        return "cart/checkout";
    }

    @GetMapping("/receipt/{orderId}/{pageNum}")
    public String receipt(@PathVariable("orderId") Long orderId, @PathVariable("pageNum") int pageNum) {
        Order order = orderService.getById(orderId);
        order.setStatus(2);
        orderService.updateById(order);
        return "redirect:/order/page/" + pageNum;
    }
}
