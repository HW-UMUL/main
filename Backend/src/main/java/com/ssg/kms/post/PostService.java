package com.ssg.kms.post;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    
    @Transactional
    public Post createPost(PostDTO postDto, Optional<User> user) {
    	Post post = Post.builder()
    			.title(postDto.getTitle())
    			.content(postDto.getContent())
    			.category(postDto.getCategory())
    			.tag(postDto.getTag())
    			.date(new Date())
    			.user(user.get())
    			.build();
    	
		return postRepository.save(post);
    }
    
    @Transactional(readOnly = true)
    public Post readPost(Long postId, Optional<User> user) {
		return postRepository.findById(postId).get();
    }
    
    @Transactional
    public Post updatePost(Long postId, PostDTO postDto, Optional<User> user) {
    	Post post = postRepository.findById(postId).get();
    	
    	post.setTitle(postDto.getTitle());
    	post.setContent(postDto.getContent());
    	post.setCategory(postDto.getCategory());
    	post.setTag(postDto.getTag());
    	post.setDate(new Date());
    	
		return postRepository.save(post);
    }

    @Transactional
    public Post deletePost(Long postId, Optional<User> user) {
    	Post post = postRepository.findById(postId).get();
    	post.setUser(null);
    	postRepository.deleteById(postId);
    	post.setUser(user.get());
    	return post;
    }
}
