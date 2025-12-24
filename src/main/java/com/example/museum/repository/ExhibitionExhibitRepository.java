package com.example.museum.repository;

import com.example.museum.model.ExhibitionExhibit;
import com.example.museum.model.ExhibitionExhibitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Репозиторий для работы с промежуточной сущностью "ExhibitionExhibit",
 * реализующей связь "многие ко многим" между выставками и экспонатами.
 * <p>
 * Наследуется от {@link JpaRepository}, что предоставляет стандартный набор
 * методов CRUD (Create, Read, Update, Delete) для работы с составным
 * первичным ключом {@link ExhibitionExhibitId}.
 */
@Repository
public interface ExhibitionExhibitRepository extends JpaRepository<ExhibitionExhibit, ExhibitionExhibitId> {
}