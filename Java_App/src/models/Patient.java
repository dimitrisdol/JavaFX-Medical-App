package models;

public class Patient {

    String patient_id,patient_name,patient_lname,sickness,email,phone_number;

    public Patient(String patient_id, String patient_name, String patient_lname, String sickness, String email, String phone_number) {
        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.patient_lname = patient_lname;
        this.sickness = sickness;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getPatient_id(){
        return patient_id;
    }

    public void setPatient_id(String patient_id){
        this.patient_id = patient_id;
    }


    public String getPatient_name(){
        return patient_name;
    }

    public void setPatient_name(String patient_name){
        this.patient_name = patient_name;
    }


    public String getPatient_lname(){
        return patient_lname;
    }

    public void setPatient_lname(String patient_lname){ this.patient_lname = patient_lname; }


    public String getSickness(){
        return sickness;
    }

    public void setSickness(String sickness){
        this.sickness = sickness;
    }


    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }


    public String getPhone_number(){
        return phone_number;
    }

    public void setPhone_number(String phone_number){
        this.phone_number = phone_number;
    }

}


