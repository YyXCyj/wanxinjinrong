package cn.itcast.wanxinp2p.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@MapperScan("cn.itcast.wanxinp2p.account.mapper")
public class Wanxinp2pAccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Wanxinp2pAccountServiceApplication.class, args);
    }

}
