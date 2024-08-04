package hello.rest.domain.content.controller;

import hello.rest.domain.content.entity.Content;
import hello.rest.domain.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/json/content")
public class ContentController {

    @Autowired
    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public ResponseEntity<List<Content>> getContents() {
        return ResponseEntity.ok(contentService.getContents());
    }

    @GetMapping("/{content-id}")
    public ResponseEntity<Content> getContent(@PathVariable("content-id") long contentId) {
        return ResponseEntity.ok(contentService.getContent(contentId));
    }

    @PostMapping
    public ResponseEntity<Long> uploadContent(@RequestBody Content content) {
        return ResponseEntity.ok(contentService.uploadContent(content));
    }

    @PutMapping
    public ResponseEntity<Long> modifyContent(@RequestBody Content content) {
        return ResponseEntity.ok(contentService.modifyContent(content));
    }

    @DeleteMapping("/{content-id}")
    public ResponseEntity<Long> deleteContent(@PathVariable("content-id") long contentId) {
        return ResponseEntity.ok(contentService.deleteContent(contentId));
    }
}
