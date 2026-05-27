package io.vitor.fintrack.database.repository;

import io.vitor.fintrack.database.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
