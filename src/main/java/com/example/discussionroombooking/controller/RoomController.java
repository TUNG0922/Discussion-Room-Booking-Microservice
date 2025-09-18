package com.example.discussionroombooking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussionroombooking.model.Room;
import com.example.discussionroombooking.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @PutMapping("/{id}")
    public Room updateRoomStatus(@PathVariable Long id, @RequestBody Room updatedRoom) {
        return roomService.updateRoomStatus(id, updatedRoom.getStatus());
    }

    // ✅ Book a room and save username
    @PutMapping("/{id}/book")
    public ResponseEntity<Room> bookRoom(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        Room bookedRoom = roomService.bookRoom(id, username);
        return ResponseEntity.ok(bookedRoom);
    }

    // Cancel booking: set status = Available and bookedBy = null
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Room> cancelBooking(@PathVariable Long id) {
        Room room = roomService.cancelBooking(id);
        if (room != null) {
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
