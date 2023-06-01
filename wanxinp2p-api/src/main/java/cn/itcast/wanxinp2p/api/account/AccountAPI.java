package cn.itcast.wanxinp2p.api.account;

import cn.itcast.wanxinp2p.api.model.AccountDTO;
import cn.itcast.wanxinp2p.api.model.AccountLoginDTO;
import cn.itcast.wanxinp2p.api.model.AccountRegisterDTO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;

/**
 * @author yy
 * @version 1.0
 */
public interface AccountAPI {
    RestResponse getSMSCode(String mobile);

    RestResponse<Integer> checkMobile(String mobile, String key, String code);

    /**
     * 用户注册
     * @param accountRegisterDTO
     * @return
     */
    RestResponse<AccountDTO> register(AccountRegisterDTO accountRegisterDTO);


    /**
     * 用户登录
     * @param accountLoginDTO 封装登录请求数据
     * @return
     */
    RestResponse<AccountDTO> login(AccountLoginDTO accountLoginDTO);



}



