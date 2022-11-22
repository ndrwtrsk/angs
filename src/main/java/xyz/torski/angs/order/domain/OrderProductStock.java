package xyz.torski.angs.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderProductStock {
    private final String id;
    private final String name;
}
