package com.ssg.kms.log.search;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class SearchLogDTO {

	@NotNull
	@Size(min = 1, max = 50)
	private String content;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
    public Date getDate() {
    	this.date = new Date();
        return this.date;
    }
	
//	public SearchLogDTO(String content) {
//		this.content = content ;
//		this.date = new Date();
//	}
	
}
