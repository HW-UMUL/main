package com.ssg.kms.wiki;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.category.Category;
import com.ssg.kms.category.CategoryRepository;
import com.ssg.kms.like.wiki.WikiLikeRepository;
import com.ssg.kms.log.WikiLogRepository;
import com.ssg.kms.log.WikiLogService;
import com.ssg.kms.star.wiki.WikiStarRepository;
import com.ssg.kms.table.TableRepository;
import com.ssg.kms.table.Tables;
import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiService {
    private final WikiRepository wikiRepository;
    private final TableRepository tableRepository;
    private final WikiLikeRepository wikiLikeRepository;
    private final WikiStarRepository wikiStarRepository;
    private final WikiLogRepository wikiLogRepository;
    private final CategoryRepository categoryRepository;
    
    private final WikiLogService wikiLogService;
    
    
    @Transactional
    public Wiki createWiki(WikiDTO wikiDto, Optional<User> user) {
    	
    	Tables table = tableRepository.findById(1L).get();
    	Category category = createCategory(wikiDto.getCategory());
    	
    	Wiki wiki = Wiki.builder()
    			.title(wikiDto.getTitle())
    			.content(wikiDto.getContent())
    			.date(new Date())
    			.user(user.get())
    			.category(category)
    			.table(table)
    			.build();
    	
    	wikiRepository.save(wiki);
    	wikiLogService.createWikiLog(wiki, user);
    	    	
		return wiki;
    }
    
    @Transactional
    public Wiki createTableWiki(Long tableId, WikiDTO wikiDto, Optional<User> user) {
    	
    	Tables table = tableRepository.findById(tableId).get();
    	Category category = createCategory(wikiDto.getCategory());
    	
    	Wiki wiki = Wiki.builder()
    			.title(wikiDto.getTitle())
    			.content(wikiDto.getContent())
    			.date(new Date())
    			.user(user.get())
    			.category(category)
    			.table(table)
    			.build();
    	
    	wikiRepository.save(wiki);
    	wikiLogService.createWikiLog(wiki, user);
    	    	
		return wiki;
    }

    
    @Transactional(readOnly = true)
    public Wiki readWiki(Long wikiId, Optional<User> user) {
		return wikiRepository.findById(wikiId).get();
    }
    
    @Transactional
    public Wiki updateWiki(Long wikiId, WikiDTO wikiDto, Optional<User> user) {
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	Category category = createCategory(wikiDto.getCategory());
    	
    	wiki.setTitle(wikiDto.getTitle());
    	wiki.setContent(wikiDto.getContent());
    	wiki.setDate(new Date());
    	wiki.setCategory(category);
    	
    	wikiRepository.save(wiki);
    	wikiLogService.createWikiLog(wiki, user);
    	
    	
		return wiki;
    }

    @Transactional
    public Wiki deleteWiki(Long wikiId, Optional<User> user) {
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	
    	wikiLikeRepository.deleteAllByWikiId(wiki.getId());
    	wikiStarRepository.deleteAllByWikiId(wiki.getId());
    	wikiLogRepository.deleteAllByWikiId(wiki.getId());

    	wiki.setUser(null);
    	wikiRepository.deleteById(wikiId);
    	wiki.setUser(user.get());
    	return wiki;
    }
    
 // 카테고리 생성
    public Category createCategory(String categoryName){

    	Optional<Category> foundCategory = categoryRepository.findByName(categoryName);
    	Category category;
    	
    	if(foundCategory.isEmpty()) {
    		category = Category.builder()
    				.name(categoryName)
    				.build();    				
    	} else {
    		category = foundCategory.get();
    	}
    	
    	return categoryRepository.save(category);
    }
}
