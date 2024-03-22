package com.example.demo.Infrastructure.Ports;

public interface AssetConsumerValidation<T> {
    public void assetCreateValidation(T asset);
    public void assetUpdateValidation(String asset, T updater);
}
