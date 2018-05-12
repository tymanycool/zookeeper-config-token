package com.tianyao.dao;

import com.tianyao.entity.TokenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component("tokenDao")
public class TokenDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public TokenConfig getConfig() {
        try {
            RowMapper<TokenConfig> rm = BeanPropertyRowMapper.newInstance(TokenConfig.class);
            TokenConfig tokenConfig = jdbcTemplate.queryForObject("select token_id as tokenId,token_value as tokenValue,exp_date as expDate from tbl_token where token_id = 'tokenid'", rm);
            return tokenConfig;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateConfig(TokenConfig config) {
        int ret;
        if(getConfig()==null){
           ret = jdbcTemplate.update("insert into tbl_token VALUE (?,?,?)", (PreparedStatementSetter) ps -> {
                ps.setString(1, config.getTokenId());
                ps.setString(2, config.getTokenValue());
                ps.setString(3, config.getExpDate());
            });
        }else{
            ret = jdbcTemplate.update("update tbl_token set token_value=?,exp_date=? where token_id =?", (PreparedStatementSetter) ps -> {
                ps.setString(3, config.getTokenId());
                ps.setString(1, config.getTokenValue());
                ps.setString(2, config.getExpDate());
            });
        }
        return ret;
    }

    public int deleteConfig(TokenConfig config){
        return jdbcTemplate.update("delete from tbl_token where token_id=?", (PreparedStatementSetter) ps -> {
            ps.setString(1, config.getTokenId());
        });
    }

}
