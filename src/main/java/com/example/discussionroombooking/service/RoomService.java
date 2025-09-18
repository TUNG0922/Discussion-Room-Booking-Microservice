package com.example.discussionroombooking.service;

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

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoomStatus(Long id, String status) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setStatus(status);
        return roomRepository.save(room);
    }

    public Room bookRoom(Long id, String username) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if ("booked".equalsIgnoreCase(room.getStatus())) {
            throw new RuntimeException("Room already booked!");
        }

        room.setStatus("booked");
        room.setBookedBy(username);
        return roomRepository.save(room);
    }
    
    public Room cancelBooking(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setStatus("Available");
        room.setBookedBy(null);
        return roomRepository.save(room);
    }
}
