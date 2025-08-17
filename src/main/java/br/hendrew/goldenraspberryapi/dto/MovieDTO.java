package br.hendrew.goldenraspberryapi.dto;

public record MovieDTO(
        Integer year,
        String title,
        String studios,
        String producers,
        boolean winner
) {}
