package com.ssg.kms.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.mapping.GetPostMapping;
import com.ssg.kms.post.Post;
import com.ssg.kms.post.PostRepository;
import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagPostService {
    private final TagRepository tagRepository;
    private final TagPostRepository tagPostRepository;
    private final PostRepository postRepository;
    
    
    @Transactional(readOnly = true)
    public String readTagByPostId(Long postId, Optional<User> user) {
    	List<TagPost> tagPosts = tagPostRepository.findAllByPostId(postId);
    	StringBuilder tagPostsSB = new StringBuilder();
    	for(TagPost tagPost : tagPosts) {
    		tagPostsSB.append(tagPost.getTag().getName());
    		tagPostsSB.append(" ");
    	}
    	return tagPostsSB.toString();
    }
    
}
