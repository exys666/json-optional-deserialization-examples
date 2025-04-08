package com.exys666;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SetterDeserializationTests {

    ObjectMapper mapper = JsonMapper.builder()
            .addModule(new Jdk8Module())
            .build();

    @Test
    void shouldDeserializeUndefined() throws IOException {
        try (var parser = mapper.createParser("{}")) {
            var obj = parser.readValueAs(Dto.class);

            assertEquals(JsonOptional.undefined(), obj.getValue());
        }
    }

    @Test
    void shouldDeserializeNull() throws IOException {
        try (var parser = mapper.createParser("""
                {
                    "value": null
                }""")) {
            var obj = parser.readValueAs(Dto.class);

            assertEquals(JsonOptional.of(null), obj.getValue());
        }
    }

    @Test
    void shouldDeserializeValue() throws IOException {
        try (var parser = mapper.createParser("""
                {
                    "value": "ABC"
                }""")) {
            var obj = parser.readValueAs(Dto.class);

            assertEquals(JsonOptional.of("ABC"), obj.getValue());
        }
    }

    static class Dto {

        private JsonOptional<String> value = JsonOptional.undefined();

        void setValue(String value) {
            this.value = JsonOptional.of(value);
        }

        JsonOptional<String> getValue() {
            return value;
        }
    }
}
