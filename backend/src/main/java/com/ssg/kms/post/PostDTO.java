package com.ssg.kms.post;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
	
    private String title;

    private String content;

//    @DateTimeFormat(iso = ISO.DATE_TIME)
//    private LocalDateTime endDate;

    private String category;
    
    private String tag;
}
