package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols.DimmerSubControl;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols.SubControl;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols.SwitchSubControl;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols.UnknownSubControl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LightingControl extends AbstractControl {
	public static final String TYPE = "LightControllerV2";

	@JsonTypeInfo(defaultImpl = UnknownSubControl.class, use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
	@JsonSubTypes({
			@JsonSubTypes.Type(name = SwitchSubControl.TYPE, value = SwitchSubControl.class),
			@JsonSubTypes.Type(name = DimmerSubControl.TYPE, value = DimmerSubControl.class)
	})
	private Map<String, SubControl> subControls;

	@Override
	public String getType() {
		return TYPE;
	}
}
