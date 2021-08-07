package com.tony.mulitithread.controller;

import com.tony.mulitithread.domain.FileInfo;
import com.tony.mulitithread.domain.ResultBean;
import com.tony.mulitithread.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;

    @GetMapping
    public ResultBean<List<FileInfo>> queryList() {
        return new ResultBean<>(fileService.queryFiles());
    }

    @GetMapping("/mulitit")
    public ResultBean<List<FileInfo>> queryFilesByMulitiThread() {
        return new ResultBean<>(fileService.queryFilesByMulitiThread());
    }

    @GetMapping("/more")
    public ResultBean<List<FileInfo>> queryMore() {
        return new ResultBean<>(fileService.queryMore());
    }

    @GetMapping("/more/mulitit")
    public ResultBean<List<FileInfo>> queryMoreMulitit() {
        return new ResultBean<>(fileService.queryMoreMulitit());
    }

}
