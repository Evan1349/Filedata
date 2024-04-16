package com.example.demo.Service;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		// uuid
		String uuid = UuidTools.getUUID();
		// path
		String uploadDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		// filename
		String originalFilename = multipartFile.getOriginalFilename();
		// new path
		String path = Paths.get(originalFilename).getParent().toString();
		Path subPath = Paths.get(path).resolve(uploadDate);
		
		if (!Files.exists(subPath)) {
            Files.createDirectories(subPath);
            System.out.println("Folder create success");
        }else {
        	System.out.println("Folder is already exit");
        }
		
		
        Files.copy(multipartFile.getInputStream(), subPath.resolve(uuid));
        
        saved.setUuid(uuid);
        saved.setPath(subPath.resolve(uuid).toString());
        saved.setFlieName(originalFilename);
        
		return saved;
	}
	
	
}
