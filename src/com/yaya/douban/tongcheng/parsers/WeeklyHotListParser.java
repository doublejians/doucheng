package com.yaya.douban.tongcheng.parsers;

import java.util.ArrayList;

import com.yaya.douban.common.http.BaseDataParser;
import com.yaya.douban.common.http.BaseDataResponse;
import com.yaya.douban.common.http.JsonSerializer;
import com.yaya.douban.tongcheng.responses.TCEventListResponse;
import com.yaya.douban.tongcheng.types.TCEvent;
import com.yaya.douban.tongcheng.types.json.TCEventJsonType;

public class WeeklyHotListParser extends BaseDataParser {

	  @Override
	  public BaseDataResponse parser(String json) {
	    TCEventJsonType data = JsonSerializer.getInstance().deserialize(json,
	        TCEventJsonType.class);
	    TCEventListResponse response = new TCEventListResponse();
	    ArrayList<TCEvent> events = new ArrayList<TCEvent>();
	    for (TCEvent event : data.getEvents()) {
	      events.add(event);
	    }
	    response.setData(events);
	    response.setTotal(data.getTotal());
	    response.setResultCode(200);
	    return response;
	  }

	}
