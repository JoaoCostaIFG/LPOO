package com.aor.numbers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ListFilterTest {
    private List<Integer> list;

    @Before
    public void setUp() {
        list = new ArrayList();
        list.add(2);
        list.add(-0);
        list.add(0);
        list.add(-100);
        list.add(99);
    }

    @Test
    public void positiveFilter() {
        PositiveFilter f = new PositiveFilter();
        assertEquals(true, f.accept(2));
        assertEquals(true, f.accept(-0));
        assertEquals(true, f.accept(0));
        assertEquals(false, f.accept(-100));
        assertEquals(true, f.accept(99));
    }

    @Test
    public void positiveFilterList() {
        IListFilter posfilter = Mockito.mock(IListFilter.class);
        Mockito.when(posfilter.accept(2)).thenReturn(true);
        Mockito.when(posfilter.accept(-0)).thenReturn(true);
        Mockito.when(posfilter.accept(0)).thenReturn(true);
        Mockito.when(posfilter.accept(-100)).thenReturn(false);
        Mockito.when(posfilter.accept(99)).thenReturn(true);

        List<Integer> expected = new ArrayList();
        expected.add(2);
        expected.add(-0);
        expected.add(0);
        expected.add(99);

        ListFilterer f = new ListFilterer(list);
        assertEquals(expected, f.filter(posfilter));
    }

    @Test
    public void divFilter() {
        DivisibleByFilter f = new DivisibleByFilter(65);
        assertEquals(false, f.accept(2));
        assertEquals(true, f.accept(-0));
        assertEquals(true, f.accept(0));
        assertEquals(true, f.accept(-65));
        assertEquals(false, f.accept(-987));
        assertEquals(true, f.accept(130));
    }

    @Test
    public void pairFilterList() {
        IListFilter posfilter = Mockito.mock(IListFilter.class);
        Mockito.when(posfilter.accept(2)).thenReturn(true);
        Mockito.when(posfilter.accept(-0)).thenReturn(true);
        Mockito.when(posfilter.accept(0)).thenReturn(true);
        Mockito.when(posfilter.accept(-100)).thenReturn(true);
        Mockito.when(posfilter.accept(99)).thenReturn(false);

        List<Integer> expected = new ArrayList();
        expected.add(2);
        expected.add(-0);
        expected.add(0);
        expected.add(-100);

        ListFilterer f = new ListFilterer(list);
        assertEquals(expected, f.filter(posfilter));
    }
}
