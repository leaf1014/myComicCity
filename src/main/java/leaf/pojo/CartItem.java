package leaf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author leaf
 * @create 2022-04-19 23:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItem {

    @TableId(value = "cart_item_id",type = IdType.AUTO)
    private Integer cartItemId;
    private String cartItemTitle;
    private Integer count;
    private BigDecimal price;
    @TableLogic
    private Integer isDeleted;
    private Integer cartId;

}
