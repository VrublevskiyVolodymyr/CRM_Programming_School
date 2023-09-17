package ua.com.owu.crm_programming_school.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.owu.crm_programming_school.models.Order;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Long> {
}

