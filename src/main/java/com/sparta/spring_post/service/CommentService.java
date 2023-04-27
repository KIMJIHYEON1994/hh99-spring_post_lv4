package com.sparta.spring_post.service;

import com.sparta.spring_post.dto.CommentRequestDto;
import com.sparta.spring_post.dto.UserResponseDto;
import com.sparta.spring_post.entity.Comment;
import com.sparta.spring_post.entity.Post;
import com.sparta.spring_post.entity.Users;
import com.sparta.spring_post.jwt.JwtUtil;
import com.sparta.spring_post.repository.CommentRepository;
import com.sparta.spring_post.repository.PostRepository;
import com.sparta.spring_post.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    // 댓글 등록
    @Transactional
    public UserResponseDto<Comment> addComment(CommentRequestDto commentRequestDto, Users user) {
        Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        Comment comment = new Comment(user, commentRequestDto, post);
        commentRepository.saveAndFlush(comment);
        return UserResponseDto.setSuccess("댓글이 등록되었습니다.");
    }

    // 댓글 수정
    @Transactional
    public UserResponseDto<Comment> updateComment(Long id, CommentRequestDto commentRequestDto, Users user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );

        if (comment.getUsers().getUsername().equals(user.getUsername()) || user.getRole().equals(user.getRole().ADMIN)) {
            comment.update(commentRequestDto);
            return UserResponseDto.setSuccess("댓글이 수정되었습니다.");
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

    }

    // 댓글 삭제
    @Transactional
    public UserResponseDto<Comment> deleteComment(Long id, Users user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );

        if (comment.getUsers().getUsername().equals(user.getUsername()) || user.getRole().equals(user.getRole().ADMIN)) {
            commentRepository.delete(comment);
            return UserResponseDto.setSuccess("댓글 삭제 성공");
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

    }


    // 토큰 체크
    public Users checkJwtToken(HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 접근 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            return user;

        }
        return null;
    }
}
