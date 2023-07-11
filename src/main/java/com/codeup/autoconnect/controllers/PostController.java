package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Comment;
import com.codeup.autoconnect.models.Post;
import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.CommentRepository;
import com.codeup.autoconnect.repositories.PostRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    public final PostRepository postsDao;
    public final UserRepository usersDao;

    public final CommentRepository commentsDao;

    public PostController(PostRepository postsDao, UserRepository usersDao, CommentRepository commentsDao) {
        this.postsDao = postsDao;
        this.usersDao = usersDao;
        this.commentsDao = commentsDao;
    }

    @GetMapping("/posts")
    public String viewAllPosts(Model model) {
        model.addAttribute("posts", postsDao.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "posts/index";
    }

    @GetMapping("/posts/{id}/post")
    public String viewPosts(Model model, @PathVariable long id) {
        model.addAttribute("posts", postsDao.findById(id).get());
        return "posts/view";
    }


    @GetMapping("/posts/create")
    public String createPost(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }
    @PostMapping("/posts/create")
    public String addPost(@ModelAttribute Post post){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(loggedInUser);
        postsDao.save(post);
        return "loading_template";
    }


    @GetMapping("posts/{id}/edit")
    public String editPost(@PathVariable long id, Model model) {
        if (postsDao.findById(id).isPresent()) {
            Post postToEdit = postsDao.findById(id).get();
            model.addAttribute("post", postToEdit);
        }
        return "posts/edit";
    }
    @PostMapping("posts/{id}/edit")
    public String updatePost(@ModelAttribute Post newPost) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setUser(user);
        postsDao.save(newPost);
        return "loading_template";
    }


    @PostMapping("posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postsDao.deleteById(id);
        return "loading_template";
    }

    @PostMapping("posts/{id}/comment")
    public String addComment(@PathVariable long id, @RequestParam String content) {
        Comment newComment = new Comment();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postsDao.findById(id).get();
        newComment.setContent(content);
        newComment.setPost(post);
        newComment.setUser(user);
        commentsDao.save(newComment);
        return "redirect:/posts/" + id + "/post" ;
    }

}
