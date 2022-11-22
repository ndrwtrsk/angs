package xyz.torski.angs.order.infra;

import xyz.torski.angs.order.domain.OrderProductStock;
import xyz.torski.angs.order.domain.OrderProductStockRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderProductStockRepository implements OrderProductStockRepository {
    private final Map<String, OrderProductStock> repo = new HashMap<>();

    @Override
    public Optional<OrderProductStock> findById(String id) {
        return Optional.ofNullable(repo.get(id));
    }

    public OrderProductStock add(OrderProductStock orderProductStock) {
        repo.put(orderProductStock.getId(), orderProductStock);
        return orderProductStock;
    }
}
