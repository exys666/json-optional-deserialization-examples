package com.exys666;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.ReferenceTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

public class JsonOptionalDeserializer extends ReferenceTypeDeserializer<JsonOptional<?>> {


    public JsonOptionalDeserializer(JavaType fullType, ValueInstantiator vi, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
        super(fullType, vi, typeDeser, deser);
    }

    @Override
    protected ReferenceTypeDeserializer<JsonOptional<?>> withResolved(TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) {
        return new JsonOptionalDeserializer(_fullType, _valueInstantiator, typeDeserializer, jsonDeserializer);
    }

    @Override
    public JsonOptional<?> getNullValue(DeserializationContext deserializationContext) throws JsonMappingException {
        return JsonOptional.of(null);
    }

    @Override
    public JsonOptional<?> getAbsentValue(DeserializationContext ctxt) throws JsonMappingException {
        return JsonOptional.undefined();
    }

    @Override
    public JsonOptional<?> referenceValue(Object o) {
        return new JsonOptional.Value<>(o);
    }

    @Override
    public JsonOptional<?> updateReference(JsonOptional<?> jsonOptional, Object o) {
        return JsonOptional.of(o);
    }

    @Override
    public Object getReferenced(JsonOptional<?> jsonOptional) {
        return jsonOptional.value();
    }
}
