package com.gollight.tacocloud.data.impl;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gollight.tacocloud.Ingredient;
import com.gollight.tacocloud.IngredientRef;
import com.gollight.tacocloud.Taco;
import com.gollight.tacocloud.TacoOrder;
import com.gollight.tacocloud.data.api.OrderRepository;

import lombok.val;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Transactional
    @Override
    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory pscf = 
        new PreparedStatementCreatorFactory("insert into Taco_Order "
        + "(delivery_name, delivery_street, delivery_city, "
        + "delivery_state, delivery_zip, cc_number, "
        + "cc_expiration, cc_cvv, placed_at) "
        + "values (?,?,?,?,?,?,?,?,?)",
        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        order.setPlaceAt(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
            Arrays.asList(order.getDeliveryName(),
            order.getDeliveryStreet(),
            order.getDeliveryCity(),
            order.getDeliveryState(),
            order.getDeliveryZip(),
            order.getCcNumber(),
            order.getCcExpiration(),
            order.getCcCVV(),
            order.getPlaceAt()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        // 保存 tacoList
        List<Taco> tacoList = order.getTacos();
        int i = 0;
        for (Taco taco : tacoList) {
            // todo save TactoList
            saveTaco(orderId, i++, taco);
        }

        return order;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
            "insert into Taco "
            + "(name, created_at, taco_order, taco_order_key) "
            + "values (?, ?, ?, ?)",
            Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT);
        pscf.setReturnGeneratedKeys(true);
        
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
            Arrays.asList(taco.getName(), taco.getCreatedAt(), orderId, orderKey)
        );
        
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(psc, generatedKeyHolder);

        long tacoId = generatedKeyHolder.getKey().longValue();

        taco.setId(tacoId);
        // 保存配料链接表
        saveIngredientRefs(tacoId, taco.getIngredientList());
        return tacoId;
    }
    
    /**
     * 保存配料链接表
     * @param tacoId
     * @param ingredientRefs
     */
    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientRefs) {
            jdbcOperations.update("insert into Ingredient_ref (ingredient, taco, taco_key) "
            + "values (?,?,?)", ingredientRef.getIngredient(), tacoId, key++);
        }
    }


    
}
