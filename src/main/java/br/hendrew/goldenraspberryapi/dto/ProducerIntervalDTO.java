package br.hendrew.goldenraspberryapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProducerIntervalDTO {
    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;
}
