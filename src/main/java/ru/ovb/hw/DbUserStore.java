package ru.ovb.hw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component("dbUserStore")
public class DbUserStore implements IUserStore {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    RowMapper<User> rse = (resultSet, i) -> {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUserName(resultSet.getString("user_name"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("phone"));
        return user;
    };

    public DbUserStore(@Autowired DataSource ds) {
        jdbcTemplate = new NamedParameterJdbcTemplate(ds);
    }


    @Override
    public User find(Long userId) {
        List<User> x = jdbcTemplate.query("select * from users where id=:id", new MapSqlParameterSource("id", userId), rse);
        if (!x.isEmpty()) {
            return x.get(0);
        }
        return null;
    }

    @Override
    public Long save(User user) {
        try {
            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
            int update = jdbcTemplate.update("insert into users(user_name, first_name, last_name, email, phone) " +
                            "values (:user_name, :first_name, :last_name, :email, :phone) returning id",
                    new MapSqlParameterSource()
                            .addValue("user_name", user.getUserName())
                            .addValue("last_name", user.getLastName())
                            .addValue("first_name", user.getFirstName())
                            .addValue("email", user.getEmail())
                            .addValue("phone", user.getPhone())
                    , generatedKeyHolder
            );

            Number key = generatedKeyHolder.getKey();
            return key == null ? null : key.longValue();
        } catch (DataAccessException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public User delete(Long userId) {
        User user = find(userId);
        if (user != null) {
            jdbcTemplate.update("delete from users where id=:id", new MapSqlParameterSource("id", userId));
        }
        return user;
    }

    @Override
    public User update(User user) {
        User userx = find(user.getId());
        if (userx != null) {
            jdbcTemplate.update("update users set user_name=:user_name, last_name=:last_name, first_name=:first_name, email=:email, phone=:phone " +
                            "where id=:id",
                    new MapSqlParameterSource("id", user.getId())
                            .addValue("user_name", user.getUserName())
                            .addValue("last_name", user.getLastName())
                            .addValue("first_name", user.getFirstName())
                            .addValue("email", user.getEmail())
                            .addValue("phone", user.getPhone())
            );
        }
        return userx;
    }
}
