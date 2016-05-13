package com.mpms.web.messageconverter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 自定义jackson 枚举序列化
 *
 * ClassName: EnumTestJsonSerializer 
 * @Description: jackson 枚举序列化
 *
 * @author tangzhi
 * @date 2015-12-21
 * @version
 */
@SuppressWarnings("rawtypes")
public class CustomEnumJsonSerializer extends JsonSerializer<Enum> {

	private Log log = LogFactory.getLog(CustomEnumJsonSerializer.class);
	
	public CustomEnumJsonSerializer() {
		super();
	}


	@Override
	public Class<Enum> handledType() {
		return Enum.class;
	}


	@Override
	public void serialize(Enum source, JsonGenerator jgen,
			SerializerProvider serializers) throws IOException,
			JsonProcessingException {
		if(null == source){
			jgen.writeObject("");  
			return;
		}
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		try {
			data.put("name", source.name());
			data.put("ordinal", source.ordinal());
			Class<?> c = source.getClass();
			Field [] fields = c.getDeclaredFields();
			for(Field f : fields){// 多余的属性处理
				boolean isStatic = Modifier.isStatic(f.getModifiers());
				if(!isStatic){
					f.setAccessible(true);
					Object fValue = f.get(source);
					data.put(f.getName(), fValue);
				}
			}
			// 去除多余数据
			for(Object obj : c.getEnumConstants()){
				data.remove(obj.toString());
			}
			data.remove("$VALUES");
			data.remove("ENUM$VALUES");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("枚举转换异常，"+source);
			jgen.writeObject("");  
			return ;
		}
		try {
			jgen.writeObject(data);  
		} catch (Exception e) {
			e.printStackTrace();
			log.error("枚举转换异常，"+source);
			jgen.writeObject("");  
		}		
	}
	
	public static void main(String[] args) {
		
	}
	

}
