package cn.itcast.wanxinp2p.account;

import cn.itcast.wanxinp2p.common.util.PasswordUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Wanxinp2pAccountServiceApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    public void getPassword(){
        String s = PasswordUtil.generate("123456");
        System.out.println(s);
    }

}
