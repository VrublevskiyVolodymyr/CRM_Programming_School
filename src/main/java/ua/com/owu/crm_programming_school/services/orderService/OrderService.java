package ua.com.owu.crm_programming_school.services.orderService;

import org.springframework.http.ResponseEntity;

import ua.com.owu.crm_programming_school.models.*;

import java.security.Principal;
import java.util.List;


public interface OrderService {

    ResponseEntity<OrderPaginated> getAllOrders(int page, String orderBy, int size);

    ResponseEntity<Order> getById(Integer id);

    ResponseEntity<Order> updateOrder(Integer id, OrderEdit orderEdit, Principal principal);

    ResponseEntity<List<Comment>> getComments(Integer orderId);

    ResponseEntity<List<Comment>> createComment(Integer orderId, CommentRequest commentRequest, Principal principal);
}
