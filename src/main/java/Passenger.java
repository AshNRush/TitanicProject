public class Passenger {
    public final int id;
    public final int survived;
    public final int pClass;
    public final String name;
    public final String sex;
    public final double age;
    public final int sibSp;
    public final int parch;
    public final String ticket;
    public final double fare;
    public final String cabin;
    public final char embarked;

    public Passenger(int id, int survived, int pClass, String name, String sex, double age, int sibSp, int parch,
                     String ticket, double fare, String cabin, char embarked) {
        this.id = id;
        this.survived = survived;
        this.pClass = pClass;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.sibSp = sibSp;
        this.parch = parch;
        this.ticket = ticket;
        this.fare = fare;
        this.cabin = cabin;
        this.embarked = embarked;
    }
}
