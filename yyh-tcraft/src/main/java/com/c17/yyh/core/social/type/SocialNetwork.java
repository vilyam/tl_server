package com.c17.yyh.core.social.type;

public enum SocialNetwork {
    MM("mm"), 
    VK("vk"), 
    OK("ok"), 
    FB("fb");

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    SocialNetwork(String name) {
        this.name = name;
    }

    public static SocialNetwork fromString(String name) {
        for (SocialNetwork elem : SocialNetwork.values()) {
            if (elem.name.equalsIgnoreCase(name))
                return elem;
        }
        return null;
    }
}
