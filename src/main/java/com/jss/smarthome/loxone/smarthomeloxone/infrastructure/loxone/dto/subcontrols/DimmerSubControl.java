package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DimmerSubControl extends SubControl{
	public static final String TYPE = "Dimmer";

	@Override
	public String getType() {
		return TYPE;
	}
}
