```java
package com.example.cms;

import java.util.ArrayList;
import java.util.List;

public class ContentManagementSystem {
    private List<Page> pages = new ArrayList<>();

    public void addPage(Page page) {
        pages.add(page);
    }

    public List<Page> getPages() {
        return pages;
    }

    public static class Page {
        private String title;
        private List<Module> modules = new ArrayList<>();

        public Page(String title) {
            this.title = title;
        }

        public void addModule(Module module) {
            modules.add(module);
        }

        public List<Module> getModules() {
            return modules;
        }
    }

    public static class Module {
        private String name;
        private String content;

        public Module(String name, String content) {
            this.name = name;
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }
    }
}
```