package com.exys666;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public class CustomModule extends Module {

    @Override
    public String getModuleName() {
        return "CustomModule";
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext setupContext) {
        setupContext.addDeserializers(new CustomDeserializers());
        setupContext.addTypeModifier(new CustomTypeModifier());
    }
}
