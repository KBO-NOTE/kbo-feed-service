package com.choyeji.kbofeedservice.domain.content.repository;

import com.choyeji.kbofeedservice.domain.content.entity.Image;
import com.choyeji.kbofeedservice.domain.content.image.dto.ContentImageItemResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("""
            select new com.choyeji.kbofeedservice.domain.content.image.dto.ContentImageItemResponse(
                i.id, i.imageUrl, i.orderIndex
            )
            from Image i
            where i.content.id = :contentId
            order by
                case when i.orderIndex is null then 1 else 0 end asc,
                i.orderIndex asc,
                i.id asc
            """)
    List<ContentImageItemResponse> findImagesByContentId(@Param("contentId") Long contentId);
}
