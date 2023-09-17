package ua.com.owu.crm_programming_school.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaginated {
    private int total_items;
    private int total_pages;
    private String prev;
    private String next;
    private List<Order> items;
}
