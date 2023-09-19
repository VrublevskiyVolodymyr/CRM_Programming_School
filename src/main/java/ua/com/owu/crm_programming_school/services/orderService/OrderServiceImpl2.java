package ua.com.owu.crm_programming_school.services.orderService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ua.com.owu.crm_programming_school.models.OrderPaginated;

@Service
public class OrderServiceImpl2 implements OrderService{

    @Override
    public ResponseEntity<OrderPaginated> getAllOrders(int page, String orderBy, int size) {
        return null;
    }
}
