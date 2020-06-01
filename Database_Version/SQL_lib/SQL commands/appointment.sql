CREATE TABLE appointment (
    appointment_id int PRIMARY KEY AUTO_INCREMENT,
    appointment_date date NOT NULL,
    doctor_id int,
    patient_id int,
    FOREIGN KEY(doctor_id) REFERENCES doctor(doctor_id),
    FOREIGN KEY(patient_id) REFERENCES patient(patient_id)
); 
