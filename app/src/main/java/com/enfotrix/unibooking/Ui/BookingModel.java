package com.enfotrix.unibooking.Ui;

public class BookingModel {

    String BookingId, Hallid,studentEmail, date,time, reason ,status,participants;

    public BookingModel() {
    }

    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String bookingId) {
        BookingId = bookingId;
    }

    public String getHallid() {
        return Hallid;
    }

    public void setHallid(String hallid) {
        Hallid = hallid;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public BookingModel(String bookingId, String hallid, String studentEmail, String date, String time, String reason, String status, String participants) {
        BookingId = bookingId;
        Hallid = hallid;
        this.studentEmail = studentEmail;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.status = status;
        this.participants = participants;
    }
}
