package com.github.camotoy.geyserblockjavaplayers.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Configurate {
    /**
     * Load config
     *
     * @param dataDirectory The config's directory
     */
    public static Configurate create(Path dataDirectory) {
        File folder = dataDirectory.toFile();
        File file = new File(folder, "config.yml");

        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try (InputStream input = Configurate.class.getResourceAsStream("/" + file.getName())) {
                if (input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(dataDirectory.resolve("config.yml").toFile(), Configurate.class);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create GeyserBlockJavaPlayers config!", e);
        }
    }

    @JsonProperty("block-java-message")
    private String blockJavaMessage;

    @JsonProperty("deny-server-access")
    private List<String> noServerAccess;

    public List<String> getNoServerAccess() { return noServerAccess; }

    public String getBlockJavaMessage() {
        return blockJavaMessage;
    }
}
