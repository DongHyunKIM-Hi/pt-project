package com.example.ptproject.trainer;

import com.example.ptproject.common.ApiResponse;
import com.example.ptproject.trainer.dto.CreateTrainerRequest;
import com.example.ptproject.trainer.dto.TrainerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Trainer", description = "트레이너 등록·조회 API")
@RestController
@RequestMapping("/v1/trainers")
public class TrainerController {

    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png");
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Operation(summary = "트레이너 등록", description = "프로필 사진은 선택 항목이다. jpg/jpeg/png만 허용한다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "등록 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 값이 올바르지 않음 (허용되지 않는 확장자 등)")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<TrainerResponse>> createTrainer(
            @Valid @ModelAttribute CreateTrainerRequest request,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        String savedFileName = image == null || image.isEmpty()
                ? null
                : saveProfileImage(image);

        Trainer trainer = trainerService.registerTrainer(
                request.getName(), request.getSpecialty(), savedFileName);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(TrainerResponse.from(trainer)));
    }

    @Operation(summary = "트레이너 목록 조회", description = "specialty를 지정하면 전문 분야로 필터링한다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TrainerResponse>>> getTrainers(
            @RequestParam(value = "specialty", required = false) String specialty) {

        List<TrainerResponse> trainers = trainerService.getTrainers(specialty).stream()
                .map(TrainerResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(trainers));
    }

    @Operation(summary = "트레이너 프로필 사진 조회")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 트레이너이거나 등록된 사진이 없음")
    })
    @GetMapping("/{trainer-id}/profile-image")
    public ResponseEntity<Resource> getProfileImage(@PathVariable("trainer-id") long trainerId) throws IOException {
        Trainer trainer = trainerService.getTrainer(trainerId);
        if (trainer.getProfileImageFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = Paths.get("./uploads/" + trainer.getProfileImageFileName());
        Resource resource = new InputStreamResource(Files.newInputStream(filePath));
        String contentType = Files.probeContentType(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    private String saveProfileImage(MultipartFile image) throws IOException {
        String originalFileName = image.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("허용되지 않는 확장자입니다: " + extension);
        }

        String savedFileName = UUID.randomUUID() + "." + extension;
        Path savePath = Paths.get("./uploads/" + savedFileName);
        Files.createDirectories(savePath.getParent());
        image.transferTo(savePath);

        return savedFileName;
    }
}
