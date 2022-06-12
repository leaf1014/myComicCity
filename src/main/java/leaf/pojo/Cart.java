package leaf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leaf
 * @create 2022-04-19 23:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cart {

    @TableId(value = "cart_id",type = IdType.AUTO)
    private Integer cartId;
    private BigDecimal totalPrice;
    private Integer totalCount;
    private Integer userId;
    @TableLogic
    private Integer isDeleted;
}
