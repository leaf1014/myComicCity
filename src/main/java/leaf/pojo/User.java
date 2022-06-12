package leaf.pojo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author leaf
 * @create 2022-04-18 17:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer userId;
    private String userName;
    private String password;
    private String email;
    @TableLogic
    private Integer isDeleted;
}
