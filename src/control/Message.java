package control;



public class Message {

     public static String message;

     private String tag;

     public String getMessage() {
         return this.message;
     }

     public void setMessage(String message) {
         this.message = message;
     }

     public String getStatus() {
         return this.tag;
     }

     public void setStatus(String tag) {
         this.tag = tag;
     }
}
