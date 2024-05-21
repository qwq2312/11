package springboot.common.websocket;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseJson extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	private static final Integer SUCCESS_STATUS = 200;
	private static final Integer ERROR_STATUS = -1;
	private static final String SUCCESS_MSG = "一切正常";

	public ResponseJson() {
		super();
	}

	public ResponseJson(int code) {
		super();
		setStatus(code);
	}

	public ResponseJson(HttpStatus status) {
		super();
		setStatus(status.value());
		setMsg(status.getReasonPhrase());
	}

	public ResponseJson success() {
		put("msg", SUCCESS_MSG);
		put("status", SUCCESS_STATUS);
		return this;
	}

	public ResponseJson success(String msg) {
		put("msg", msg);
		put("status", SUCCESS_STATUS);
		return this;
	}

	public ResponseJson error(String msg) {
		put("msg", msg);
		put("status", ERROR_STATUS);
		return this;
	}

	public ResponseJson setData(String key, Object obj) {
		Map<String, Object> data = (Map<String, Object>) get("data");
		if (data == null) {
			data = new HashMap<String, Object>();
			put("data", data);
		}
		data.put(key, obj);
		return this;
	}

	public ResponseJson addToCollection(String collectionName) {
		List<Map<String, Object>> collections = (List<Map<String, Object>>) get(collectionName);
		if (collections == null) {
			collections = new ArrayList<>();
			put(collectionName, collections);
		}
		collections.add((Map<String, Object>) get("data"));
		remove("data");
		return this;
	}

	public ResponseJson setCollectionToData(String collectionName) {
		List<Map<String, Object>> collections = (List<Map<String, Object>>) get(collectionName);
		this.setData(collectionName, collections);
		remove(collectionName);
		return this;
	}

	public ResponseJson setStatus(int status) {
		put("status", status);
		return this;
	}

	public ResponseJson setMsg(String msg) {
		put("msg", msg);
		return this;
	}

	public ResponseJson setValue(String key, Object val) {
		put(key, val);
		return this;
	}

	/**
	 * 返回JSON字符串
	 */
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
