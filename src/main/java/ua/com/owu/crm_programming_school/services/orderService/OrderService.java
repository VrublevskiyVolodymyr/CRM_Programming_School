package ua.com.owu.crm_programming_school.services.orderService;

import org.springframework.http.ResponseEntity;

import ua.com.owu.crm_programming_school.models.OrderPaginated;


public interface OrderService {

    ResponseEntity<OrderPaginated> getAllOrders(int page, String orderBy, int size);
}
