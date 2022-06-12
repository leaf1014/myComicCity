package leaf.pojo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author leaf
 * @create 2022-04-20 16:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItem {

    private Integer orderItemId;
    private String orderItemName;
    private Integer count;
    private BigDecimal price;
    private Long orderId;
    @TableLogic
    private Integer isDeleted;
}
