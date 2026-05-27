package io.vitor.fintrack.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse{
    private String message;
    private Integer status;
}
