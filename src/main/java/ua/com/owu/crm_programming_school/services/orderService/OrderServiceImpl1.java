package ua.com.owu.crm_programming_school.services.orderService;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import ua.com.owu.crm_programming_school.dao.GroupDAO;
import ua.com.owu.crm_programming_school.dao.OrderDAO;
import ua.com.owu.crm_programming_school.dao.UserDAO;
import ua.com.owu.crm_programming_school.models.*;



@Service
@AllArgsConstructor
public class OrderServiceImpl1 implements OrderService {
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private GroupDAO groupDAO;

    public ResponseEntity<OrderPaginated> getAllOrders(int page, String order, int size) {
        try {

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

            Pageable pageable = PageRequest.of(page - 1, size, sortDirection, sortBy);

            Page<Order> orders = orderDAO.findAll(pageable);

            OrderPaginated orderPaginated = new OrderPaginated();
            orderPaginated.setTotal_items((int) orders.getTotalElements());
            orderPaginated.setTotal_pages(orders.getTotalPages());
            orderPaginated.setPrev(originalPage > 1 ? generatePageUrlPrev(originalPage - 1, order, maxPage) : null);
            orderPaginated.setNext(originalPage < maxPage ? generatePageUrlNext(originalPage + 1, order, maxPage) : null);
            orderPaginated.setItems(orders.getContent());

            return new ResponseEntity<>(orderPaginated, HttpStatus.OK);

        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Order> getById(Integer id) {
        Order order = null;
        if (id > 0) {
            order = orderDAO.findById(Long.valueOf(id)).get();
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Order> updateOrder(Integer id, OrderEdit orderEdit, Principal principal) {
        Order order = orderDAO.findById(Long.valueOf(id)).get();
        if (order != null) {

            String email = principal.getName();
            User manager = userDAO.findByEmail(email);
            boolean isOwner = order.getManager() != null && order.getManager().getEmail().equals(principal.getName());
            boolean isManagerNotAssigned = order.getManager() == null;

            Group group = groupDAO.findByName(orderEdit.getGroup());

            if (isOwner || isManagerNotAssigned) {
                order.setName(orderEdit.getName());
                order.setSurname(orderEdit.getSurname());
                order.setEmail(orderEdit.getEmail());
                order.setPhone(orderEdit.getPhone());
                order.setAge(orderEdit.getAge());
                order.setCourse(orderEdit.getCourse());
                order.setCourseFormat(orderEdit.getCourseFormat());
                order.setCourseType(orderEdit.getCourseType());
                order.setAlreadyPaid(orderEdit.getAlreadyPaid());
                order.setSum(orderEdit.getSum());
                order.setMsg(orderEdit.getMsg());
                order.setStatus(orderEdit.getStatus());
                order.setUtm(orderEdit.getUtm());
                if (group != null) {
                    order.setGroup(group);
                } else {
                    Group newGroup = new Group(orderEdit.getGroup());
                    order.setGroup(newGroup);
                }
                order.setManager(manager);

                Order savedOrder = orderDAO.save(order);

                return new ResponseEntity<>(savedOrder, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.valueOf("You cannot do it"));
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<Comment>> getComments(Integer orderId) {
        Order order = null;
        if (orderId > 0) {
            order = orderDAO.findById(Long.valueOf(orderId)).get();
            if (order != null) {
                List<Comment> comments = order.getComments();
                return new ResponseEntity<>(comments, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<Comment>> createComment(Integer orderId, CommentRequest commentRequest, Principal principal) {
        Order order = null;
        if (orderId > 0) {
            order = orderDAO.findById(Long.valueOf(orderId)).get();

            if (order != null) {
                String email = principal.getName();
                User manager = userDAO.findByEmail(email);
                boolean isOwner = order.getManager() != null && order.getManager().getEmail().equals(principal.getName());
                boolean isManagerNotAssigned = order.getManager() == null;

                if (isOwner || isManagerNotAssigned) {
                    List<Comment> comments = order.getComments();
                    Comment comment = Comment
                            .builder()
                            .comment(commentRequest.getComment())
                            .createdAt(LocalDateTime.now().withNano(0))
                            .orderId(orderId)
                            .manager(manager)
                            .build();
                    comments.add(comment);
                    order.setComments(comments);
                    String status = order.getStatus();

                    if (status.equals("New") || status.equals(null)) {
                        order.setStatus("In work");
                    }
                    orderDAO.save(order);

                    return new ResponseEntity<>(comments, HttpStatus.OK);
                }
                return new ResponseEntity<>(null, HttpStatus.valueOf("You cannot do it"));
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    private String generatePageUrlPrev(int pageUrl, String order, int maxPage) {
        if (pageUrl < 1) {
            pageUrl = 2;
        }
        if (pageUrl >= maxPage) {
            pageUrl = maxPage - 1;
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
