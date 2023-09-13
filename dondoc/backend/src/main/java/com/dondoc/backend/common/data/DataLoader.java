package com.dondoc.backend.common.data;

import com.dondoc.backend.group.entity.Category;
import com.dondoc.backend.group.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        categoryRepository.save(new Category(0L, "쇼핑"));
        categoryRepository.save(new Category(1L, "교육"));
        categoryRepository.save(new Category(2L, "식사"));
        categoryRepository.save(new Category(3L, "여가"));
        categoryRepository.save(new Category(4L, "생활요금"));
        categoryRepository.save(new Category(5L, "의료"));
        categoryRepository.save(new Category(6L, "기타"));

    }
}