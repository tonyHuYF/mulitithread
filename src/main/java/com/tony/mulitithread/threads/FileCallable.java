package com.tony.mulitithread.threads;

import com.tony.mulitithread.domain.FileInfo;
import com.tony.mulitithread.mapper.FileMapper;
import com.tony.mulitithread.utils.SpringContextUtil;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.concurrent.Callable;


public class FileCallable implements Callable<List<FileInfo>> {
    private int start;
    private int num;
    private List<FileInfo> data;

    private static ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();

    public FileCallable(int start, int num) {
        this.start = start;
        this.num = num;
    }


    @Override
    public List<FileInfo> call() throws Exception {
        //获取bean
        FileMapper fileMapper = applicationContext.getBean(FileMapper.class);
        List<FileInfo> fileInfos = fileMapper.queryList(start, num);
        this.data = fileInfos;
        return data;
    }
}
