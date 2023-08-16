package likelion.underdog.songgotmae.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.underdog.songgotmae.domain.post.PostService;
import likelion.underdog.songgotmae.domain.post.PostServiceImpl;
import likelion.underdog.songgotmae.web.dto.CommonResponseDto;
import likelion.underdog.songgotmae.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Post API", description = "게시글 관련 API입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 작성 api 입니다. - 테스트 완료 (황제철)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 작성 완료"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "500", description = "Internal_Serer_Error")
    })
    @PostMapping("/posts/new")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostDto.CreateRequestDto requestBody, BindingResult bindingResult) {
        PostDto.SaveResponseDto saveResponseDto = postService.createPost(requestBody);
        CommonResponseDto<PostDto.SaveResponseDto> response = new CommonResponseDto<>(1, "게시글 작성 성공", saveResponseDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "게시글 전체 조회 api 입니다. - 테스트 완료 (황제철)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 완료"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "500", description = "Internal_Serer_Error")
    })
    @GetMapping("/posts/all")
    public ResponseEntity<?> findAllPosts() {
        List<PostDto.FindResponseDto> allPosts = postService.findAllPosts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allPosts);
    }

    @Operation(summary = "검열 통과된 게시글 전체 조회 api 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 완료"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "500", description = "Internal_Serer_Error")
    })
    @GetMapping("/posts/approved")
    public ResponseEntity<?> findApprovedPosts() {
        List<PostDto.FindResponseDto> approvedPosts = postService.findApprovedPosts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(approvedPosts);
    }

    @Operation(summary = "멤버가 자신이 작성한 게시글을 조회할 수 있는 api 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 완료"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "500", description = "Internal_Serer_Error")
    })
    @GetMapping
    public ResponseEntity<?> findMemberPosts(@PathVariable Long memberId) {
        List<PostDto.FindResponseDto> memberPosts = postService.findMemberPosts(memberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberPosts);
    }

}
