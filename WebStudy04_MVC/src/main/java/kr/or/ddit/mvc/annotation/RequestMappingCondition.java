package kr.or.ddit.mvc.annotation;

/**
 * @RequestMapping에 설정된 핸들러의 매핑 가능한 요청 정보(uri, method)
 * 둘을 캡슐화할 때 사용할 객체
 *
 */
public class RequestMappingCondition {
	private String url;
	private RequestMethod method;
	
	public RequestMappingCondition(String url, RequestMethod method) {
		this.url = url;
		this.method = method;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestMappingCondition other = (RequestMappingCondition) obj;
		if (method != other.method)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String getUrl() {
		return url;
	}

	public RequestMethod getMethod() {
		return method;
	}

	@Override
	public String toString() {
		return "요청 매핑 정보 [url=" + url + ", method=" + method + "]";
	}
}