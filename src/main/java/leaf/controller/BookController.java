package leaf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import leaf.pojo.Book;
import leaf.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author leaf
 * @create 2022-04-18 16:51
 */
@Controller
public class BookController {

    @Resource
    BookService bookService;

    @GetMapping({"/page/{pageNum}"})
    public String page(@PathVariable("pageNum") int pageNum, Model model) {
        Page<Book> bookPage = bookService.page(new Page<>(pageNum,4));
        if (bookPage == null) {
            return "error/error_404";
        }
        model.addAttribute("pageResult",bookPage);
        model.addAttribute("url","/page/");
        return "client/index";
    }

    @PostMapping("/bookByPrice")
    public String bookByPrice(@RequestParam(value = "max",required = false) BigDecimal max,
                              @RequestParam(value = "min",required = false) BigDecimal min,
                              Model model) {
        min = min == null ? new BigDecimal(0) : min;
        max = max == null ? new BigDecimal(Integer.MAX_VALUE) : max;
        return bookByPrice(1,min,max,model);
    }

    @GetMapping("/bookByPrice/{min}/{max}/{pageNum}")
    public String bookByPrice(@PathVariable("pageNum") Integer pageNum,
                              @PathVariable("min") BigDecimal min,
                              @PathVariable("max") BigDecimal max,
                              Model model) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<Book>().between("price",min,max);
        Page<Book> bookPage = bookService.page(new Page<>(pageNum,4),bookQueryWrapper);
        model.addAttribute("pageResult",bookPage);
        model.addAttribute("url","/bookByPrice/"+ min + "/" + max + "/");
        return "client/index";
    }
}
