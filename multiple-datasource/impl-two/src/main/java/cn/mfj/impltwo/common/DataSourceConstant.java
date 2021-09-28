package cn.mfj.impltwo.common;

/**
 * Java主从数据源的名字常量，后续切换为数据库保存
 *
 * @author mfj on 2021/9/28
 */
public interface DataSourceConstant {

    String MASTER_DATA_SOURCE = "master";

    String SLAVE_DATA_SOURCE = "slave";
}
