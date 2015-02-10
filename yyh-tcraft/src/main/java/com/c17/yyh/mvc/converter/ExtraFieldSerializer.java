package com.c17.yyh.mvc.converter;

import java.io.IOException;

import com.c17.yyh.server.message.BaseOutboundMessage;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;

public class ExtraFieldSerializer extends BeanSerializerBase {

    private static String API_VER ;
    private static String LEVEL_SET_VER ;

    public ExtraFieldSerializer(BeanSerializerBase source, String apiVersion, String levelSetVersion) {
        super(source);
        API_VER = apiVersion;
        LEVEL_SET_VER = levelSetVersion;
    }

    ExtraFieldSerializer(ExtraFieldSerializer source,
            ObjectIdWriter objectIdWriter) {
        super(source, objectIdWriter);
    }

    ExtraFieldSerializer(ExtraFieldSerializer source, String[] toIgnore) {
        super(source, toIgnore);
    }

    public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return new ExtraFieldSerializer(this, objectIdWriter);
    }

    protected BeanSerializerBase withIgnorals(String[] toIgnore) {
        return new ExtraFieldSerializer(this, toIgnore);
    }

    public void serialize(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeStartObject();
        serializeFields(bean, jgen, provider);
        if(bean instanceof BaseOutboundMessage) {
            jgen.writeStringField("lvl_set", LEVEL_SET_VER);
            jgen.writeStringField("api", API_VER);
            jgen.writeNumberField("ts", System.currentTimeMillis());
        }
        jgen.writeEndObject();
    }

    @Override
    protected BeanSerializerBase asArraySerializer() {
        return null;
    }

    @Override
    protected BeanSerializerBase withFilterId(Object filterId) {
        return null;
    }
}
