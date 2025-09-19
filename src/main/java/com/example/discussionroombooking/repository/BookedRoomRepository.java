package com.example.discussionroombooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.discussionroombooking.model.BookedRoom;

@Repository
public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {

    // Check if a room is booked during a time slot
    boolean existsByNameAndStatusAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            String name, String status, String endTime, String startTime);
}
