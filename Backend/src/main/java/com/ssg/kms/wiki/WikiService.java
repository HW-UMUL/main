package com.ssg.kms.wiki;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiService {
    private final WikiRepository wikiRepository;
    
    @Transactional
    public Wiki createWiki(WikiDTO wikiDto, Optional<User> user) {
    	Wiki wiki = Wiki.builder()
    			.title(wikiDto.getTitle())
    			.content(wikiDto.getContent())
    			.tag(wikiDto.getTag())
    			.date(new Date())
    			.user(user.get())
    			.build();
    	
		return wikiRepository.save(wiki);
    }
    
    @Transactional(readOnly = true)
    public Wiki readWiki(Long wikiId, Optional<User> user) {
		return wikiRepository.findById(wikiId).get();
    }
    
    @Transactional
    public Wiki updateWiki(Long wikiId, WikiDTO wikiDto, Optional<User> user) {
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	
    	wiki.setTitle(wikiDto.getTitle());
    	wiki.setContent(wikiDto.getContent());
    	wiki.setTag(wikiDto.getTag());
    	wiki.setDate(new Date());
    	
		return wikiRepository.save(wiki);
    }

    @Transactional
    public Wiki deleteWiki(Long wikiId, Optional<User> user) {
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	wiki.setUser(null);
    	wikiRepository.deleteById(wikiId);
    	wiki.setUser(user.get());
    	return wiki;
    }
}
