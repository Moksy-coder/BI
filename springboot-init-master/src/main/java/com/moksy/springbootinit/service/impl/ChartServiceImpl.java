package com.moksy.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moksy.springbootinit.model.entity.Chart;
import com.moksy.springbootinit.service.ChartService;
import com.moksy.springbootinit.mapper.ChartMapper;
import org.springframework.stereotype.Service;

/**
* @author wz的电脑
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2024-10-18 15:06:18
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}




