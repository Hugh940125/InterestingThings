package com.example.hugh.interesting.RecyclerView.refresh_layout;


public abstract class Decorator implements IDecorator {
    protected IDecorator decorator;
    protected RefreshLayout.CoContext cp;

    public Decorator(RefreshLayout.CoContext processor, IDecorator decorator1) {
        cp = processor;
        decorator = decorator1;
    }
}
