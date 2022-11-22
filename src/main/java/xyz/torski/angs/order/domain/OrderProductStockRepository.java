package xyz.torski.angs.order.domain;

import java.util.Optional;

public interface OrderProductStockRepository {

    Optional<OrderProductStock> findById(String id);

}
