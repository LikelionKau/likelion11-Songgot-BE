package likelion.underdog.songgotmae.domain.post;

import likelion.underdog.songgotmae.web.dto.PostDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;


import java.util.List;

public interface PostService {
    PostDto.SaveResponseDto createPost(PostDto.CreateRequestDto requestBody);
    PostDto.SaveResponseDto modifyPost(Long postId, PostDto.ModifyRequestDto requestBody);
    PostDto.SaveResponseDto approvePostTrue(Long id);
    PostDto.SaveResponseDto approvePostFalse(Long id);
    List<PostDto.FindResponseDto> findAllPosts();
    Page<PostDto.FindResponseDto> findAllPostsOrderByCreatedAt(Pageable pageable);
    Page<PostDto.FindResponseDto> findAllPostsOrderByOpinionCount(Pageable pageable);
    List<PostDto.FindResponseDto> findApprovedPosts();
    List<PostDto.FindResponseDto> findMemberPosts();

    Page<PostDto.PostSearchRequestDto> searchPost(PostDto.PostSearchRequestDto requestDto);
}
