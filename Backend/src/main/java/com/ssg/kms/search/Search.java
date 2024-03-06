package com.ssg.kms.search;

import java.util.Date;

import groovy.transform.builder.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SearchModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_id")
	private Integer user_id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "tag")
	private String tag;
	
	@Column(name = "date")
	private Date date;
	
}

