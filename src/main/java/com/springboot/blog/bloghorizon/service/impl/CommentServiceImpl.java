package com.springboot.blog.bloghorizon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.bloghorizon.entity.Comment;
import com.springboot.blog.bloghorizon.entity.Post;
import com.springboot.blog.bloghorizon.exception.BlogAPIException;
import com.springboot.blog.bloghorizon.exception.ResourceNotFoundException;
import com.springboot.blog.bloghorizon.payload.CommentDto;
import com.springboot.blog.bloghorizon.repository.CommentRepository;
import com.springboot.blog.bloghorizon.repository.PostRepository;
import com.springboot.blog.bloghorizon.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);

		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));

		// set post to the comment entity
		comment.setPost(post);

		// save comment entity to the database
		Comment newComment = commentRepository.save(comment);
		
		log.info("Posted a new comment successfully.");

		return mapToDto(newComment);
	}

	@Override
	public List<CommentDto> getCommentByPostId(long postId) {

		// retrieving comment by post id
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		log.info("Fetched data for a comment with a given post id successfully.");

		// converting list of comment entities to list of comment dto
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) throws BlogAPIException {

		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));

		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(commentId)));

		if (comment.getPost().getId() != post.getId()) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the selected post");
		}
		
		log.info("Fetched data for a single comment successfully.");

		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {

		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));

		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(commentId)));

		if (comment.getPost().getId() != post.getId()) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the selected post");
		}

		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());

		Comment updatedComment = commentRepository.save(comment);
		
		log.info("Updated comment data successfully.");

		return mapToDto(updatedComment);
	}

	@Override
	public void deleteComment(long postId, long commentId) {

		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));

		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(commentId)));
		
		if (comment.getPost().getId() != post.getId()) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the selected post");
		}
		
		log.info("Deleted comment data successfully");
		
		commentRepository.delete(comment);

	}

	private CommentDto mapToDto(Comment comment) {

		CommentDto commentDto = mapper.map(comment, CommentDto.class);

		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {

		Comment comment = mapper.map(commentDto, Comment.class);
		
		return comment;
	}

}