package com.aco.practice.demo1.controller;

import com.aco.practice.demo1.domain.request.dto.FileDto;
import com.aco.practice.demo1.service.UploadService;
import com.aco.practice.demo1.util.ApiResponseResult;
import com.aco.practice.demo1.util.IdUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class UploadFileController {

    @Autowired
    private UploadService uploadService;


    @RequestMapping("/fileupload/{userId}")
    public ResponseEntity testUpload(@RequestParam("file") MultipartFile file, @PathVariable("userId") String userId) throws Exception {
        String path = "E://ci//";
        File parent = new File(path);
        FileDto fileDto = new FileDto();

        //获取原始名称
        String oldName = file.getOriginalFilename();

        //获取文件后缀名
        String extension = "." + FilenameUtils.getExtension(file.getOriginalFilename());

        //生成新的文件名称
        String newFileName = IdUtil.get32UUID() + extension;

        //文件的类型
        String type = file.getContentType();

        //文件上传

        file.transferTo(new File(parent, newFileName));

        //处理上传文件信息
        fileDto.setUserId(userId);
        fileDto.setFileName(newFileName);
        fileDto.setFileSuffix(extension);
        fileDto.setFileType(type);
        fileDto.setFileUrl(path);
        String s = uploadService.UploadFile(fileDto);
        return ResponseEntity.ok().body(ApiResponseResult.ok("上传成功"));
    }

    @RequestMapping("/filedownload")
    public ResponseEntity testDownload(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            File file = new File("D:/file/aaa.txt");
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
            IOUtils.copy(fis,response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ResponseEntity.ok().body(ApiResponseResult.ok("下载成功"));
    }
}
