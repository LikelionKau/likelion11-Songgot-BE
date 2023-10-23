package likelion.underdog.songgotmae.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.underdog.songgotmae.domain.member.service.MemberService;
import likelion.underdog.songgotmae.web.dto.member.LoginRequestDto;
import likelion.underdog.songgotmae.web.dto.member.MemberRequestDto;
import likelion.underdog.songgotmae.web.dto.member.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Member API", description = "회원 관련 API입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원가입 - 테스트 완료 (황제철)")
    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody @Valid MemberRequestDto.JoinRequestDto joinRequest, BindingResult bindingResult) {
        MemberResponseDto.JoinResponseDto responseDto = memberService.joinMember(joinRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @Operation(summary = "관리자 계정 생성 API (임시) - 테스트 완료 (황제철)")
    @PostMapping("/join/admin")
    public ResponseEntity<?> joinAdmin(@RequestBody @Valid MemberRequestDto.JoinRequestDto joinRequest, BindingResult bindingResult) {
        MemberResponseDto.JoinResponseDto responseDto = memberService.joinAdminMember(joinRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody @Valid LoginRequestDto request, BindingResult bindingResult) {
        MemberResponseDto.LoginResponseDto response = memberService.loginMember(request);
        return ResponseEntity
                .ok(response);
    }

}
