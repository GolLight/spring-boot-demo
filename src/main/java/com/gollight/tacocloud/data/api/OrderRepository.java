package com.gollight.tacocloud.data.api;

import com.gollight.tacocloud.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
