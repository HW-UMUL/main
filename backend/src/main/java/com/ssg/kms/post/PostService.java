package com.ssg.kms.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.like.post.PostLikeRepository;
import com.ssg.kms.star.post.PostStarRepository;
import com.ssg.kms.table.TableRepository;
import com.ssg.kms.table.Tables;
import com.ssg.kms.tag.Tag;
import com.ssg.kms.tag.TagPost;
import com.ssg.kms.tag.TagPostRepository;
import com.ssg.kms.tag.TagRepository;
import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	
    private final PostRepository postRepository;
    private final TableRepository tableRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostStarRepository postStarRepository;
    private final TagRepository tagRepository;
    private final TagPostRepository tagPostRepository;
    
    // 메인
    @Transactional
    public Post createPost(PostDTO postDto, Optional<User> user) {
    	
    	Tables table = tableRepository.findById(1L).get();

    	Post post = Post.builder()
    			.title(postDto.getTitle())
    			.content(postDto.getContent())
//    			.category(postDto.getCategory())
//    			.tag(postDto.getTag())
    			.date(new Date())
    			.user(user.get())
    			.table(table)
    			.build();
    	
    	createTags(postDto, post);
    	
		return post;
    }
    
    // 테이블
    @Transactional
    public Post createPost(Long tableId, PostDTO postDto, Optional<User> user) {
    	
    	Tables table = tableRepository.findById(tableId).get();
    	
    	Post post = Post.builder()
    			.title(postDto.getTitle())
    			.content(postDto.getContent())
//    			.category(postDto.getCategory())
//    			.tag(postDto.getTag())
    			.date(new Date())
    			.user(user.get())
    			.table(table)
    			.build();
    	    	
    	createTags(postDto, post);

		return post;
    }
    
    @Transactional(readOnly = true)
    public Post readPost(Long postId, Optional<User> user) {
		return postRepository.findById(postId).get();
    }

    @Transactional(readOnly = true)
    public List<Post> readAllPost(Optional<User> user) {
		return postRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Post> readMyPost(Optional<User> user) {
		return postRepository.findAllByUserId(user.get().getId());
    }


    @Transactional
    public Post updatePost(Long postId, PostDTO postDto, Optional<User> user) {
    	Post post = postRepository.findById(postId).get();
    	
    	post.setTitle(postDto.getTitle());
    	post.setContent(postDto.getContent());
//    	post.setCategory(postDto.getCategory());
//    	post.setTag(postDto.getTag());
    	post.setDate(new Date());
    	
    	// tag post 연결된거 지우기
    	tagPostRepository.deleteAllByPostId(postId);
    	
    	// 새로 생성
    	createTags(postDto, post);

    	return post;
    }

    @Transactional
    public Post deletePost(Long postId, Optional<User> user) {
    	Post post = postRepository.findById(postId).get();
    	
    	postLikeRepository.deleteAllByPostId(post.getId());
    	postStarRepository.deleteAllByPostId(post.getId());
    	
    	post.setUser(null);
    	postRepository.deleteById(postId);
    	post.setUser(user.get());
    	return post;
    }

    @Transactional
    public List<Post> searchPost(String searchKeyword, Optional<User> user){
        return postRepository.findAllByTitleContaining(searchKeyword);
    }    
    
    // 태그 분리
    public String[] splitTag(String tags){
    	String sepStr = "MQcc44ZY687GhWB2rzVc";
    	tags = tags.replaceAll(",", " ").replaceAll("\\s+|,", sepStr);    	
    	String[] tagArr = tags.split(sepStr);
    	return tagArr;
    }
    
    // 태그 생성
    public void createTags(PostDTO postDto, Post post){
    	
    	String[] strTags = splitTag(postDto.getTag());
    	List<Tag> tags = new ArrayList<>();
    	List<TagPost> tagPosts = new ArrayList<>();
    	
    	for(String str : strTags) {
    		    		
    		Optional<Tag> foundTag = tagRepository.findByName(str);
    		Tag tag;
    		if(foundTag.isEmpty()) {
        		tag = Tag.builder()
        				.name(str)
        				.build();    			
        		tags.add(tag);
    		} else {
    			tag = foundTag.get();
    		}
    		
    		
    		TagPost tagPost = TagPost.builder()
    				.post(post)
    				.tag(tag)
    				.build();

    		tagPosts.add(tagPost);
    	}
    	
    	postRepository.save(post);
    	tagRepository.saveAll(tags);
    	tagPostRepository.saveAll(tagPosts);
    	    	
    }
}
