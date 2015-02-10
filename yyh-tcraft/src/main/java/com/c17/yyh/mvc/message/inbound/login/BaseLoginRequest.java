package com.c17.yyh.mvc.message.inbound.login;

import com.c17.yyh.server.message.BaseInboundMessage;

public class BaseLoginRequest extends BaseInboundMessage {
    private String  name;
    private String  lastName;
    private int     sex;
    private String  city;

    private String  country;
    private String  birthday;
    private int     language;
    private boolean noDailyBonus;

    private String  user_page;
    private int     friends_count = 0;
    private String  ref;

    private String  secretCode    = "";
    private String  session_key   = "";
    private String  auth_sig      = "";
    private String  hash          = "";

    private String  referrer;

    // private String timezone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUser_page() {
        return user_page;
    }

    public void setUser_page(String user_page) {
        this.user_page = user_page;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getAuth_sig() {
        return auth_sig;
    }

    public void setAuth_sig(String auth_sig) {
        this.auth_sig = auth_sig;
    }

    public boolean isNoDailyBonus() {
        return noDailyBonus;
    }

    public void setNoDailyBonus(boolean noDailyBonus) {
        this.noDailyBonus = noDailyBonus;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}
