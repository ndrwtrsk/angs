package xyz.torski.angs.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@ToString
public final class OrderRealizationCommand {
    private final String orderId;
    private final String userId;
    private final List<String> productsToDeliver;

}
