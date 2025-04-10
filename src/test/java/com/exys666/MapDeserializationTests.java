package com.exys666;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapDeserializationTests {

    ObjectMapper mapper = JsonMapper.builder()
            .addModule(new Jdk8Module())
            .build();

    @Test
    void shouldDeserializeUndefined() throws IOException {
        try (var parser = mapper.createParser("{}")) {
            var map = parser.readValueAs(Map.class);

            assertFalse(map.containsKey("value"));
        }
    }

    @Test
    void shouldDeserializeNull() throws IOException {
        try (var parser = mapper.createParser("""
                {
                    "value": null
                }""")) {
            var map = parser.readValueAs(Map.class);

            assertTrue(map.containsKey("value"));
            assertNull(map.get("value"));
        }
    }

    @Test
    void shouldDeserializeValue() throws IOException {
        try (var parser = mapper.createParser("""
                {
                    "value": "ABC"
                }""")) {
            var map = parser.readValueAs(Map.class);

            assertEquals("ABC", map.get("value"));
        }
    }

}
