package leaf.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author leaf
 * @create 2022-04-18 17:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    @TableId(value = "order_id")
    private Long orderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private BigDecimal price;
    // 0未发货 1已发货 2表示已签收
    private Integer status = 0;
    private Integer userId;
    @TableLogic
    private Integer isDeleted;
}
