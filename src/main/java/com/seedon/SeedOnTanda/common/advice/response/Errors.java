package com.seedon.SeedOnTanda.common.advice.response;

public record Errors(
        String field,
        String errors
) {
}
