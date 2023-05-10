package com.gollight.tacocloud.data.api;

import java.util.Optional;

import com.gollight.tacocloud.Ingredient;

public interface IngredientRepository {
    /**
     * 查询所有的配料信息
     * @return
     */
    Iterable<Ingredient> queryAllIngredient(); 

    /**
     * 根据id查询配料
     * @return
     */
    Optional<Ingredient> queryIngredientById(String id);

    /**
     * 保存Ingredient对象
     * @param ingredient
     * @return
     */
    Ingredient saveIngredient(Ingredient ingredient);
}