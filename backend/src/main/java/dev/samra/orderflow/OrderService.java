package dev.samra.orderflow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@Service
public class OrderService {
    private final ProductRepository products;
    private final OrderRepository orders;
    public OrderService(ProductRepository products, OrderRepository orders) {
        this.products = products; this.orders = orders;
    }
    @Transactional
    public PurchaseOrder place(Long productId, int quantity) {
        if (quantity < 1 || quantity > 1000) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be 1–1000");
        var product = products.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if (products.reserve(productId, quantity) != 1) throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient stock");
        return orders.save(new PurchaseOrder(product, quantity));
    }
}
