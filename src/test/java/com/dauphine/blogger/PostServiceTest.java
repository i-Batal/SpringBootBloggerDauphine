package com.dauphine.blogger;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.repository.PostRepository;
import com.dauphine.blogger.services.CategoryService;
import com.dauphine.blogger.services.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.services.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.services.impl.PostServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostServiceImplTest {

    private PostServiceImpl postService;

    private CategoryService categoryService;

    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        categoryService = mock(CategoryService.class);
        postService = new PostServiceImpl(postRepository,categoryService);
    }

    @Test
    void shouldReturnPostWhenIdExists() throws PostNotFoundByIdException {
        //Arrange
        UUID id = UUID.randomUUID();
        Post expected = new Post("Title", "Content", new Category("Category"));

        when(postRepository.findById(id)).thenReturn(Optional.of(expected));

        //Act
        Post actual = postService.getById(id);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExists() {
        //Arrange
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        PostNotFoundByIdException exception = assertThrows(
                PostNotFoundByIdException.class,
                () -> postService.getById(id)
        );

        //Assert
        assertEquals("Post with id " + id + " not found", exception.getMessage());
    }

    @Test
    void shouldCreatePost() throws CategoryNotFoundByIdException {
        //Arrange
        String title = "New Title";
        String content = "New Content";
        Category category = new Category("Category");
        Post expected = new Post(title, content, category);
        when(postRepository.save(any())).thenReturn(expected);

        //Act
        Post actual = postService.create(title, content, category.getId());

        //Assert
        assertEquals(expected, actual);
    }

}