package cn.itcast.wanxinp2p.api.consumer;

import cn.itcast.wanxinp2p.api.model.ConsumerRegisterDTO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;

/**
 * @author yy
 * @version 1.0
 */
public interface ConsumerAPI {
    /**
     * 用户注册
     * @param consumerRegisterDTO
     * @return
     */
    RestResponse register(ConsumerRegisterDTO consumerRegisterDTO);
}
