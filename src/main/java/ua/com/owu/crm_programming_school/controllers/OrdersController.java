package ua.com.owu.crm_programming_school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.owu.crm_programming_school.models.OrderPaginated;
import ua.com.owu.crm_programming_school.services.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name="orders")
public class OrdersController {

    private final OrderService orderService;
    public OrdersController(@Qualifier("orderServiceImpl1") OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(description = "Get all orders",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderPaginated.class
                                    )
                    ))
            })
    public ResponseEntity<OrderPaginated> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "created") String order,
            @RequestParam(defaultValue = "25") int size) {
        return orderService.getAllOrders(page,order,size);
    }

}
