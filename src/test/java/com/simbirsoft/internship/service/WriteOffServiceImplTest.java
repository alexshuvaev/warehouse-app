package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.repository.WriteOffRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.simbirsoft.internship.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class WriteOffServiceImplTest {
    @MockBean
    private WriteOffRepository writeOffRepository;
    @Autowired
    private WriteOffService writeOffService;

    @Test
    void findAll() {
        // given
        when(writeOffRepository.findAll()).thenReturn(WRITEOFF_ENTITIES);
        // when
        List<WriteOffEntity> result = writeOffService.findAll();
        // than
        assertIterableEquals(WRITEOFF_ENTITIES, result);
        verify(writeOffRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        // given
        when(writeOffRepository.findById(1)).thenReturn(java.util.Optional.of(WRITEOFF_FALSE));
        // when
        WriteOffEntity result = writeOffService.findById(1);
        // than
        assertEquals(WRITEOFF_FALSE, result);
        verify(writeOffRepository, times(1)).findById(1);
    }

    @Test
    void createWriteOff() {
    }

    @Test
    void confirm() {
    }

    @Test
    void deleteById() {
    }
}