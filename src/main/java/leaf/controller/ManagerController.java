package leaf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import leaf.pojo.Book;
import leaf.pojo.Order;
import leaf.service.BookService;
import leaf.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author leaf
 * @create 2022-04-18 17:48
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    BookService bookService;

    @Resource
    OrderService orderService;

    @GetMapping("")
    public String manager() {
        return "manager/manager";
    }

    @GetMapping("/book/{pageNum}")
    public String bookManager(@PathVariable("pageNum") int pageNum, Model model) {
        Page<Book> bookPage = bookService.page(new Page<>(pageNum,4));
        model.addAttribute("pageResult",bookPage);
        model.addAttribute("url","/manager/book/");
        return "manager/book_manager";
    }

    @GetMapping("/update/{bookId}/{pageNum}")
    public String update(@PathVariable("bookId") int bookId,
                         @PathVariable("pageNum") int pageNum,
                         Model model) {
        Book book = bookService.getById(bookId);
        model.addAttribute("book",book);
        model.addAttribute("pageNum",pageNum);
        return "manager/book_edit";
    }

    @PostMapping("/book")
    public String update(@RequestParam("bookId") Integer bookId,
                         @RequestParam("title") String title,
                         @RequestParam("author") String author,
                         @RequestParam("price") BigDecimal price,
                         @RequestParam("sales") Integer sales,
                         @RequestParam("stock") Integer stock,
                         @RequestParam("pageNum") Integer pageNum) {
        Book book = bookService.getById(bookId);
        bookService.updateById(new Book(bookId,title,author,price,sales,stock,book.getImgPath(),book.getIsDeleted()));
        return "redirect:/manager/book/" + pageNum;
    }

    @GetMapping("/delete/{bookId}/{pageNum}")
    public String delete(@PathVariable("bookId") int bookId,
                         @PathVariable("pageNum") int pageNum) {
        bookService.removeById(bookId);
        return "redirect:/manager/book/" + pageNum;
    }

    @GetMapping("/add/{pages}")
    public String add(@PathVariable("pages") int pages,Model model) {
        model.addAttribute("pages",pages);
        return "manager/book_add";
    }

    @PostMapping("/add")
    public String add(@RequestParam("title") String title,
                      @RequestParam("author") String author,
                      @RequestParam("price") BigDecimal price,
                      @RequestParam("sales") Integer sales,
                      @RequestParam("stock") Integer stock,
                      @RequestParam("pages") Integer pages) {
        bookService.save(new Book(null,title,author,price,sales,stock,0));
        return "redirect:/manager/book/" + pages;
    }

    @GetMapping("/order/{pageNum}")
    public String orderManager(@PathVariable("pageNum") int pageNum, Model model) {
        Page<Order> orderPage = orderService.page(new Page<>(pageNum,4));
        model.addAttribute("pageResult",orderPage);
        model.addAttribute("url","/manager/order/");
        return "manager/order_manager";
    }

    @GetMapping("/ship/{orderId}/{pageNum}")
    public String ship(@PathVariable("orderId") Long orderId, @PathVariable("pageNum") int pageNum) {
        Order order = orderService.getById(orderId);
        order.setStatus(1);
        orderService.updateById(order);
        return "redirect:/manager/order/" + pageNum;
    }
}
