package ru.ovb.hw;

public class HealthStatus {
    public enum HealthStatuses {
        OK, BAD
    }

    private final HealthStatuses status;

    public HealthStatus(HealthStatuses status) {
        this.status = status;
    }

    public HealthStatuses getStatus() {
        return status;
    }
}
