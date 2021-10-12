package cn.mfj.implthree.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 数据源信息存储到数据库的实体类映射
 *
 * @author favian.meng on 2021-10-12
 */
@Data
@TableName("t_es_datasource_info")
public class DataSourceInfo {

    /**
     * 数据源主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据源code
     */
    private String code;

    /**
     * 数据源驱动程序
     */
    private String driverClassName;

    /**
     * 数据源url连接
     */
    private String url;

    /**
     * 数据源用户名
     */
    private String username;

    /**
     * 数据源密码
     */
    private String password;
}
