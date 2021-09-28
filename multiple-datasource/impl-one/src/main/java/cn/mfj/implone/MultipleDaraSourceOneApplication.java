package cn.mfj.implone;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author favian.meng on 2021-09-28
 */
@SpringBootApplication
@MapperScans({
        @MapperScan(basePackages = "cn.mfj.implone.mapper.master", sqlSessionFactoryRef = "masterSqlSessionFactory"),
        @MapperScan(basePackages = "cn.mfj.implone.mapper.slave", sqlSessionFactoryRef = "slaveSqlSessionFactory")
})
public class MultipleDaraSourceOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultipleDaraSourceOneApplication.class, args);
    }
}
