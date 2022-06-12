package leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import leaf.pojo.Book;

/**
 * @author leaf
 * @create 2022-04-18 17:26
 */
public interface BookService extends IService<Book> {

    Book getBookByTitle(String title);
}
