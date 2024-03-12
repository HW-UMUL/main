package com.ssg.kms.follow;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.user.User;
import com.ssg.kms.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;    
    
    @Transactional
    public Follow checkFollow(String email, Optional<User> user) {
    	
    	User foundUser = userRepository.findByEmail(email);
    	
    	Follow follow = followRepository.findByFollowerAndFollowee(user.get(), foundUser).orElse(null);
    	    	
    	if(follow == null) {
    		Follow newFollow = Follow.builder()
    				.follower(user.get())
    				.followee(foundUser)
        			.build();
    		
    		followRepository.save(newFollow);
    		follow = newFollow;
    		
    	} else {
    		followRepository.deleteById(follow.getId());
    	}
    	    	
    	
    	
		return follow;
    }
    
    @Transactional(readOnly = true)
    public List<Follow> readFollower(String email, Optional<User> user) {
    	return followRepository.findAllByFollowerEmail(email);
    }
    
    @Transactional(readOnly = true)
    public List<Follow> readFollowee(String email, Optional<User> user) {
    	return followRepository.findAllByFolloweeEmail(email);
    }
}
