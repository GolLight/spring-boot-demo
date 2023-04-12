package com.gollight.tacocloud.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.gollight.tacocloud.Ingredient;

@Repository
public class JdbcIngredientRepositroy implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepositroy(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // @Override
    // public Iterable<Ingredient> queryAllIngredient() {
    // return jdbcTemplate.query("select id, name, type from Ingredient",
    // this::mapRowToIngredient);
    // }

    @Override
    public Iterable<Ingredient> queryAllIngredient() {
        return jdbcTemplate.query("select id, name, type from Ingredient", new RowMapper<Ingredient>() {

            @Override
            @Nullable
            public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Ingredient(
                        rs.getString("id"),
                        rs.getString("name"),
                        Ingredient.Type.valueOf(rs.getString("type")));
            }

        });
    }

    @Override
    public Optional<Ingredient> queryIngredientById(String id) {
        List<Ingredient> res = jdbcTemplate.query("select id, name, type from Ingredient where id = ?",
                this::mapRowToIngredient, id);
        return res.size() == 0 ? Optional.empty() : Optional.of(res.get(0));
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
        jdbcTemplate.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type")));
    }

}
