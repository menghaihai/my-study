package cn.mfj.implthree;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mfj on 2021/10/11
 */
@MapperScan(basePackages = "cn.mfj.implthree.mapper")
@SpringBootApplication
public class ImplThreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImplThreeApplication.class, args);
    }
}
