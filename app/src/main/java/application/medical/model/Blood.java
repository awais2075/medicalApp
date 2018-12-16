package application.medical.model;

public class Blood {
    private String bloodId;
    private String bloodType;
    private String hospitalName;
    private int quantity;

    public Blood() {
    }

    public Blood(String bloodId, String bloodType, String hospitalName, int quantity) {
        this.bloodId = bloodId;
        this.bloodType = bloodType;
        this.hospitalName = hospitalName;
        this.quantity = quantity;
    }

    public String getBloodId() {
        return bloodId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public int getQuantity() {
        return quantity;
    }
}
