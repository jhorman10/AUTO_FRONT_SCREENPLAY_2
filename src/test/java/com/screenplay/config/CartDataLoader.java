package com.screenplay.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public final class CartDataLoader {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private CartDataLoader() {}

    public static CartDataset load(String datasetKey) {
        String path = TestConstants.TESTDATA_PATH + datasetKey + TestConstants.TESTDATA_JSON_EXT;
        try (InputStream is = CartDataLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException(UiLabels.DATASET_NOT_FOUND + path);
            }
            return MAPPER.readValue(is, CartDataset.class);
        } catch (IOException e) {
            throw new IllegalStateException(UiLabels.DATASET_LOAD_ERROR + datasetKey, e);
        }
    }
}
