package com.exys666;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomModuleDeserializationTests {

    ObjectMapper mapper = JsonMapper.builder()
            .addModule(new CustomModule())
            .build();


    @Test
    void shouldDeserializeUndefined() throws IOException {
        try (var parser = mapper.createParser("{}")) {
            var obj = parser.readValueAs(Dto.class);

            assertEquals(JsonOptional.undefined(), obj.value);
        }
    }

    @Test
    void shouldDeserializeNull() throws IOException {
        try (var parser = mapper.createParser("""
                {
                    "value": null
                }""")) {
            var obj = parser.readValueAs(Dto.class);

            assertEquals(JsonOptional.of(null), obj.value);
        }
    }

    @Test
    void shouldDeserializeValue() throws IOException {
        try (var parser = mapper.createParser("""
                {
                    "value": "ABC"
                }""")) {
            var obj = parser.readValueAs(Dto.class);

            assertEquals(JsonOptional.of("ABC"), obj.value);
        }
    }

    @Test
    void shouldDeserializeValueWithCustomDeserializer() throws IOException {
        try (var parser = mapper.createParser("""
                {
                    "value": "ABC"
                }""")) {
            var obj = parser.readValueAs(WDto.class);

            assertEquals(JsonOptional.of(new Wrapper("ABC")), obj.value);
        }
    }

    record Dto(JsonOptional<String> value) {
    }

    record Wrapper(String value) {
    }

    record WDto(
            @JsonDeserialize(contentUsing = WrapperDeserializer.class)
            JsonOptional<Wrapper> value
    ) {
    }

    static class WrapperDeserializer extends JsonDeserializer<Wrapper> {

        @Override
        public Wrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new Wrapper(jsonParser.getValueAsString());
        }
    }
}
