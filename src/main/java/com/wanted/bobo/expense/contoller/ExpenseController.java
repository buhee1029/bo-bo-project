package com.wanted.bobo.expense.contoller;

import com.wanted.bobo.common.response.ApiResponse;
import com.wanted.bobo.expense.dto.request.ExpenseFilter;
import com.wanted.bobo.expense.dto.response.ExpenseListResponse;
import com.wanted.bobo.expense.dto.request.ExpenseRequest;
import com.wanted.bobo.expense.dto.response.ExpenseResponse;
import com.wanted.bobo.expense.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "지출 관리")
@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    @Operation(summary = "지출 목록 조회")
    public ApiResponse<ExpenseListResponse> getExpenses(
            @RequestAttribute Long userId,
            @Valid @ParameterObject @ModelAttribute ExpenseFilter filter) {
        return ApiResponse.ok(expenseService.getExpenses(userId, filter));
    }

    @GetMapping("/{id}")
    @Operation(summary = "지출 상세 조회")
    public ApiResponse<ExpenseResponse> getExpense(
            @RequestAttribute Long userId,
            @PathVariable("id") Long expenseId) {
        return ApiResponse.ok(expenseService.getExpense(userId, expenseId));
    }

    @PostMapping
    @Operation(summary = "지출 등록")
    public ApiResponse<ExpenseResponse> registerExpense(
            @RequestAttribute Long userId,
            @Valid @RequestBody ExpenseRequest request) {
        return ApiResponse.created(expenseService.registerExpense(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "지출 수정")
    public ApiResponse<ExpenseResponse> modifyBudget(
            @RequestAttribute Long userId,
            @PathVariable("id") Long expenseId,
            @Valid @RequestBody ExpenseRequest request) {
        return ApiResponse.created(expenseService.modifyExpense(userId, expenseId, request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "지출 합계 처리")
    public ApiResponse<Void> excludeExpense(
            @RequestAttribute Long userId,
            @PathVariable("id") Long expenseId) {
        expenseService.toggleExcludedStatus(userId, expenseId);
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "지출 삭제")
    public ApiResponse<Void> removeExpense(
            @RequestAttribute Long userId,
            @PathVariable("id") Long expenseId) {
        expenseService.removeExpense(userId, expenseId);
        return ApiResponse.noContent();
    }

}
