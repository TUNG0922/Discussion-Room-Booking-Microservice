package com.example.discussionroombooking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.discussionroombooking.model.Room;
import com.example.discussionroombooking.repository.RoomRepository;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room createRoom(Room room) {
        room.setStatus("available");
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());
        return roomRepository.save(room);
    }

    public Room updateRoomStatus(Long id, String status) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setStatus(status);
        room.setUpdatedAt(LocalDateTime.now());
        return roomRepository.save(room);
    }

    public Room bookRoom(Long id, String username, String startTime, String endTime) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if ("booked".equalsIgnoreCase(room.getStatus())) {
            throw new RuntimeException("Room already booked!");
        }

        room.setStatus("booked");
        room.setBookedBy(username);
        room.setStartTime(startTime);
        room.setEndTime(endTime);
        room.setUpdatedAt(LocalDateTime.now());

        return roomRepository.save(room);
    }

    public Room cancelBooking(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setStatus("available");
        room.setBookedBy(null);
        room.setStartTime(null);
        room.setEndTime(null);
        room.setUpdatedAt(LocalDateTime.now());
        return roomRepository.save(room);
    }
}
