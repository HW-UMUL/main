package com.ssg.kms.FileManager;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileManagerRepository extends JpaRepository<FileManager, Long> {

}
