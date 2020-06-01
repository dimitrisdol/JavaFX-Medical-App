package models;

public class Cure {
    String diagnoses_id,patient_id,doctor_id,cure,cured;

    public Cure(String diagnoses_id, String patient_id, String doctor_id, String cure, String cured) {
        this.diagnoses_id = diagnoses_id;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.cure = cure;
        this.cured = cured;
    }

    public String getDiagnoses_id(){
        return diagnoses_id;
    }

    public void setDiagnoses_id(String diagnoses_id){
        this.diagnoses_id = diagnoses_id;
    }


    public String getPatient_id(){
        return patient_id;
    }

    public void setPatient_id(String patient_id){
        this.patient_id = patient_id;
    }


    public String getDoctor_id(){
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id){ this.doctor_id = doctor_id; }


    public String getCure(){
        return cure;
    }

    public void setCure(String cure){
        this.cure = cure;
    }


    public String getCured(){
        return cured;
    }

    public void setCured(String cured){
        this.cured = cured;
    }


}
