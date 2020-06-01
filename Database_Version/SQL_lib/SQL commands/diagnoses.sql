CREATE TABLE diagnoses (
	diagnoses_id int PRIMARY KEY AUTO_INCREMENT,
    patient_id int,
	doctor_id int,
    cure VARCHAR(64555),
    cured BOOL,
    FOREIGN KEY(doctor_id) REFERENCES doctor(doctor_id),
    FOREIGN KEY(patient_id) REFERENCES patient(patient_id)
); 