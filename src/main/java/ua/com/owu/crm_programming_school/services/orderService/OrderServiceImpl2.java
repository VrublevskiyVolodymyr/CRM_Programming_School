package ua.com.owu.crm_programming_school.services.orderService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ua.com.owu.crm_programming_school.models.*;

import java.security.Principal;
import java.util.List;

@Service
public class OrderServiceImpl2 implements OrderService{

    @Override
    public ResponseEntity<OrderPaginated> getAllOrders(int page, String orderBy, int size) {
        return null;
    }

    @Override
    public ResponseEntity<Order> getById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Order> updateOrder(Integer id, OrderEdit orderEdit, Principal principal) {
        return null;
    }

    @Override
    public ResponseEntity<List<Comment>> getComments(Integer orderId) {
        return null;
    }

    @Override
    public ResponseEntity<List<Comment>> createComment(Integer orderId, CommentRequest commentRequest, Principal principal) {
        return null;
    }
}
