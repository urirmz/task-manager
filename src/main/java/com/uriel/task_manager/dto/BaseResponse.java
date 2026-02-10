package com.uriel.task_manager.dto;

import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

public class BaseResponse {

    private @Nullable String status;
    private @Nullable LocalDateTime timeStamp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        BaseResponse that = (BaseResponse) o;
        return Objects.equals(status, that.status) && Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, timeStamp);
    }
}