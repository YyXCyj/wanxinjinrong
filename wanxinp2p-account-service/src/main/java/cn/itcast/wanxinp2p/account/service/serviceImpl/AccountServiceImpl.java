package cn.itcast.wanxinp2p.account.service.serviceImpl;

import cn.itcast.wanxinp2p.account.common.AccountErrorCode;
import cn.itcast.wanxinp2p.account.entity.Account;
import cn.itcast.wanxinp2p.account.mapper.AccountMapper;
import cn.itcast.wanxinp2p.account.service.AccountService;
import cn.itcast.wanxinp2p.api.model.AccountDTO;
import cn.itcast.wanxinp2p.api.model.AccountLoginDTO;
import cn.itcast.wanxinp2p.api.model.AccountRegisterDTO;
import cn.itcast.wanxinp2p.common.domain.BusinessException;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.common.domain.StatusCode;
import cn.itcast.wanxinp2p.common.util.PasswordUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @version 1.0
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private SmsService smsService;

    @Value("${sms.enable}")
    private Boolean smsEnable;

    @Override
    public RestResponse getSMSCode(String mobile) {
        return smsService.getSmsCode(mobile);
    }

    @Override
    public Integer checkMobile(String mobile, String key, String code) {
        smsService.verifySmsCode(key,code);
        QueryWrapper<Account> wrapper=new QueryWrapper<>();//wrapper.eq("mobile",mobile);
        wrapper.lambda().eq(Account::getMobile,mobile);
        int count=count(wrapper);
        return count > 0 ? 1 : 0;
    }

    @Override
    public AccountDTO register(AccountRegisterDTO registerDTO) {
        Account account = new Account();
        account.setUsername(registerDTO.getUsername());
        account.setMobile(registerDTO.getMobile());
        account.setPassword(PasswordUtil.generate(registerDTO.getPassword()));
//        String s = PasswordUtil.generate("123456");
//        System.out.println(s);
        if (smsEnable) {
            account.setPassword(PasswordUtil.generate(account.getMobile()));
        }
        account.setDomain("c");
        account.setStatus(StatusCode.STATUS_OUT.getCode());
        save(account);
        return convertAccountEntityToDTO(account);
    }

    @Override
    public AccountDTO login(AccountLoginDTO accountLoginDTO) {
        Account account = null;
        if (accountLoginDTO.getDomain().equalsIgnoreCase("c")) {
            account = getAccountByMobile(accountLoginDTO.getMobile());//获取c端用户
        } else {
            account = getAccountByUsername(accountLoginDTO.getUsername());//获取b端用户
        }
        if (account == null) {
            throw new BusinessException(AccountErrorCode.E_130104); // 用户不存在
        }
        AccountDTO accountDTO = convertAccountEntityToDTO(account);
        if (smsEnable) {// 如果smsEnable=true，说明是短信验证码登录，不做密码校验
            return accountDTO;
        } //验证密码
        if (PasswordUtil.verify(accountLoginDTO.getPassword(),
                account.getPassword())) {
            return accountDTO;
        }
        throw new BusinessException(AccountErrorCode.E_130105);
    }

    /**
     根据手机获取账户信息
     @param mobile 手机号
     @return 账户实体
     */
    private Account getAccountByMobile(String mobile) {
        return getOne(new QueryWrapper<Account>().lambda()
                .eq(Account::getMobile,mobile));
    }
    /**
     根据用户名获取账户信息
     @param username 用户名
     @return 账户实体
     */
    private Account getAccountByUsername(String username) {
        return getOne(new QueryWrapper<Account>().lambda()
                .eq(Account::getUsername,username));
    }



    private AccountDTO convertAccountEntityToDTO(Account entity) {
        if (entity == null) {
            return null;
        }
        AccountDTO dto = new AccountDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }


}
