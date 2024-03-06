package com.ssg.kms.mainpage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
	
	@Autowired
	private SearchRepository searchRepository;
	
	public List<SearchModel> postList(){
		return searchRepository.findAll();
	};
	
	public List<SearchModel> postSearchList(String searchKeyword){
		return searchRepository.findByTitleContaining(searchKeyword);
	}

}
