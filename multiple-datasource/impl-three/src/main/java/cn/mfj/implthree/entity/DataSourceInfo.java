package cn.mfj.implthree.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 数据源信息存储到数据库的实体类映射
 *
 * @author favian.meng on 2021-10-12
 */
@Data
@TableName("t_datasource_info")
public class DataSourceInfo {

    /**
     * 数据源主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据源code
     */
    @NotBlank(message = "数据源code不能为空")
    private String code;

    /**
     * 数据源驱动程序
     */
    @NotBlank(message = "数据源驱动不能为空")
    private String driverClassName;

    /**
     * 数据源url连接
     */
    @NotBlank(message = "数据源url不能为空")
    private String url;

    /**
     * 数据源用户名
     */
    @NotBlank(message = "数据源姓名不能为空")
    private String username;

    /**
     * 数据源密码
     */
    @NotBlank(message = "数据源密码不能为空")
    private String password;
}
