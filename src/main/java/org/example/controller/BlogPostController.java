package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.model.BlogPost;
import org.example.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/blogpost")
public class BlogPostController {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @PostMapping("/create")
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPost blogPost) {
        BlogPost savedBlogPost = blogPostRepository.save(blogPost);
        return new ResponseEntity<>(savedBlogPost, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPostById(@PathVariable Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            return new ResponseEntity<>(blogPost.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Page<BlogPost>> getAllBlogPosts(Pageable pageable) {
        Page<BlogPost> blogPosts = blogPostRepository.findAll(pageable);
        return new ResponseEntity<>(blogPosts, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@RequestBody BlogPost updatedBlogPost, @PathVariable Long id) {
        Optional<BlogPost> optionalBlogPost = blogPostRepository.findById(id);
        if (optionalBlogPost.isPresent()) {
            BlogPost blogPost = optionalBlogPost.get();
            blogPost.setTitle(updatedBlogPost.getTitle());
            blogPost.setBody(updatedBlogPost.getBody());
            blogPost.setAuthor(updatedBlogPost.getAuthor());
            blogPost.setModifiedDate(new Date());
            BlogPost savedBlogPost = blogPostRepository.save(blogPost);
            return new ResponseEntity<>(savedBlogPost, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBlogPost(@PathVariable Long id) {
        Optional<BlogPost> optionalBlogPost = blogPostRepository.findById(id);
        if (optionalBlogPost.isPresent()) {
            blogPostRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
