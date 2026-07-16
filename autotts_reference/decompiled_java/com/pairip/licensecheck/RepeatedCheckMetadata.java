/*
 * Decompiled with CFR 0.152.
 */
package com.pairip.licensecheck;

public class RepeatedCheckMetadata {
    private final long durationToRetryMillis;
    private final long timeToRetryMillis;

    public RepeatedCheckMetadata(long l3, long l4) {
        if (l3 > 0L) {
            if (l4 > 0L) {
                this.durationToRetryMillis = l3;
                this.timeToRetryMillis = l4;
                return;
            }
            throw new IllegalArgumentException("Time to retry must be positive.");
        }
        throw new IllegalArgumentException("Duration to retry must be positive.");
    }

    public long getDurationToRetryMillis() {
        return this.durationToRetryMillis;
    }

    public long getTimeToRetryMillis() {
        return this.timeToRetryMillis;
    }
}

