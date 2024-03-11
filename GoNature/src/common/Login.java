package common;

public class Login {
		private String ID;
		private String password;
		
		public Login(String ID,String password) {
			this.ID=ID;
			this.password=password;
		}
		
		public String getID() {
			return ID;
		}
		
		public String getPassword() {
			return password;
		}
		
		public void setID(String iD) {
			ID = iD;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
}
