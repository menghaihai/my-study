package cn.mfj.implthree.service.impl;

import cn.mfj.implthree.entity.DataSourceInfo;
import cn.mfj.implthree.mapper.DataSourceInfoMapper;
import cn.mfj.implthree.service.DataSourceInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author favian.meng on 2021-10-13
 */
@Service
public class DataSourceInfoServiceImpl extends ServiceImpl<DataSourceInfoMapper, DataSourceInfo> implements DataSourceInfoService {
}
