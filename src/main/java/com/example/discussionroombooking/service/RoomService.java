package com.example.discussionroombooking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.discussionroombooking.model.BookedRoom;
import com.example.discussionroombooking.model.Room;
import com.example.discussionroombooking.repository.BookedRoomRepository;
import com.example.discussionroombooking.repository.RoomRepository;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookedRoomRepository bookedRoomRepository;

    public RoomService(RoomRepository roomRepository, BookedRoomRepository bookedRoomRepository) {
        this.roomRepository = roomRepository;
        this.bookedRoomRepository = bookedRoomRepository;
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
        Room room = getRoomById(id);
        room.setStatus(status);
        room.setUpdatedAt(LocalDateTime.now());
        return roomRepository.save(room);
    }

    public List<BookedRoom> getAllBookedRooms() {
        return bookedRoomRepository.findAll();
    }

    // Book a room
    public BookedRoom bookRoom(Long roomId, String bookedBy, String startTime, String endTime) {
        Room room = getRoomById(roomId);

        // Check overlapping booking (HH:mm string comparison works for same day)
        boolean conflict = bookedRoomRepository
                .existsByNameAndStatusAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        room.getName(), "booked", endTime, startTime);

        if (conflict) {
            throw new RuntimeException("Room already booked for this time slot!");
        }

        // Update Room status
        room.setStatus("booked");
        room.setUpdatedAt(LocalDateTime.now());
        roomRepository.save(room);

        // Record booking in BookedRoom
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setName(room.getName());
        bookedRoom.setStatus("booked");
        bookedRoom.setBookedBy(bookedBy);
        bookedRoom.setStartTime(startTime);
        bookedRoom.setEndTime(endTime);
        bookedRoom.setCreatedAt(LocalDateTime.now());
        bookedRoom.setUpdatedAt(LocalDateTime.now());

        return bookedRoomRepository.save(bookedRoom);
    }

    // Cancel booking
    public BookedRoom cancelBooking(Long bookedRoomId) {
        BookedRoom bookedRoom = bookedRoomRepository.findById(bookedRoomId)
                .orElseThrow(() -> new RuntimeException("Booked room not found"));

        bookedRoom.setStatus("canceled");
        bookedRoom.setUpdatedAt(LocalDateTime.now());
        bookedRoomRepository.save(bookedRoom);

        // Reset Room status to available
        Room room = roomRepository.findByName(bookedRoom.getName())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setStatus("available");
        room.setUpdatedAt(LocalDateTime.now());
        roomRepository.save(room);

        return bookedRoom;
    }
}
