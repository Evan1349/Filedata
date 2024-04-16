package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FileData;

public interface FiledataRepository extends JpaRepository<FileData, Long> {
	FileData findByUuid(String Uuid);
}
