package com.gollight.tacocloud;


import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {

    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min = 5, message = "Name should be at last 5 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "你必须选择至少一种配料")
    private List<IngredientRef> IngredientList;
}
