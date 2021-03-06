package ro.student.mtapo.advertisy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.student.mtapo.advertisy.models.Announcement;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

    List<Announcement> findByCategory_Id(int categoryId);

    List<Announcement> findByIsActiveTrueAndIsVisibleTrue();

    List<Announcement> findByUser_Id(int userId);

    List<Announcement> findAllByTitleContaining(String queryString);
}
