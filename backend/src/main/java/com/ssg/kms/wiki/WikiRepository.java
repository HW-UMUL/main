package com.ssg.kms.wiki;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.post.Post;

public interface WikiRepository extends JpaRepository<Wiki, Long> {

	List<Post> findAllByTitleContaining(String searchKeyword);

}
