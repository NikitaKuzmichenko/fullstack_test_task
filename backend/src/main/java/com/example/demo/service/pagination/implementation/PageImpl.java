package com.example.demo.service.pagination.implementation;

import com.example.demo.service.pagination.Page;
import lombok.Data;

import java.util.List;

@Data
public class PageImpl<E> implements Page<E> {
    private List<E> content;
    private long pageOffset;
    private long pageSize;
    private long totalEntitiesAmount;

    public PageImpl(List<E> content, long pageStart,long totalEntitiesAmount){
        this.content = content;
        if(content != null){
            this.pageSize = content.size();
        }
        this.pageOffset = pageStart;
        this.totalEntitiesAmount = totalEntitiesAmount;
    }

    @Override
    public List<E> getContent(){
        return content;
    }

    @Override
    public long getTotalEntitiesAmount() {
        return totalEntitiesAmount;
    }

    @Override
    public long getPageSize() {
        return pageSize;
    }

}
