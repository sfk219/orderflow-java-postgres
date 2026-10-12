package dev.samra.orderflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);
    Optional<Product> findBySku(String sku);
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Product p set p.stock = p.stock - :quantity where p.id = :id and p.stock >= :quantity")
    int reserve(@Param("id") Long id, @Param("quantity") int quantity);
}
