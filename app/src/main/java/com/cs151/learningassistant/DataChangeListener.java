package com.cs151.learningassistant;

import java.io.Serializable;

public interface DataChangeListener extends Serializable {

    void onDataChange();
}
