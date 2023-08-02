package controller;

import org.example.controller.BlogPostController;
import org.example.model.BlogPost;
import org.example.repository.BlogPostRepository;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class BlogPostControllerTest {
    @InjectMocks
    private BlogPostController blogPostController;

    @Mock
    private BlogPostRepository blogPostRepository;

    @Test
    public void testCreateBlogPost() {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("Unit Testing in Spring Boot");
        blogPost.setBody("This is a blog post about unit testing in Spring Boot");
        blogPost.setAuthor("John Doe");
        Mockito.when(blogPostRepository.save(blogPost)).thenReturn(blogPost);

        ResponseEntity<BlogPost> responseEntity = blogPostController.createBlogPost(blogPost);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(blogPost, responseEntity.getBody());
    }

    @Test
    public void testGetBlogPostById() {
        Long id = 1L;
        BlogPost blogPost = new BlogPost();
        blogPost.setId(id);
        blogPost.setTitle("Unit Testing in Spring Boot");
        blogPost.setBody("This is a blog post about unit testing in Spring Boot");
        blogPost.setAuthor("John Doe");
        Mockito.when(blogPostRepository.findById(id)).thenReturn(Optional.of(blogPost));

        ResponseEntity<BlogPost> responseEntity = blogPostController.getBlogPostById(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(blogPost, responseEntity.getBody());
    }

    @Test
    public void testGetAllBlogPosts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<BlogPost> blogPosts = new PageImpl<>(Arrays.asList(new BlogPost(), new BlogPost()));
        Mockito.when(blogPostRepository.findAll(pageable)).thenReturn(blogPosts);

        ResponseEntity<Page<BlogPost>> responseEntity = blogPostController.getAllBlogPosts(pageable);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(blogPosts, responseEntity.getBody());
    }

    @Test
    public void testUpdateBlogPost() {
        Long id = 1L;
        BlogPost updatedBlogPost = new BlogPost();
        updatedBlogPost.setTitle("Updated Title");
        updatedBlogPost.setBody("Updated Body");
        updatedBlogPost.setAuthor("Updated Author");
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("Original Title");
        blogPost.setBody("Original Body");
        blogPost.setAuthor("Original Author");
        Mockito.when(blogPostRepository.findById(id)).thenReturn(Optional.of(blogPost));
        Mockito.when(blogPostRepository.save(blogPost)).thenReturn(blogPost);

        ResponseEntity<BlogPost> responseEntity = blogPostController.updateBlogPost(updatedBlogPost, id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(blogPost, responseEntity.getBody());
        assertEquals(updatedBlogPost.getTitle(), blogPost.getTitle());
        assertEquals(updatedBlogPost.getBody(), blogPost.getBody());
        assertEquals(updatedBlogPost.getAuthor(), blogPost.getAuthor());
    }

    @Test
    public void testDeleteBlogPost() {
        Long id = 1L;
        BlogPost blogPost = new BlogPost();
        blogPost.setId(id);
        blogPost.setTitle("Unit Testing in Spring Boot");
        blogPost.setBody("This is a blog post about unit testing in Spring Boot");
        blogPost.setAuthor("John Doe");
        Mockito.when(blogPostRepository.findById(id)).thenReturn(Optional.of(blogPost));

        ResponseEntity<HttpStatus> responseEntity = blogPostController.deleteBlogPost(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
