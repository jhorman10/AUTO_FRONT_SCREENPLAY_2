package com.screenplay.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDataset {

    private String datasetId;
    private int expectedCartItemCount;
    private int expectedQuantityPerItem;
    private List<CartItem> items;

    public CartDataset() {}

    public String getDatasetId() { return datasetId; }
    public int getExpectedCartItemCount() { return expectedCartItemCount; }
    public int getExpectedQuantityPerItem() { return expectedQuantityPerItem; }
    public List<CartItem> getItems() { return items; }

    public void setDatasetId(String datasetId) { this.datasetId = datasetId; }
    public void setExpectedCartItemCount(int expectedCartItemCount) { this.expectedCartItemCount = expectedCartItemCount; }
    public void setExpectedQuantityPerItem(int expectedQuantityPerItem) { this.expectedQuantityPerItem = expectedQuantityPerItem; }
    public void setItems(List<CartItem> items) { this.items = items; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CartItem {
        private String key;
        private int productIndex;

        public CartItem() {}

        public String getKey() { return key; }
        public int getProductIndex() { return productIndex; }

        public void setKey(String key) { this.key = key; }
        public void setProductIndex(int productIndex) { this.productIndex = productIndex; }
    }
}
