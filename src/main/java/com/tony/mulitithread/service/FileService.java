package com.tony.mulitithread.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tony.mulitithread.domain.FileInfo;
import com.tony.mulitithread.domain.FileInfo2;
import com.tony.mulitithread.domain.FileInfo3;
import com.tony.mulitithread.mapper.File2Mapper;
import com.tony.mulitithread.mapper.File3Mapper;
import com.tony.mulitithread.mapper.FileMapper;
import com.tony.mulitithread.threads.FileCallable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class FileService {
    @Resource
    private FileMapper fileMapper;
    @Resource
    private File2Mapper file2Mapper;
    @Resource
    private File3Mapper file3Mapper;


    /**
     * 以多线程形式查询单表
     */
    public List<FileInfo> queryFilesByMulitiThread() {
        //表总数
        Integer total = fileMapper.selectCount(new QueryWrapper<>());

        //创建一个线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        List<Callable<List<FileInfo>>> taskList = new ArrayList<>();

        List<FileInfo> result = new ArrayList<>();

        //根据表总数确定查询次数
        int selectNum = total / 100000;
        if (total % 100000 != 0) {
            selectNum += 1;
        }

        for (int i = 0; i <= selectNum; i++) {
            //用多线程查询,先创建callable
            int start = 100000 * i;
            int num = 100000;

            taskList.add(new FileCallable(start, num));
        }

        long startTime = 0;
        long endTime = 0;
        try {
            startTime = System.currentTimeMillis();
            List<Future<List<FileInfo>>> futureList = threadPoolExecutor.invokeAll(taskList);

            if (ObjectUtil.isNotEmpty(futureList)) {
                for (Future<List<FileInfo>> data : futureList) {
                    result.addAll(data.get());
                }
            }

            endTime = System.currentTimeMillis();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            threadPoolExecutor.shutdown();
        }

        System.out.println("=====查询数据总条数：" + result.size());
        System.out.println("=====多线程查询耗时：" + (endTime - startTime));

        return result;
    }

    /**
     * 以单线程形式查询单表
     */
    public List<FileInfo> queryFiles() {
        long startTime = System.currentTimeMillis();
        List<FileInfo> fileInfos = fileMapper.selectList(new QueryWrapper<>());
        long endTime = System.currentTimeMillis();
        System.out.println("=====查询数据总条数：" + fileInfos.size());
        System.out.println("=====单线程查询耗时：" + (endTime - startTime));

        return fileInfos;
    }

    /**
     * 以单线程形式查询多表
     */
    public List<FileInfo> queryMore() {

        long startTime = System.currentTimeMillis();

        List<FileInfo2> fileInfo2s = file2Mapper.selectList(new QueryWrapper<>());
        List<FileInfo3> fileInfo3s = file3Mapper.selectList(new QueryWrapper<>());

        long endTime = System.currentTimeMillis();

        List<FileInfo> result = new ArrayList<>();

        fileInfo2s.forEach(p -> {
            FileInfo temp = new FileInfo();
            BeanUtil.copyProperties(p, temp);
            result.add(temp);
        });

        fileInfo3s.forEach(p -> {
            FileInfo temp = new FileInfo();
            BeanUtil.copyProperties(p, temp);
            result.add(temp);
        });

        System.out.println("表t_file_2条数：" + fileInfo2s.size());
        System.out.println("表t_file_3条数：" + fileInfo3s.size());
        System.out.println("合并展示条数：" + result.size());
        System.out.println("=====单线程所有查询所耗时间：" + (endTime - startTime));

        return result;
    }


    /**
     * 以多线程形式查询多表
     */
    public List<FileInfo> queryMoreMulitit() {
        //创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        List<FileInfo> result = new ArrayList<>();

        try {
            long startTime = System.currentTimeMillis();
            Future<List<FileInfo2>> file2Future = threadPoolExecutor.submit(() -> file2Mapper.selectList(new QueryWrapper<>()));
            Future<List<FileInfo3>> file3Future = threadPoolExecutor.submit(() -> file3Mapper.selectList(new QueryWrapper<>()));

            List<FileInfo2> fileInfo2s = file2Future.get();
            List<FileInfo3> fileInfo3s = file3Future.get();

            long endTime = System.currentTimeMillis();

            fileInfo2s.forEach(p -> {
                FileInfo temp = new FileInfo();
                BeanUtil.copyProperties(p, temp);
                result.add(temp);
            });

            fileInfo3s.forEach(p -> {
                FileInfo temp = new FileInfo();
                BeanUtil.copyProperties(p, temp);
                result.add(temp);
            });

            System.out.println("表t_file_2条数：" + fileInfo2s.size());
            System.out.println("表t_file_3条数：" + fileInfo3s.size());
            System.out.println("合并展示条数：" + result.size());
            System.out.println("=====多线程所有查询所耗时间：" + (endTime - startTime));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}
