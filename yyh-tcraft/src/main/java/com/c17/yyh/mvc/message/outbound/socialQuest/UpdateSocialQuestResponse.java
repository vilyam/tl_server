package com.c17.yyh.mvc.message.outbound.socialQuest;

import com.c17.yyh.server.message.BaseOutboundMessage;

public class UpdateSocialQuestResponse extends BaseOutboundMessage{
    public UpdateSocialQuestResponse() {
        super("/social/update_quest");
    }
}
