package com.amazon.Controller;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NearByStoreRequest {

    @NotBlank(message = "pincode field cannot be empty !! Please enter Pincode. ")
    @Size(min = 6, max = 6, message = "Indian pincode should consist of 6 digits!!")
    @Pattern(regexp = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$", message = "Not a proper pincode pattern. Please enter properly!!")
    private String pincode;


    @NotNull(message = "radius field cannot be empty!! Please enter some radius.")
    @Min(0)
    private Double radius;


    @NotNull
    private String category;
    
    @NotNull(message="Please select one !!")
    private String factory;
}