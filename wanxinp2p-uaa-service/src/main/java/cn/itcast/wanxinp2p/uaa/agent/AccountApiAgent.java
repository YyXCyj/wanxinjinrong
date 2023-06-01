package cn.itcast.wanxinp2p.uaa.agent;

import cn.itcast.wanxinp2p.api.model.AccountDTO;
import cn.itcast.wanxinp2p.api.model.AccountLoginDTO;
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
    @PostMapping(value = "/account/l/accounts/session")
    RestResponse<AccountDTO> login(@RequestBody AccountLoginDTO accountLoginDTO);
}
