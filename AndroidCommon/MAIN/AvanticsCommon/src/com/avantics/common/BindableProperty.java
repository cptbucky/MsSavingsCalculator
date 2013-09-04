package com.avantics.common;

import java.util.ArrayList;

public class BindableProperty<T> {
    private T value;
    private OnValueChangedEventListener valueChanged;
    public ArrayList<UiBindingContainer> listeners;

    public BindableProperty(T newValue) {
        listeners = new ArrayList<UiBindingContainer>();
        setValue(newValue);
    }

    public BindableProperty(T newValue, OnValueChangedEventListener onValueChanged) {
        this(newValue);

        valueChanged = onValueChanged;
    }

    public void setValue(T newValue) {
        if (value == null ? value != newValue : !value.equals(newValue)) {
            value = newValue;

            if (valueChanged != null) {
                valueChanged.OnValueChanged();
            }

            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).rebindValue();
            }
        }
    }

    public T getValue() {
        return value;
    }

    public void addListener(UiBindingContainer container) {
        listeners.add(container);
    }

    public void removeListener(UiBindingContainer container) {

    }
}
