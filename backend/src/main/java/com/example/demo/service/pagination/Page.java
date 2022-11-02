package com.example.demo.service.pagination;

import java.util.List;

public interface Page<T> {
    long getTotalElementsAmount();
    long getAmountElementsInPage();

    List<T> getContent();
}
