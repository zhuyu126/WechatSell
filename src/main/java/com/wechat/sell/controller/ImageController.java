package com.wechat.sell.controller;

import com.UpYun;
import com.upyun.UpException;
import com.wechat.sell.VO.ResultVO;

import com.wechat.sell.config.UpYunConfig;
import com.wechat.sell.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private UpYunConfig upYunConfig;

    @PostMapping("/upload")
    public ResultVO upload(@RequestParam("file_date")MultipartFile multipartFile) throws Exception {
        UpYun upYun=new UpYun(upYunConfig.getBucketName(),upYunConfig.getUsername(),upYunConfig.getPassword());
        String fileName = String.format("%s.%s", UUID.randomUUID().toString(), multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1));
        upYun.writeFile(fileName,multipartFile.getInputStream(),true, new HashMap<>());
        Map map=new HashMap<>();
        map.put("fileName",fileName);
        return ResultVOUtil.success();
    }

}
