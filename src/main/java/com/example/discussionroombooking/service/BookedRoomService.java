package com.example.discussionroombooking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.discussionroombooking.model.BookedRoom;
import com.example.discussionroombooking.repository.BookedRoomRepository;

@Service
public class BookedRoomService {

    private final BookedRoomRepository bookedRoomRepository;

    public BookedRoomService(BookedRoomRepository bookedRoomRepository) {
        this.bookedRoomRepository = bookedRoomRepository;
    }

    public List<BookedRoom> getAllBookedRooms() {
        return bookedRoomRepository.findAll();
    }

    public BookedRoom recordBooking(String name, String bookedBy, String startTime, String endTime) {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setName(name);
        bookedRoom.setStatus("booked");
        bookedRoom.setBookedBy(bookedBy);
        bookedRoom.setStartTime(startTime);
        bookedRoom.setEndTime(endTime);
        bookedRoom.setCreatedAt(LocalDateTime.now());
        bookedRoom.setUpdatedAt(LocalDateTime.now());
        return bookedRoomRepository.save(bookedRoom);
    }

    public BookedRoom cancelBooking(Long id) {
        BookedRoom bookedRoom = bookedRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booked room not found"));
        bookedRoom.setStatus("canceled");
        bookedRoom.setUpdatedAt(LocalDateTime.now());
        return bookedRoomRepository.save(bookedRoom);
    }
}
