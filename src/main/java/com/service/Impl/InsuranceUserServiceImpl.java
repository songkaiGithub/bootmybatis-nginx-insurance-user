package com.service.Impl;

import com.mapper.IInsuranceUserMapper;
import com.po.Insurance;
import com.service.IInsuranceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InsuranceUserServiceImpl implements IInsuranceUserService {
    @Autowired
    private IInsuranceUserMapper iInsuranceUserMapper;

    @Override
    public int save(Insurance insurance) {
        return iInsuranceUserMapper.save(insurance);
    }

    @Override
    public Insurance show(String usercode) {
        Insurance insurance1=iInsuranceUserMapper.show(usercode);
        return insurance1;
    }

    @Override
    public int updateactivatied(String usercode) {
        return iInsuranceUserMapper.updateactivatied(usercode);
    }

    @Override
    public int updatepasswd(Insurance insurance) {
        return iInsuranceUserMapper.updatepasswd(insurance);
    }
}
