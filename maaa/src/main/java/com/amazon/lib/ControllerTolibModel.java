package com.amazon.lib;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ControllerTolibModel {
	
	@NotBlank
	private String pincode;
	
	
	@NotNull
	@Min(1)
	@Max(100)
	private Double radius;
	
	
	@NotNull
	private String category;
}
