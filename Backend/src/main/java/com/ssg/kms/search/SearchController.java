package com.ssg.kms.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

	@Autowired
	SearchService searchService;
	
	@GetMapping("post")
	public String searchPageForm(Model model, String searchKeyword) {
		
		List<SearchModel> list = null;
		
		if(searchKeyword == null) {
			list = searchService.postList();
		}else {
			list = searchService.postSearchList(searchKeyword);
		}
		
		model.addAttribute("list", list);
		
		return "searchPage";
	}
	
}