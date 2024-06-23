import java.time.LocalDate;

public class Employee {
    private int id;
    private String name;
    private String job;
    private int mgr;
    private LocalDate doj;
    private float sal;
    private float comm;
    private int dno;
    
    public Employee(int id, String name, String job, int mgr, LocalDate doj, float sal, float comm, int dno) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.mgr = mgr;
        this.doj = doj;
        this.sal = sal;
        this.comm = comm;
        this.dno = dno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getMgr() {
        return mgr;
    }

    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    public LocalDate getDoj() {
        return doj;
    }

    public void setDoj(LocalDate doj) {
        this.doj = doj;
    }

    public float getSal() {
        return sal;
    }

    public void setSal(float sal) {
        this.sal = sal;
    }

    public float getComm() {
        return comm;
    }

    public void setComm(float comm) {
        this.comm = comm;
    }

    public int getDno() {
        return dno;
    }

    public void setDno(int dno) {
        this.dno = dno;
    }

}
