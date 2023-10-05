package ua.com.owu.crm_programming_school.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.services.orderService.OrderService;
import ua.com.owu.crm_programming_school.views.Views;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name="orders")
public class OrdersController {

    private final OrderService orderService;
    public OrdersController(@Qualifier("orderServiceImpl1") OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "get all orders",
            description = "Get all orders",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderPaginatedResponse.class
                                    )
                    ))
            })
    @JsonView(Views.Level3.class)
    public ResponseEntity<OrderPaginated> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "-id") String order,
            @RequestParam(defaultValue = "25") int size) {
        return orderService.getAllOrders(page,order,size);
    }

    @Operation(summary = "get order by id",
            description = "Get order by id",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)))
            })
    @GetMapping("/{id}")
    @JsonView(Views.Level3.class)
    public ResponseEntity<Order> getById(@PathVariable Integer id){
        return orderService.getById(id);
    }

    @Operation(summary = "update order by id",
            description = "Update order by id",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseError.class),
                                    examples = @ExampleObject(name = "errorResponse",
                                            value = "{\"error\": \"One or more fields are not valid\", \"code\": 400, \"details\": {\"courseFormat\": \"Must match the pattern 'static|online|^$'\"}}")))})
    @PatchMapping("/{id}")
    @JsonView(Views.Level3.class)
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody OrderEdit orderEdit, Principal principal){
        return orderService.updateOrder(id, orderEdit, principal);
    }

    @Operation(summary = "get comments by order id",
            description = "Get comments by order id",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentListResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)))
            })
    @GetMapping("/{order_id}/comments")
    @JsonView(Views.Level3.class)
    public ResponseEntity<List<Comment>> getComments(@PathVariable Integer order_id){
        return orderService.getComments(order_id);
    }

    @Operation(summary = "create comment",
            description = "Create comment",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentListResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)))
            })
    @PostMapping("/{order_id}/comments")
    @JsonView(Views.Level3.class)
    public ResponseEntity<List<Comment>> createComment(@PathVariable Integer order_id, @RequestBody CommentRequest commentRequest, Principal principal){
        return orderService.createComment(order_id, commentRequest, principal);
    }
}
