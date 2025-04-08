package com.exys666;

public sealed interface JsonOptional<T> permits JsonOptional.Null, JsonOptional.Undefined, JsonOptional.Value {

    static <T> JsonOptional<T> undefined() {
        return new JsonOptional.Undefined<>();
    }

    static <T> JsonOptional<T> of(T value) {
        return value == null ? new Null<>() : new JsonOptional.Value<>(value);
    }

    default T value() {
        return null;
    }


    record Value<T>(T value) implements JsonOptional<T> {
    }

    record Null<T>() implements JsonOptional<T> {
    }

    record Undefined<T>() implements JsonOptional<T> {
    }

}
