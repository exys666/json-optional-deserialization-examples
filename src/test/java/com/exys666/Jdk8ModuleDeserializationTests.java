package com.exys666;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class Jdk8ModuleDeserializationTests {

    @Test
    void shouldDeserializeUndefinedAsNullOptional() throws IOException {
        var mapper = JsonMapper.builder()
                .addModule(new Jdk8Module().configureReadAbsentAsNull(true))
                .build();

        try (var parser = mapper.createParser("{}")) {
            var obj = parser.readValueAs(Dto.class);

            assertNull(obj.value);
        }
    }

    @Test
    void shouldDeserializeUndefinedAsEmptyOptional() throws IOException {
        var mapper = JsonMapper.builder()
                .addModule(new Jdk8Module())
                .build();

        try (var parser = mapper.createParser("{}")) {
            var obj = parser.readValueAs(Dto.class);

            assertEquals(Optional.empty(), obj.value);
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldDeserializeNullAsEmptyOptional(boolean absentAsNull) throws IOException {
        var mapper = JsonMapper.builder()
                .addModule(new Jdk8Module().configureReadAbsentAsNull(absentAsNull))
                .build();

        try (var parser = mapper.createParser("""
                {
                    "value": null
                }""")) {
            var obj = parser.readValueAs(Dto.class);

            assertEquals(Optional.empty(), obj.value);
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldDeserializeValue(boolean absentAsNull) throws IOException {
        var mapper = JsonMapper.builder()
                .addModule(new Jdk8Module().configureReadAbsentAsNull(absentAsNull))
                .build();

        try (var parser = mapper.createParser("""
                {
                    "value": "ABC"
                }""")) {
            var obj = parser.readValueAs(Dto.class);

            assertEquals(Optional.of("ABC"), obj.value);
        }
    }

    record Dto(Optional<String> value) {
    }
}
