package trustnet.auth.log.service.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class ServiceLogInfoVO {
	int id;
	String date;
	String description;
	
	public ServiceLogInfoVO(String description){
		Date time = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.date = format.format(time);
		this.description = description;
	}
}
