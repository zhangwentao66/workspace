package com.huiminpay.merchant.service.impl;

import com.huiminpay.merchant.dto.StoreStaffDTO;
import com.huiminpay.merchant.mapper.StoreStaffMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huiminpay.merchant.service.IStoreStaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘光辉
 * @since 2021-09-03
 */
@Slf4j
@Service
@Transactional
public class StoreStaffServiceImpl extends ServiceImpl<StoreStaffMapper, StoreStaffDTO> implements IStoreStaffService {

}
