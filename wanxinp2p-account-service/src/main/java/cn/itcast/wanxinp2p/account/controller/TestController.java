package cn.itcast.wanxinp2p.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @version 1.0
 */
@RestController
@Api(value = "测试API", tags = {"测试API"})
public class TestController {
    @GetMapping("/hello2")
    @ApiOperation(value = "获取用户列表", notes = "用户列表页面")
    public String hello2(){
        return "Hello World";
    }

    @GetMapping("/hello3")
    @ApiOperation(value = "获取用户列表", notes = "用户列表页面")
    public String hello3(){
        return "Hello World1";
    }
}


class test1{
    public static void main(String[] args) {
        String s = new String("abcabcabcabc");
        boolean b = repeatedSubstringPattern(s);
        System.out.println(b);
    }
    public static boolean repeatedSubstringPattern(String s) {
        if (s.equals("")) return false;

        int len = s.length();
        // 原串加个空格(哨兵)，使下标从1开始，这样j从0开始，也不用初始化了
        s = " " + s;
        char[] chars = s.toCharArray();
        int[] next = new int[len + 1];

        // 构造 next 数组过程，j从0开始(空格)，i从2开始
        for (int i = 2, j = 0; i <= len; i++) {
            // 匹配不成功，j回到前一位置 next 数组所对应的值
            while (j > 0 && chars[i] != chars[j + 1]) j = next[j];
            // 匹配成功，j往后移
            if (chars[i] == chars[j + 1]) j++;
            // 更新 next 数组的值
            next[i] = j;
        }

        // 最后判断是否是重复的子字符串，这里 next[len] 即代表next数组末尾的值
        if (next[len] > 0 && len % (len - next[len]) == 0) {
            return true;
        }
        return false;
    }
}
