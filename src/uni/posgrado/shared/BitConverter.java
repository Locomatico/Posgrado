package uni.posgrado.shared;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

 
@Converter
public class BitConverter implements AttributeConverter<Boolean, Character > {	
	
	@Override
    public Character convertToDatabaseColumn(Boolean attribute) { 
    	/*String strToConvert = "0";
    	if(attribute)
    		strToConvert = "1";
    	byte [] bytes = strToConvert.getBytes();
    	StringBuilder bits = new StringBuilder(bytes.length * 8); 
    	for(byte b:bytes) {
    		bits.append(Integer.toString(b, 1));
    	} 
    	return bits;*/
 	
    	return '1';
    }
 
    @Override
    public Boolean convertToEntityAttribute(Character dbData) {
        switch (dbData.toString()) {
            case "1":
                return true;
            case "0":
                return false;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}
