package leaf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author leaf
 * @create 2022-04-18 17:23
 */
@Data
@NoArgsConstructor
@ToString
public class Book {

    @TableId(value = "book_id")
    private Integer bookId;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer sales;
    private Integer stock;
    private String imgPath  = "static/img/default.jpg";
    @TableLogic
    private Integer isDeleted = 0;

    public Book(Integer bookId, String title, String author, BigDecimal price, Integer sales, Integer stock, String imgPath, Integer isDeleted) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
        this.imgPath = imgPath;
        this.isDeleted = isDeleted;
    }

    public Book(Integer bookId, String title, String author, BigDecimal price, Integer sales, Integer stock, Integer isDeleted) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
        this.isDeleted = isDeleted;
    }
}
