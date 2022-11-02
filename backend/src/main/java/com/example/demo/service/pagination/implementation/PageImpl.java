package com.example.demo.service.pagination.implementation;

import com.example.demo.service.pagination.Page;
import lombok.Data;

import java.util.List;

@Data
public class PageImpl<E> implements Page<E> {
    private List<E> content;
    private long pageStart;
    private long pageSize;
    private long totalEntitiesAmount;

    public PageImpl(List<E> content, long pageStart,long totalEntitiesAmount){
        this.content = content;
        if(content != null){
            this.pageStart = content.size();
        }
        this.pageSize = pageStart;
        this.totalEntitiesAmount = totalEntitiesAmount;
    }


    @Override
    public long getTotalElementsAmount() {
        return totalEntitiesAmount;
    }

    @Override
    public long getAmountElementsInPage() {
        return pageSize;
    }

}
