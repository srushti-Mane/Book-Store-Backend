package com.example.demo;

public class Response {

	 String message;
	    Object obj;
		public Response(String message, Object obj) {
			super();
			this.message = message;
			this.obj = obj;
		}
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public Object getObj() {
			return obj;
		}
		public void setObj(Object obj) {
			this.obj = obj;
		}
	    
	    
}
