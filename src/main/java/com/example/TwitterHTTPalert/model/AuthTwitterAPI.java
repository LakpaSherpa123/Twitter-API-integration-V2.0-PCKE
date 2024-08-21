package com.example.TwitterHTTPalert.model;


import jakarta.persistence.*;

@Entity
@Table(schema = "twitter_alert")
public class AuthTwitterAPI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String BEARER_TOKEN;
    private String CONSUMER_KEY;
    private String CONSUMER_SECRET;
    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String REDIRECT_URI;
    private String scope;
    private String code_verifier;
    private String code_challenge;
    private String access_token;

    public String getBEARER_TOKEN() {
        return BEARER_TOKEN;
    }

    public void setBEARER_TOKEN(String BEARER_TOKEN) {
        this.BEARER_TOKEN = BEARER_TOKEN;
    }

    public String getCONSUMER_KEY() {
        return CONSUMER_KEY;
    }

    public void setCONSUMER_KEY(String CONSUMER_KEY) {
        this.CONSUMER_KEY = CONSUMER_KEY;
    }

    public String getCONSUMER_SECRET() {
        return CONSUMER_SECRET;
    }

    public void setCONSUMER_SECRET(String CONSUMER_SECRET) {
        this.CONSUMER_SECRET = CONSUMER_SECRET;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public void setCLIENT_ID(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public String getCLIENT_SECRET() {
        return CLIENT_SECRET;
    }

    public void setCLIENT_SECRET(String CLIENT_SECRET) {
        this.CLIENT_SECRET = CLIENT_SECRET;
    }

    public String getREDIRECT_URI() {
        return REDIRECT_URI;
    }

    public void setREDIRECT_URI(String REDIRECT_URI) {
        this.REDIRECT_URI = REDIRECT_URI;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCode_verifier() {
        return code_verifier;
    }

    public void setCode_verifier(String code_verifier) {
        this.code_verifier = code_verifier;
    }

    public String getCode_challenge() {
        return code_challenge;
    }

    public void setCode_challenge(String code_challenge) {
        this.code_challenge = code_challenge;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
