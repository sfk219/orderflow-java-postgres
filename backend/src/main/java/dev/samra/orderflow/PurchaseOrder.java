package dev.samra.orderflow;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "product_id") private Product product;
    @Column(nullable = false) private int quantity;
    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2) private BigDecimal unitPrice;
    @Column(name = "total_price", nullable = false, precision = 12, scale = 2) private BigDecimal totalPrice;
    @Column(name = "created_at", nullable = false) private Instant createdAt;
    protected PurchaseOrder() {}
    public PurchaseOrder(Product product, int quantity) {
        this.product = product; this.quantity = quantity;
        this.unitPrice = product.getPrice(); this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        this.createdAt = Instant.now();
    }
    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public Instant getCreatedAt() { return createdAt; }
}
