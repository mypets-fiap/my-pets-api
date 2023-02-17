package br.com.fiap.mypets.entity;

public class ResponseMyPetsEntity {
    private Object content;
    private String message;

    public ResponseMyPetsEntity(){
        super();
    }

    public ResponseMyPetsEntity(Object content){
        this.content = content;
    }

    public ResponseMyPetsEntity(String message){
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public String getMessage() {
        return message;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
