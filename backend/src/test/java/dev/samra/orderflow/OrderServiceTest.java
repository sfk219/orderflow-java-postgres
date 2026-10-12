package dev.samra.orderflow;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class OrderServiceTest {
    @Test void reserveStockWhenAvailable() {
        var products=mock(ProductRepository.class);var orders=mock(OrderRepository.class);
        var product=new Product("A100","Desk",new BigDecimal("19.99"),10);
        when(products.findById(1L)).thenReturn(Optional.of(product));
        when(products.reserve(1L,2)).thenReturn(1);
        when(orders.save(any(PurchaseOrder.class))).thenAnswer(inv->inv.getArgument(0));
        var result=new OrderService(products,orders).place(1L,2);
        assertEquals(new BigDecimal("39.98"),result.getTotalPrice());
        verify(products).reserve(1L,2);
    }
    @Test void preventOverSelling() {
        var products=mock(ProductRepository.class);var orders=mock(OrderRepository.class);
        when(products.findById(1L)).thenReturn(Optional.of(new Product("B","Pen",BigDecimal.ONE,1)));
        when(products.reserve(1L,2)).thenReturn(0);
        assertThrows(ResponseStatusException.class,()->new OrderService(products,orders).place(1L,2));
        verifyNoInteractions(orders);
    }
    @Test void rejectInvalidQuantity() {
        assertThrows(ResponseStatusException.class,()->new OrderService(mock(ProductRepository.class),mock(OrderRepository.class)).place(1L,0));
    }
}
