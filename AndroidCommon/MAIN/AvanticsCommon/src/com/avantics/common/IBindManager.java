package com.avantics.common;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by tom on 03/06/13.
 */
public interface IBindManager {
    ArrayList<UiBindingContainer> AttachToView(View view);
}
