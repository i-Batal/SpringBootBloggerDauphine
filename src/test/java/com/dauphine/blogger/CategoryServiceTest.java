package com.dauphine.blogger;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.repository.CategoryRepository;
import com.dauphine.blogger.services.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private CategoryServiceImpl serviceUnderTest;
    private CategoryRepository mockRepository;

    @BeforeEach
    void initialize() {
        mockRepository = mock(CategoryRepository.class);
        serviceUnderTest = new CategoryServiceImpl(mockRepository);
    }

    @Test
    void testGetByIdWhenIdExists() throws CategoryNotFoundByIdException {
        // Préparation
        UUID testId = UUID.randomUUID();
        Category expectedCategory = new Category("Test Category");

        when(mockRepository.findById(testId)).thenReturn(Optional.of(expectedCategory));

        // Exécution
        Category actualCategory = serviceUnderTest.getById(testId);

        // Vérification
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void testGetByIdWhenIdNotExists() {
        // Préparation
        UUID testId = UUID.randomUUID();

        when(mockRepository.findById(testId)).thenReturn(Optional.empty());

        // Exécution et vérification
        CategoryNotFoundByIdException thrownException = assertThrows(
            CategoryNotFoundByIdException.class,
            () -> serviceUnderTest.getById(testId)
        );

        assertEquals("Category with id " + testId + " not found", thrownException.getMessage());
    }

    @Test
    void testCreateCategory() {
        // Préparation
        String categoryName = "Sample Category";
        Category expectedCategory = new Category(categoryName);

        when(mockRepository.save(any(Category.class))).thenReturn(expectedCategory);

        // Exécution
        Category actualCategory = serviceUnderTest.create(categoryName);

        // Vérification
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void testUpdateCategoryName() throws CategoryNotFoundByIdException {
        // Préparation
        UUID testId = UUID.randomUUID();
        String initialName = "Initial Category";
        String updatedName = "Updated Category";

        Category existingCategory = new Category(initialName);
        Category updatedCategory = new Category(updatedName);

        when(mockRepository.findById(testId)).thenReturn(Optional.of(existingCategory));
        when(mockRepository.save(existingCategory)).thenReturn(updatedCategory);

        // Exécution
        Category actualCategory = serviceUnderTest.updateName(testId, updatedName);

        // Vérification
        assertEquals(updatedCategory, actualCategory);
    }
}
