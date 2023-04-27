package com.sparta.spring_post.service;

import com.sparta.spring_post.dto.PostRequestDto;
import com.sparta.spring_post.dto.PostResponseDto;
import com.sparta.spring_post.dto.UserResponseDto;
import com.sparta.spring_post.entity.Post;
import com.sparta.spring_post.entity.Users;
import com.sparta.spring_post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    // PostRepository 연결
    private final PostRepository postRepository;

    // 전체 게시물 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    // 선택한 게시물 상세 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException(id + "번 게시물이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    // 게시물 등록
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, Users user) {
        Post post = postRepository.saveAndFlush(new Post(postRequestDto, user));
        return new PostResponseDto(post);
    }

    // 게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, Users user) {

        Post post = postRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 글이 존재하지 않습니다."));

        if (post.getUsers().getUsername().equals(user.getUsername()) || user.getRole().equals(user.getRole().ADMIN)) {
            post.update(postRequestDto);
            return new PostResponseDto(post);
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    // 게시물 삭제
    @Transactional
    public UserResponseDto<Post> deletePost(Long id, Users user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException(String.valueOf(UserResponseDto.setFailed("게시글 삭제 실패"))));

        if (post.getUsers().getUsername().equals(user.getUsername()) || user.getRole().equals(user.getRole().ADMIN)) {
            postRepository.delete(post);
            return UserResponseDto.setSuccess("게시글 삭제 성공");
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

    }

}
