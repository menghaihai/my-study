package cn.mfj.implone.entity.slave;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author favian.meng on 2021-09-28
 */
@Data
@TableName("t_user")
public class SlaveUser {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String email;

    private Integer sex;
}
