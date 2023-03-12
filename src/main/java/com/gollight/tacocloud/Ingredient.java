package com.gollight.tacocloud;
import lombok.Data;
/**
 * 配料类
 */
@Data
public class Ingredient {
    
    private final String id;
    private final String name; // 配料名称
    private final Type type; // 类型

    public enum Type {
        WRAP, // 包裹
        PROTEIN, // 蛋白质
        VEGGIES, // 蔬菜
        CHEESE, // 奶酪
        SAUCE, // 酱油
    }
}
