package com.aor.numbers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ListDeduplicatorTest {
    List<Integer> list, expected;
    @Before
    public void setUp() {
        list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        list.add(2);
        list.add(5);

        expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(4);
        expected.add(5);
    }

    @Test
    public void deduplicate() {
        ListDeduplicator deduplicator = new ListDeduplicator(list);
        List<Integer> distinct = deduplicator.deduplicate();

        assertEquals(expected, distinct);
    }

    @Test
    public void deduplicate2() {
        class ListSorterStub implements IListSorter {
            public List<Integer> sort() {
                list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(2);
                list.add(4);
                return list;
            }
        }

        expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(4);

        ListSorterStub stub = new ListSorterStub();
        ListDeduplicator deduplicator = new ListDeduplicator(list);
        List<Integer> distinct = deduplicator.deduplicate(stub);

        assertEquals(expected, distinct);
    }
}