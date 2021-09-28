package cn.mfj.impltwo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author favian.meng on 2021-09-28
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.mfj.impltwo.mapper")
public class MultipleDataSourceTwoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultipleDataSourceTwoApplication.class, args);
    }
}
