package com.example.ptproject.reservation;

import com.example.ptproject.common.ApiResponse;
import com.example.ptproject.reservation.dto.CreateReservationRequest;
import com.example.ptproject.reservation.dto.ReservationResponse;
import com.example.ptproject.reservation.dto.UpdateReservationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Reservation", description = "PT 예약 관리 API")
@RestController
@RequestMapping("/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "PT 예약 등록", description = "트레이너, 회원 이름, 예약 시간을 받아 새 예약을 생성한다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "예약 생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 값이 올바르지 않음 (과거 시간 등)"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 트레이너"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "같은 트레이너·같은 시간대 중복 예약")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(
            @Valid @RequestBody CreateReservationRequest request) {

        Reservation reservation = reservationService.createReservation(
                request.getTrainerId(), request.getMemberName(), request.getStartTime());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(ReservationResponse.from(reservation)));
    }

    @Operation(summary = "예약 단건 조회")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 예약")
    })
    @GetMapping("/{reservation-id}")
    public ResponseEntity<ApiResponse<ReservationResponse>> getReservation(
            @PathVariable("reservation-id") long reservationId) {

        Reservation reservation = reservationService.getReservation(reservationId);
        return ResponseEntity.ok(ApiResponse.success(ReservationResponse.from(reservation)));
    }

    @Operation(summary = "예약 목록 조회", description = "trainerId를 지정하면 해당 트레이너의 예약만 필터링한다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getReservations(
            @RequestParam(value = "trainerId", required = false) Long trainerId) {

        List<ReservationResponse> reservations = reservationService.getReservations(trainerId).stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(reservations));
    }

    @Operation(summary = "예약 시간 변경", description = "예약 시간만 부분 수정한다. 트레이너·회원 이름은 변경할 수 없다.")
    @PatchMapping("/{reservation-id}")
    public ResponseEntity<ApiResponse<ReservationResponse>> updateReservation(
            @PathVariable("reservation-id") long reservationId,
            @Valid @RequestBody UpdateReservationRequest request) {

        Reservation reservation = reservationService.updateReservation(reservationId, request.getStartTime());
        return ResponseEntity.ok(ApiResponse.success(ReservationResponse.from(reservation)));
    }

    @Operation(summary = "예약 취소")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "취소 성공, 본문 없음"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 예약")
    })
    @DeleteMapping("/{reservation-id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservation-id") long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
