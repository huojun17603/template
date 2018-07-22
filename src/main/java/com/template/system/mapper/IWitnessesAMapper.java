package com.template.system.mapper;

import java.util.List;
import java.util.Map;

public interface IWitnessesAMapper {

    List<Map<String,Object>> selectByExample(Map<String, Object> example);

}
