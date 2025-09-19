package com.example.discussionroombooking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussionroombooking.model.BookedRoom;
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

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PutMapping("/{id}")
    public Room updateRoomStatus(@PathVariable Long id, @RequestBody Room updatedRoom) {
        return roomService.updateRoomStatus(id, updatedRoom.getStatus());
    }

    // Book a room without DTO
    @PutMapping("/{id}/book")
    public ResponseEntity<BookedRoom> bookRoom(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String bookedBy = body.get("bookedBy");
        String startTime = body.get("startTime");
        String endTime = body.get("endTime");

        if (bookedBy == null || startTime == null || endTime == null) {
            throw new RuntimeException("Missing booking parameters");
        }

        BookedRoom bookedRoom = roomService.bookRoom(id, bookedBy, startTime, endTime);
        return ResponseEntity.ok(bookedRoom);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookedRoom> cancelBooking(@PathVariable Long id) {
        BookedRoom bookedRoom = roomService.cancelBooking(id);
        return ResponseEntity.ok(bookedRoom);
    }
}
