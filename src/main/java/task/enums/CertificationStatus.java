package task.enums;

public enum CertificationStatus {
    CONSIDERED(0),
    CERTIFIED(1),
    REFUSED(3),
    REMOVED(2);

    private byte value;

    CertificationStatus(int number) {
        this.value = (byte) number;
    }

    public byte getValue() {
        return value;
    }

    public static String getClassName() {
        return CertificationStatus.class.getName();
    }
}
