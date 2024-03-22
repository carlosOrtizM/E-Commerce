package com.example.demo.Infrastructure.Ports;

import com.example.demo.Domain.Users;

import java.util.List;
import java.util.Optional;

public interface AssetLifecycle<T> {
    public List<T> getAllAssets();
    public Optional<T> getAssetByName(String name);
    public Optional<T> getAssetById(int id);
    public void createAsset(T created);
    public void updateAssetByAsset(String asset, T updater);
    public void updateAssetByName(String asset, T updater);
    public void updateAssetById(int asset, T updater);
    public void deleteAssetById(int id);
}
