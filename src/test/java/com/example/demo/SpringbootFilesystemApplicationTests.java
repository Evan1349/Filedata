package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.demo.Repository.FiledataRepository;

@SpringBootTest
class SpringbootFilesystemApplicationTests {
	
	@Autowired
	FiledataRepository filedataRepository;
	
	@Test
	void contextLoads() {
		
		assertEquals(0, filedataRepository.count());
		
		
	}

}
