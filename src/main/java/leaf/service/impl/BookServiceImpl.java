package leaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import leaf.mapper.BookMapper;
import leaf.pojo.Book;
import leaf.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author leaf
 * @create 2022-04-18 17:27
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Resource
    BookMapper bookMapper;

    @Override
    public Book getBookByTitle(String title) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>().eq("title",title);
        return bookMapper.selectOne(queryWrapper);
    }
}
