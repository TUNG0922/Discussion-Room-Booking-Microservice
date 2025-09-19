package com.example.discussionroombooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.discussionroombooking.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Find a room by name
    Optional<Room> findByName(String name);
}
