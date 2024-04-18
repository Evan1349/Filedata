package com.example.demo.Controller;


import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.FileData;
import com.example.demo.Repository.FiledataRepository;
import com.example.demo.Service.FiledataService;


@RestController
public class FileDataController {
	@Autowired
	FiledataService filedataService;
	@Autowired
	FiledataRepository filedataRepository;
	
	@PostMapping("/upload")
	public ResponseEntity<FileData> upload(@RequestParam("file") MultipartFile file){
		
		try {
			return ResponseEntity.ok(filedataService.save(file));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
				
	}
	
	@GetMapping(value = "/uuid/{uuid}")
	public ResponseEntity<Resource> getFile(@PathVariable("uuid") String uuid) throws Exception{
		// 取得檔案資源
		Resource file = filedataService.load(uuid);
		// uuid 查詢檔案實體
		FileData filedata = filedataRepository.findByUuid(uuid);
		//轉碼
		String filename = URLEncoder.encode(filedata.getFlieName(),"utf-8");
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\"")
				.header(HttpHeaders.CONTENT_TYPE, "multipart/form-data")
				.body(file);
	}
	
}
