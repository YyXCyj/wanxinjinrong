package cn.itcast.wanxinp2p.consumer.service.serviceImpl;

import cn.itcast.wanxinp2p.api.model.AccountDTO;
import cn.itcast.wanxinp2p.api.model.AccountRegisterDTO;
import cn.itcast.wanxinp2p.api.model.ConsumerRegisterDTO;
import cn.itcast.wanxinp2p.common.domain.BusinessException;
import cn.itcast.wanxinp2p.common.domain.CodePrefixCode;
import cn.itcast.wanxinp2p.common.domain.CommonErrorCode;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.common.util.CodeNoUtil;
import cn.itcast.wanxinp2p.consumer.agent.AccountApiAgent;
import cn.itcast.wanxinp2p.consumer.common.ConsumerErrorCode;
import cn.itcast.wanxinp2p.consumer.entity.Consumer;
import cn.itcast.wanxinp2p.consumer.entity.ConsumerDTO;
import cn.itcast.wanxinp2p.consumer.mapper.ConsumerMapper;
import cn.itcast.wanxinp2p.consumer.service.ConsumerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @version 1.0
 */
@Service
public class ConsumerServiceImpl  extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService {

    @Autowired
    private AccountApiAgent accountApiAgent;
    @Override
    public Integer checkMobile(String mobile) {
        return getByMobile(mobile, false) != null ? 1 : 0;
    }
    /**
     * 根据手机号获取用户信息
     * @param mobile 手机号
     * @param throwEx 不存在是否抛出异常
     * @return
     */
    private ConsumerDTO getByMobile(String mobile, Boolean throwEx) {
        Consumer entity = getOne(new QueryWrapper<Consumer>().lambda().eq(Consumer::getMobile, mobile));
        return convertConsumerEntityToDTO(entity);
    }

    @Override
    public void register(ConsumerRegisterDTO consumerRegisterDTO) {
        //检测是否已注册
        if (checkMobile(consumerRegisterDTO.getMobile()) == 1) {
            throw new BusinessException(ConsumerErrorCode.E_140107);
        }
        Consumer consumer=new Consumer();
        BeanUtils.copyProperties(consumerRegisterDTO, consumer);
        consumer.setUsername(CodeNoUtil.getNo(CodePrefixCode.CODE_NO_PREFIX));
        consumerRegisterDTO.setUsername(consumer.getUsername());
        consumer.setUserNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
        consumer.setIsBindCard(0);
        //保存用户信息
        save(consumer);

        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO();
        BeanUtils.copyProperties(consumerRegisterDTO, accountRegisterDTO);
        RestResponse<AccountDTO> restResponse = accountApiAgent
                .register(accountRegisterDTO);
        if (restResponse.getCode() != CommonErrorCode.SUCCESS.getCode()) {
            throw new BusinessException(ConsumerErrorCode.E_140106);
        }
    }

    /**
     * entity转为dto
     * @param entity
     * @return
     */
    private ConsumerDTO convertConsumerEntityToDTO(Consumer entity) {
        if (entity == null) {
            return null;
        }
        ConsumerDTO dto = new ConsumerDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
