package com.gollight.tacocloud.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gollight.tacocloud.Ingredient;
import com.gollight.tacocloud.Taco;
import com.gollight.tacocloud.TacoOrder;
import com.gollight.tacocloud.Ingredient.Type;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesiginTacoController {

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla 面粉玉米饼", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla 麦粉玉米饼", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef 牛肉", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas 猪肉丝", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes 小块番茄", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce 生菜", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar 黄色奶酪", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa 辣调味汁", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream 醋", Type.SAUCE));
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
            log.error("addIngredientsToModel type" + type.toString().toLowerCase() 
            + " " + filterByType(ingredients, type));
        }
    }

    /**
     * @param ingredients
     * @param type
     * @return
     */
    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(Taco taco, @ModelAttribute TacoOrder tacoOrder) {
        tacoOrder.addTaco(taco);
        log.info("processTaco {}", taco);
        return "redirect:/orders/current";
    }

}
