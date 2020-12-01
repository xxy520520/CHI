package com.example.dao;

import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getAccountByName(String userName) {
        String sql="select * from t_user where user_name=?";
        RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
        List<User> users = jdbcTemplate.query(sql,new Object[]{userName},rowMapper);
        if(users.size() == 1)
            return users.get(0);
        else
            return null;
    }

    public int createNewAccount(String userName,String password) {
        String sql="insert into t_user (user_name,password) values (?,?)";
        int i=jdbcTemplate.update(sql,new Object[]{userName,password});
        return i;
    }
}
