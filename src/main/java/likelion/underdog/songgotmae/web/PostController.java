package likelion.underdog.songgotmae.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.underdog.songgotmae.domain.post.Post;
import likelion.underdog.songgotmae.domain.post.PostRepository;
import likelion.underdog.songgotmae.domain.post.PostService;
import likelion.underdog.songgotmae.web.dto.PostDetailFindRequestDto;
import likelion.underdog.songgotmae.web.dto.PostDetailResponseDto;
import likelion.underdog.songgotmae.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Tag(name = "Post API", description = "게시글 관련 API입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @Operation(summary = "게시글 작성 api 입니다. - 테스트 완료 (황제철)")
    @PostMapping("posts/new")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostDto.CreateRequestDto requestBody, BindingResult bindingResult) {
        PostDto.SaveResponseDto saveResponseDto = postService.createPost(requestBody);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saveResponseDto);
    }

    @Operation(summary = "게시글 전체 조회 api 입니다. - 테스트 완료 (황제철)")
    @GetMapping("posts/all")
    public ResponseEntity<?> findAllPosts() {
        List<PostDto.FindResponseDto> allPosts = postService.findAllPosts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allPosts);
    }

    @Operation(summary = "검열 통과된 게시글 전체 조회 api 입니다.")
    @GetMapping("posts/all/approved")
    public ResponseEntity<?> findApprovedPosts() {
        List<PostDto.FindResponseDto> approvedPosts = postService.findApprovedPosts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(approvedPosts);
    }

    @Operation(summary = "멤버가 자신이 작성한 게시글을 조회할 수 있는 api 입니다.")
    @GetMapping("posts/wroteBy/me")
    public ResponseEntity<?> findMemberPosts() {
        List<PostDto.FindResponseDto> memberPosts = postService.findMemberPosts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberPosts);
    }

    @Operation(summary = "게시글 하나의 디테일을 조회할 수 있는 api입니다.")
    @GetMapping("posts/{postId}")
    public ResponseEntity<?> findPostById(@NotNull @PathVariable Long postId) {
        PostDetailResponseDto responseBody = postService.findPostById(new PostDetailFindRequestDto(postId));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/posts/orderedByCreatedAt")
    public ResponseEntity<Page<PostDto.FindResponseDto>> findAllPostsOrderByCreatedAt(
            @RequestParam(name = "pageNumber") int pageNumber,
            @RequestParam(name = "pageSize") int pageSize) {

        // 페이지 번호나 페이지 크기가 유효하지 않은 경우의 예외처리
        if (pageNumber < 0 || pageSize <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PostDto.FindResponseDto> posts = postService.findAllPostsOrderByCreatedAt(pageable);

        // 조회된 게시글이 없는 경우의 예외처리
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/orderedByOpinionCount")
    public ResponseEntity<Page<PostDto.FindResponseDto>> findAllPostsOrderByOpinionCount(
            @RequestParam(name = "pageNumber") int pageNumber,
            @RequestParam(name = "pageSize") int pageSize) {

        // 페이지 번호나 페이지 크기가 유효하지 않은 경우의 예외처리
        if (pageNumber < 0 || pageSize <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PostDto.FindResponseDto> posts = postService.findAllPostsOrderByOpinionCount(pageable);

        // 조회된 게시글이 없는 경우의 예외처리
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(posts);
    }


    @GetMapping("posts/search")
    public ResponseEntity<Page<PostDto.PostSearchRequestDto>> searchPost(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().build();
        }

        PostDto.PostSearchRequestDto requestDto = new PostDto.PostSearchRequestDto(keyword, page, size);
        Page<PostDto.PostSearchRequestDto> posts = postService.searchPost(requestDto);

        return ResponseEntity.ok(posts);
    }

    @PutMapping("/posts/{postId}")
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 수정된 경우")
    @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없는 경우")
    public ResponseEntity<PostDto.SaveResponseDto> modifyPost(
            @PathVariable Long postId,
            @RequestBody PostDto.ModifyRequestDto requestBody) {
        PostDto.SaveResponseDto saveResponseDto = postService.modifyPost(postId, requestBody);
        return ResponseEntity.ok(saveResponseDto);
    }

}
