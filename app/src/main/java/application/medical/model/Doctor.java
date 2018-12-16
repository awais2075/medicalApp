package application.medical.model;

public class Doctor {
    private String doctorId;
    private String doctorName;
    private String doctorEmail;
    private String doctorType;
    private String doctorRating;

    public Doctor() {
    }

    public Doctor(String doctorId, String doctorName, String doctorEmail, String doctorType, String doctorRating) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorEmail = doctorEmail;
        this.doctorType = doctorType;
        this.doctorRating = doctorRating;
    }


    public String getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public String getDoctorRating() {
        return doctorRating;
    }
}
