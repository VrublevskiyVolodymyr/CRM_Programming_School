package ua.com.owu.crm_programming_school.services.orderService;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ua.com.owu.crm_programming_school.dao.OrderDAO;
import ua.com.owu.crm_programming_school.models.Order;
import ua.com.owu.crm_programming_school.models.OrderPaginated;

@Service
@AllArgsConstructor
public class OrderServiceImpl1 implements OrderService {
    private OrderDAO orderDAO;

    public ResponseEntity<OrderPaginated> getAllOrders(int page, String order, int size) {

        System.out.println(order);
        Sort.Direction sortDirection = Sort.Direction.ASC;
        String sortBy;

        if (order != null && !order.isEmpty()) {
            if (order.startsWith("-")) {
                sortDirection = Sort.Direction.DESC;
                sortBy = order.substring(1);
            } else {
                sortBy = order;
            }
        } else {
            sortBy = order;
        }
        int totalRecords = (int) orderDAO.count();
        int maxPage = (int) Math.ceil((double) totalRecords / size);
        int originalPage = page;

        if (page > maxPage) {
            page = maxPage;
        }
        if (page < 1) {
            page = 1;

        }

        Pageable pageable = PageRequest.of(page-1, size, sortDirection, sortBy);

        Page<Order> orders = orderDAO.findAll(pageable);

        OrderPaginated orderPaginated = new OrderPaginated();
        orderPaginated.setTotal_items((int) orders.getTotalElements());
        orderPaginated.setTotal_pages(orders.getTotalPages());
        orderPaginated.setPrev(originalPage > 1 ? generatePageUrlPrev(originalPage - 1, order, maxPage) : null);
        orderPaginated.setNext(originalPage < maxPage? generatePageUrlNext(originalPage + 1, order, maxPage) : null);
        orderPaginated.setItems(orders.getContent());

        return new ResponseEntity<>(orderPaginated, HttpStatus.OK);
    }

    private String generatePageUrlPrev(int pageUrl, String order, int maxPage) {
        if (pageUrl < 1) {
            pageUrl = 2;
        }
        if (pageUrl >= maxPage) {
            pageUrl = maxPage-1;
        }

        return "/api/v1/orders?page=" + pageUrl +
                "&order=" + order;
    }
    private String generatePageUrlNext(int pageUrl, String order, int maxPage) {

        if (pageUrl <= 1) {
            pageUrl = 2;
        }
        if (pageUrl >= maxPage) {
            pageUrl = maxPage;
        }
        return "/api/v1/orders?page=" + pageUrl +
                "&order=" + order;
    }
}
