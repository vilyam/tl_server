package com.c17.yyh.mvc.message.inbound;

import com.c17.yyh.server.message.BaseInboundMessage;

public class TakeCollectionAwardRequest extends BaseInboundMessage {
	private int collection_id;

	public int getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(int collection_id) {
		this.collection_id = collection_id;
	}
}
