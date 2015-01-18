package com.epam.training.persistence;

import com.epam.training.persistence.DAOFactory;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Oleg_Burshinov on 14.01.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class DemoTest extends TestCase {
    private DAOFactory daoFactory;
    @Before
    public void init() {
        daoFactory = mock(DAOFactory.class);
    }

    @Test
    public void test() {
        //mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @After
    public void tearDown() throws Exception {

    }
}
