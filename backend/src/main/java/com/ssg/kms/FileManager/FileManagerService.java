package com.ssg.kms.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssg.kms.user.User;
import com.ssg.kms.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileManagerService {
	
	private final UserRepository userRepository;
	private final FileManagerRepository fileManagerRepository;
	
	@Value("${filemanager.dir}")
	private String fileDir;
	
	@Transactional
	public List<FileManager> createFile(MultipartFile[] files, Optional<User> user) throws Exception {
		
		/*
		 * 
		 * 		String realPath = "/Users/hvvany/Desktop/OISO_BE/last_pjt/trip/src/main/resources/static/imgs";  // 스프링 부트에서 파일 저장 시 상대경로로 하면 경로 못찾음
				String today = new SimpleDateFormat("yyMMdd").format(new Date());
		 * */
				File folder = new File(fileDir);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		List<FileManager> fileManagers = new ArrayList<>();
		for(MultipartFile mfile : files) {
			
			//    private String originFileName;
		    String originFileName = mfile.getOriginalFilename();
		    if(!originFileName.isEmpty()) {
		    	String storeFileName = UUID.randomUUID().toString() 
		    			+ originFileName.substring(originFileName.lastIndexOf('.'));
		    	
				FileManager fileManager = FileManager.builder()
						.originFileName(fileDir)
						.storeFileName(storeFileName)
						.build();
				
				mfile.transferTo(new File(fileDir, storeFileName));
			    fileManagers.add(fileManager);		    	
		    }
//		    private String storeFileName;
		}
		 
		
		fileManagerRepository.saveAll(fileManagers);
		return fileManagers;
	}
	
	@Transactional(readOnly = true)
	public InputStreamResource readFile() throws FileNotFoundException{
		String filepath = fileDir + "/2024-02-01 15 11 39.png";	        
		        // 파일 생성 및 복사
		File file = new File(filepath);
				
		InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
				
		return isr;
	}
//@Transactional(readOnly = true)
    
}
