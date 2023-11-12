package likelion.underdog.songgotmae.web;

import likelion.underdog.songgotmae.domain.voc.Voc;
import likelion.underdog.songgotmae.domain.voc.VocService;
import likelion.underdog.songgotmae.web.dto.VocDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vocs")
public class VocController {
    private final VocService vocService;

    @Autowired
    public VocController(VocService vocService) {
        this.vocService = vocService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createPost(@RequestBody @Valid VocDto.CreateRequestDto requestBody, BindingResult bindingResult) {
        VocDto.SaveResponseDto saveResponseDto = vocService.createPost(requestBody);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saveResponseDto);
    }
}

