package cn.itcast.wanxinp2p.consumer.controller;

import cn.itcast.wanxinp2p.api.consumer.ConsumerAPI;
import cn.itcast.wanxinp2p.api.model.ConsumerRegisterDTO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.common.util.EncryptUtil;
import cn.itcast.wanxinp2p.consumer.service.ConsumerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "用户服务的Controller", tags = "Consumer", description = "用户服务API")
    public class ConsumerController implements ConsumerAPI {

    @Autowired
    private ConsumerService consumerService;

        @ApiOperation("测试hello")
        @GetMapping(path = "/hello")
        public String hello(){
            return "hello";
        }

        @ApiOperation("测试hi")
        @PostMapping(path="/hi")
        @ApiImplicitParam(name="name",value = "姓名",required = true,dataType = "String")
        public String hi(String name){
            return "hi,"+name;
        }


    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "consumerRegisterDTO", value = "用户注册", required =
            true,
            dataType = "AccountRegisterDTO", paramType = "body")
    @PostMapping(value = "/consumers")
    @Override
    public RestResponse register(@RequestBody ConsumerRegisterDTO consumerRegisterDTO) {
        consumerService.register(consumerRegisterDTO);
        return RestResponse.success();
    }

    @ApiOperation("过网关受保护资源，进行认证拦截测试")
    @ApiImplicitParam(name = "jsonToken", value = "访问令牌",  required = true, dataType = "String")
    @GetMapping(value = "/m/consumers/test")
    public RestResponse<String> testResources( String jsonToken) {
        return RestResponse.success(EncryptUtil.decodeUTF8StringBase64(jsonToken));
    }
}
