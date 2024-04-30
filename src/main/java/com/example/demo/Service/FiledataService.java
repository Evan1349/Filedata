package com.example.demo.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repository.FiledataRepository;
import com.example.demo.entity.FileData;
import com.example.demo.utils.UuidTools;

@Service
public class FiledataService {

    @Autowired
    FiledataRepository filedataRepository;

    public FileData save(MultipartFile multipartFile) throws IOException {
        FileData saved = new FileData();
        try {
            String uuid = UuidTools.getUUID();
            String uploadDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String path = "C:\\Users\\Evan\\OneDrive\\桌面\\file"; 
            Path subPath = Paths.get(path).resolve(uploadDate);

            if (!Files.exists(subPath)) {
                Files.createDirectories(subPath);
                System.out.println("Folder create success");
            } else {
                System.out.println("Folder is already exist");
            }

            Files.copy(multipartFile.getInputStream(), subPath.resolve(uuid));

            saved.setUuid(uuid);
            saved.setPath(subPath.resolve(uuid).toString());
            saved.setFileName(multipartFile.getOriginalFilename());
            saved.setContentType(multipartFile.getContentType());

            filedataRepository.save(saved);
        } catch (IOException e) {
            System.out.println("Error occurred while saving the file: " + e.getMessage());
            throw new RuntimeException("Failed to save the file: " + e.getMessage(), e);
        }
        return saved;
    }

    public Resource load(String uuid) throws Exception {
        FileData filedata = filedataRepository.findByUuid(uuid);
        Path filePath = Paths.get(filedata.getPath());
        Resource resource = new UrlResource(filePath.toUri());
        return resource;
    }

    public FileData getFileData(String uuid) {
        return filedataRepository.findByUuid(uuid);
    }
}
