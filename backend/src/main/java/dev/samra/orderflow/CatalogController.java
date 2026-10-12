package dev.samra.orderflow;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;
record ProductInput(@NotBlank @Size(max=40) String sku,
                    @NotBlank @Size(max=150) String name,
                    @NotNull @DecimalMin("0.01") BigDecimal price,
                    @Min(0) int stock) {}
@RestController
@RequestMapping("/api/products")
public class CatalogController {
    private final ProductRepository products;
    public CatalogController(ProductRepository products) { this.products = products; }
    @GetMapping public List<Product> list() { return products.findAll(); }
    @GetMapping("/{id}") public Product one(@PathVariable Long id) {
        return products.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
    @ResponseStatus(HttpStatus.CREATED) @PostMapping
    public Product create(@Valid @RequestBody ProductInput input) {
        if (products.existsBySku(input.sku().trim())) throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU already exists");
        try { return products.saveAndFlush(new Product(input.sku().trim(),input.name().trim(),input.price(),input.stock())); }
        catch (DataIntegrityViolationException ex) { throw new ResponseStatusException(HttpStatus.CONFLICT,"SKU already exists"); }
    }
}
