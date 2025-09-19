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

import com.example.discussionroombooking.model.BookedRoom;
import com.example.discussionroombooking.service.BookedRoomService;

@RestController
@RequestMapping("/api/booked-rooms")
@CrossOrigin(origins = "*")
public class BookedRoomController {

    private final BookedRoomService bookedRoomService;

    public BookedRoomController(BookedRoomService bookedRoomService) {
        this.bookedRoomService = bookedRoomService;
    }

    @GetMapping
    public List<BookedRoom> getAllBookedRooms() {
        return bookedRoomService.getAllBookedRooms();
    }

    @PostMapping
    public ResponseEntity<BookedRoom> createBooking(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String bookedBy = payload.get("bookedBy");
        String startTime = payload.get("startTime");
        String endTime = payload.get("endTime");

        BookedRoom bookedRoom = bookedRoomService.recordBooking(name, bookedBy, startTime, endTime);
        return ResponseEntity.ok(bookedRoom);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookedRoom> cancelBooking(@PathVariable Long id) {
        BookedRoom bookedRoom = bookedRoomService.cancelBooking(id);
        return ResponseEntity.ok(bookedRoom);
    }
}
