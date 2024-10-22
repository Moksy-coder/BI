package com.moksy.springbootinit.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ExcelUtils {
    public static String excelToCsv(MultipartFile multipartFile) {
        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.info("表格处理错误", e);
        }
        //判断用户数据是否为空
        if(CollUtil.isEmpty(list)){
            return "";
        }

        //转换为csv
        StringBuilder stringBuilder=new StringBuilder();
        //读取表头(第一行)
        LinkedHashMap<Integer,String> headermap=(LinkedHashMap<Integer, String>)list.get(0);
        List<String> headerList=headermap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
        stringBuilder.append(StringUtils.join(headerList,','));
        //读取完表头数据之后，从第一行数据开始读取
        for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<Integer,String> datamap=(LinkedHashMap<Integer, String>)list.get(i);
            List<String> datalist=headermap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
            stringBuilder.append(StringUtils.join(datalist,',')).append("\n");
        }
        return stringBuilder.toString();
    }
}
