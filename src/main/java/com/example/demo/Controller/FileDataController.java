package com.example.demo.Controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.FileData;
import com.example.demo.Service.FiledataService;

@RestController
public class FileDataController {

    @Autowired
    FiledataService filedataService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<FileData> upload(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(filedataService.save(file));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/file/{uuid}")
    public ResponseEntity<Resource> getFile(@PathVariable("uuid") String uuid) throws Exception {
        Resource file = filedataService.load(uuid);
        FileData filedata = filedataService.getFileData(uuid);
        String filename = URLEncoder.encode(StringUtils.cleanPath(filedata.getFileName()), "utf-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType(filedata.getContentType()))
                .body(file);
    }
}
