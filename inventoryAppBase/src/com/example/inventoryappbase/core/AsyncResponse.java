package com.example.inventoryappbase.core;

public interface AsyncResponse<T, S> {
    void processFinish(T output);
    void processProgress(S level);
    void processTime(long timeInMs);
}