package com.tianyao.entity;

import java.io.Serializable;

public class TokenConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tokenId = "tokenid";
    // token
    private String tokenValue;
    //过期时间
    private String expDate;

    public String getTokenValue() {
        return tokenValue;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public TokenConfig() {
    }

    public TokenConfig(String tokenValue, String expDate) {
        this.tokenValue = tokenValue;
        this.expDate = expDate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TokenConfig{");
        sb.append("tokenValue='").append(tokenValue).append('\'');
        sb.append(", expDate='").append(expDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
