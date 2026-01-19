//package com.example.domoycoursework.controllers
//
//import com.example.domoycoursework.services.PostService
//import jakarta.validation.Valid
//import org.springframework.web.bind.annotation.CrossOrigin
//import org.springframework.web.bind.annotation.DeleteMapping
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestHeader
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestPart
//import org.springframework.web.bind.annotation.RestController
//import org.springframework.web.multipart.MultipartFile
//
//
//@RestController
//@RequestMapping("/posts")
//@CrossOrigin(origins = ["*"])
//class PostController(
//    private var postService: PostService
//) {
//
//    @PostMapping("/create")
//    fun createPost(@RequestHeader("Authorization") token: String, @RequestPart("postDto") @Valid postDto: String, @RequestPart("image", required = false) image: MultipartFile? ): Post {
//        return postService.createPost(postService.convertToDto(postDto), token, image)
//    }
//
//    @PostMapping("/update/{id}")
//    fun updatePost(@RequestHeader("Authorization") token: String, @PathVariable id: Int, @RequestPart @Valid postDto: String, @RequestPart("image", required = false) image: MultipartFile? ): Post {
//        return postService.updatePost(id,postService.convertToDto(postDto), token, image)
//    }
//
//    @DeleteMapping("/delete/{id}")
//    fun deletePost(@RequestHeader("Authorization") token: String, @PathVariable id: Int) {
//        postService.deletePost(id, token)
//    }
//
////    @GetMapping("/all")
////    fun getAllPosts(): List<Post> {
////        return postService.getAllPosts()
////    }
//
//    @GetMapping("/image/{filename}")
//    fun getImage(@PathVariable filename: String): String {
//        return postService.getImageByFilename(filename)
//    }
//}