package com.aor.numbers;

import java.util.ArrayList;
import java.util.List;

public class ListFilterer {
    List<Integer> list;

    public ListFilterer(List<Integer> list) {
        this.list = list;
    }

    public List<Integer> filter(IListFilter filter) {
        List<Integer> ret = new ArrayList();

        for (Integer i : list) {
            if (filter.accept(i))
                ret.add(i);
        }

        return ret;
    }
}
