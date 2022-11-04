package com.example.demo.service.pagination;

import java.util.List;

public interface Page<E> {
    List<E> getContent();
    public long getTotalEntitiesAmount();
    public long getPageSize();
}
