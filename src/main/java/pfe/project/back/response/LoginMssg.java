package pfe.project.back.response;

public class LoginMssg {
    String message;
    Boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LoginMssg() {

    }

    public LoginMssg(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
