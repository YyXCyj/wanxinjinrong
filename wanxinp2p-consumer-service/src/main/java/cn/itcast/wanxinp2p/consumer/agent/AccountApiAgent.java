package cn.itcast.wanxinp2p.consumer.agent;

import cn.itcast.wanxinp2p.api.model.AccountDTO;
import cn.itcast.wanxinp2p.api.model.AccountRegisterDTO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yy
 * @version 1.0
 */

@FeignClient(value = "account-service")
public interface AccountApiAgent {

    @PostMapping(value = "/account/l/accounts")
    RestResponse<AccountDTO> register(@RequestBody AccountRegisterDTO
                                              accountRegisterDTO);
}
