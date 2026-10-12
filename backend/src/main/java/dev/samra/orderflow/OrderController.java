package dev.samra.orderflow;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
record PlaceOrder(@NotNull Long productId, @Min(1) @Max(1000) int quantity) {}
record OrderView(Long id, Long productId, int quantity, java.math.BigDecimal unitPrice, java.math.BigDecimal totalPrice, java.time.Instant createdAt) {
    static OrderView from(PurchaseOrder order) {
        return new OrderView(order.getId(), order.getProduct().getId(), order.getQuantity(), order.getUnitPrice(),order.getTotalPrice(),order.getCreatedAt());
    }
}
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service; private final OrderRepository orders;
    public OrderController(OrderService service, OrderRepository orders){this.service=service;this.orders=orders;}
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public OrderView place(@Valid @RequestBody PlaceOrder order){return OrderView.from(service.place(order.productId(),order.quantity()));}
    @GetMapping public List<OrderView> list(){return orders.findAll().stream().map(OrderView::from).toList();}
    @GetMapping("/{id}") public OrderView one(@PathVariable Long id){
        return OrderView.from(orders.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found")));
    }
}
