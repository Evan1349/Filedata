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
		
		// UUID
		String uuid = UuidTools.getUUID();
		// Path
		String uploadDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		// FileName
		String originalFilename = multipartFile.getOriginalFilename();
		// New Path
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
	
		public Resource load(String uuid) throws Exception {
			
			// 透過UUID取得檔案位置
			FileData filedata = filedataRepository.findByUuid(uuid);
			// 將file.getPath()<String>轉變為Path Object 就可以對路徑操作處理
			Path filePath = Paths.get(filedata.getPath());	
			//使用 Spring 工具 UrlResource 取得檔案並回傳(Resource型態方便在網路傳遞)
			Resource resource = new UrlResource(filePath.toUri());
			return resource;
		}
	
	
	
}
