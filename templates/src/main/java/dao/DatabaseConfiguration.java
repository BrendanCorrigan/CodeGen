package ${bean.packageName}.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatabaseConfiguration {
		
		public String name;
		public String driver;
		public String url;
		public String user;
		public String passwd;
		
		public DatabaseConfiguration() {
		}

		@Override
		public String toString() {
			return "DatabaseConfiguration [name=" + name + ", driver=" + driver + ", url=" + url + ", user=" + user
					+ ", passwd=" + passwd + "]";
		};
	};
