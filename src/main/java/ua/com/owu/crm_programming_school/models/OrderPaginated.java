package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaginated {
    @Schema(description = "Total number of items")
    private int total_items;

    @Schema(description = "Total number of pages")
    private int total_pages;

    @Schema(description = "Link to the previous page of orders")
    private String prev;

    @Schema(description = "List of orders on the current page")
    private String next;

    private List<Order> items;
}
