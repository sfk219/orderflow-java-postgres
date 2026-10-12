package dev.samra.orderflow;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(name = "uq_products_sku", columnNames = "sku"))
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 40) private String sku;
    @Column(nullable = false, length = 150) private String name;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal price;
    @Column(nullable = false) private int stock;
    protected Product() {}
    public Product(String sku, String name, BigDecimal price, int stock) {
        this.sku = sku; this.name = name; this.price = price; this.stock = stock;
    }
    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public int getStock() { return stock; }
}
