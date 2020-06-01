package models;

public class Appointment {

    String appointment_id,appointment_date,doctor_id,patient_id;

    public Appointment(String appointment_id, String appointment_date, String doctor_id, String patient_id) {
        this.appointment_id = appointment_id;
        this.appointment_date = appointment_date;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
    }

    public String getAppointment_id(){
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id){
        this.appointment_id = appointment_id;
    }


    public String getAppointment_date(){
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date){
        this.appointment_date = appointment_date;
    }


    public String getDoctor_id(){
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id){ this.doctor_id = doctor_id; }


    public String getPatient_id(){
        return patient_id;
    }

    public void setPatient_id(String patient_id){
        this.patient_id = patient_id;
    }


}

