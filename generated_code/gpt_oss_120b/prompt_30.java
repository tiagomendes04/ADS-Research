```java
// File: src/main/java/com/example/cms/model/Module.java
package com.example.cms.model;

import java.util.UUID;

public abstract class Module {
    private final String id = UUID.randomUUID().toString();
    private String title;

    public Module(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public abstract String render();
}
```

```java
// File: src/main/java/com/example/cms/model/TextModule.java
package com.example.cms.model;

public class TextModule extends Module {
    private String content;

    public TextModule(String title, String content) {
        super(title);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String render() {
        return "<div class=\"text-module\"><h2>" + getTitle() + "</h2><p>" + content + "</p></div>";
    }
}
```

```java
// File: src/main/java/com/example/cms/model/ImageModule.java
package com.example.cms.model;

public class ImageModule extends Module {
    private String imageUrl;
    private String altText;

    public ImageModule(String title, String imageUrl, String altText) {
        super(title);
        this.imageUrl = imageUrl;
        this.altText = altText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    @Override
    public String render() {
        return "<div class=\"image-module\"><h2>" + getTitle() + "</h2>"
                + "<img src=\"" + imageUrl + "\" alt=\"" + altText + "\"/></div>";
    }
}
```

```java
// File: src/main/java/com/example/cms/model/Page.java
package com.example.cms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Page {
    private final String id = UUID.randomUUID().toString();
    private String title;
    private final List<Module> modules = new ArrayList<>();

    public Page(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Module> getModules() {
        return Collections.unmodifiableList(modules);
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void removeModule(String moduleId) {
        modules.removeIf(m -> m.getId().equals(moduleId));
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>").append(title).append("</title></head><body>");
        sb.append("<h1>").append(title).append("</h1>");
        for (Module module : modules) {
            sb.append(module.render());
        }
        sb.append("</body></html>");
        return sb.toString();
    }
}
```

```java
// File: src/main/java/com/example/cms/repository/PageRepository.java
package com.example.cms.repository;

import com.example.cms.model.Page;

import java.util.Optional;
import java.util.List;

public interface PageRepository {
    Page save(Page page);
    Optional<Page> findById(String id);
    List<Page> findAll();
    void deleteById(String id);
}
```

```java
// File: src/main/java/com/example/cms/repository/InMemoryPageRepository.java
package com.example.cms.repository;

import com.example.cms.model.Page;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPageRepository implements PageRepository {
    private final Map<String, Page> store = new ConcurrentHashMap<>();

    @Override
    public Page save(Page page) {
        store.put(page.getId(), page);
        return page;
    }

    @Override
    public Optional<Page> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Page> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(String id) {
        store.remove(id);
    }
}
```

```java
// File: src/main/java/com/example/cms/service/PageService.java
package com.example.cms.service;

import com.example.cms.model.Page;
import com.example.cms.model.Module;
import com.example.cms.repository.PageRepository;

import java.util.List;
import java.util.Optional;

public class PageService {
    private final PageRepository repository;

    public PageService(PageRepository repository) {
        this.repository = repository;
    }

    public Page createPage(String title) {
        Page page = new Page(title);
        return repository.save(page);
    }

    public Optional<Page> getPage(String id) {
        return repository.findById(id);
    }

    public List<Page> listPages() {
        return repository.findAll();
    }

    public void deletePage(String id) {
        repository.deleteById(id);
    }

    public void addModule(String pageId, Module module) {
        Page page = repository.findById(pageId)
                .orElseThrow(() -> new IllegalArgumentException("Page not found: " + pageId));
        page.addModule(module);
        repository.save(page);
    }

    public void removeModule(String pageId, String moduleId) {
        Page page = repository.findById(pageId)
                .orElseThrow(() -> new IllegalArgumentException("Page not found: " + pageId));
        page.removeModule(moduleId);
        repository.save(page);
    }
}
```

```java
// File: src/main/java/com/example/cms/CMSApplication.java
package com.example.cms;

import com.example.cms.model.*;
import com.example.cms.repository.InMemoryPageRepository;
import com.example.cms.service.PageService;

public class CMSApplication {
    public static void main(String[] args) {
        PageService pageService = new PageService(new InMemoryPageRepository());

        // Create a page
        Page homePage = pageService.createPage("Home");

        // Add modules
        pageService.addModule(homePage.getId(),
                new TextModule("Welcome", "Welcome to our website!"));
        pageService.addModule(homePage.getId(),
                new ImageModule("Banner", "https://example.com/banner.jpg", "Banner Image"));

        // Render page
        String html = homePage.render();
        System.out.println(html);
    }
}
```