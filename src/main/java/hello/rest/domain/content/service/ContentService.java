package hello.rest.domain.content.service;

import hello.rest.domain.content.entity.Content;
import hello.rest.domain.content.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ContentService {

    @Autowired
    private final ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public List<Content> getContents() {
        return this.contentRepository.findAll();
    }

    public Content getContent(long contentId) {
        return this.contentRepository.findById(contentId).orElseThrow(NoSuchElementException::new);
    }

    public long uploadContent(Content content) {
        this.contentRepository.save(content);
        return content.getId();
    }

    public long modifyContent(Content content) {
        Content findContent = this.contentRepository.findById(content.getId()).orElseThrow(NoSuchElementException::new);
        findContent.setTitle(content.getTitle());
        findContent.setContent(content.getContent());
        return content.getId();
    }

    public long deleteContent(long contentId) {
        this.contentRepository.deleteById(contentId);
        return contentId;
    }
}
