package models;

/**
 *
 * @author Mike Koukias
 */
public class Doctor {

    String doctor_id,first_name,last_name,job_id,email,phone_number,salary;

    public Doctor(String doctor_id, String first_name, String last_name, String job_id, String email, String phone_number, String salary) {
        this.doctor_id = doctor_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.job_id = job_id;
        this.email = email;
        this.phone_number = phone_number;
        this.salary = salary;
    }


    public String getDoctor_id(){
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id){
        this.doctor_id = doctor_id;
    }

    public String getFirst_name(){
        return first_name;
    }

    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }

    public String getLast_name(){
        return last_name;
    }

    public void setLast_name(String last_name){
        this.last_name = last_name;
    }
    public String getJob_id(){
        return job_id;
    }

    public void setJob_id(String job_id){
        this.job_id = job_id;
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
    public String getSalary(){
        return salary;
    }

    public void setSalary(String salary){
        this.salary = salary;
    }
}
