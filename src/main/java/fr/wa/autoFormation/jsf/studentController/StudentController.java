package fr.wa.autoFormation.jsf.studentController;

import fr.wa.autoFormation.jsf.DAO.StudentDAO;
import fr.wa.autoFormation.jsf.models.Student;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Said B on 29/05/2017.
 */

@ManagedBean
@SessionScoped
public class StudentController {

    private String firstName;
    private String lastName;
    private String country;
    private String favoriteLanguage;
    private String countryOrCity;
    private String code;
    private List<String> favoriteLanguages;




    private List<Student> students;
    private StudentDAO studentDao;

    private Student student=new Student();


    public StudentController() throws Exception {
        students = new ArrayList<>();
        studentDao = StudentDAO.getInstance();
    }



    public void loadStudents(){
        students.clear();
        try {
            students = studentDao.getStudents();
        }catch (Exception exc) {
            addErrorMessage(exc);
        }
    }

    public String loadStudent(int id){

        try {
            student = studentDao.getStudentById(id);

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String,Object> requestMap = externalContext.getRequestMap();
            requestMap.put("student", student);
        }catch (Exception exc) {
            addErrorMessage(exc);
            return null;
        }
        return "update";
    }

    private void addErrorMessage ( Exception e){
        FacesMessage message = new FacesMessage("Error" + e.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public String addStudent ( Student st ){
        try{

            studentDao.add(st);

        }
        catch(Exception ex){
            addErrorMessage(ex);
        }
        return "students";
    }

    public String updateStudent ( Student st ){
        try{

            studentDao.update(st);

        }
        catch(Exception ex){
            addErrorMessage(ex);
        }
        return "students?faces-redirect=true";
    }

    public String deleteStudent ( int id ){
        try{

            studentDao.delete(id);

        }
        catch(Exception ex){
            addErrorMessage(ex);
        }
        return "students?faces-redirect=true";
    }


    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", String.valueOf(((Student) event.getObject()).getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(((Student) event.getObject()).getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    //Conditional navigation
    public String choiceCountryOrCity(){
        if (countryOrCity != null && countryOrCity.equals("city")){
            return "city";
        }
        else return "country";
    }



    //methode validation
    public void validateCode (FacesContext context, UIComponent component, Object value) throws ValidatorException{

        if ( value == null){
            return;
        }
        String code = value.toString();

        if ( !code.startsWith("SA")){
            FacesMessage message = new FacesMessage("Le code doit commencer par SA");
            throw new ValidatorException(message);
        }
    }




















    public Student getStudent() {

        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }



/*    public void setStudents(List<Student> students) {
        this.students = students;
    }
    private List<Student> getStudents(){
        return students;
    }*/

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getCountryOrCity() {
        return countryOrCity;
    }

    public void setCountryOrCity(String countryOrCity) {
        this.countryOrCity = countryOrCity;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public List<String> getFavoriteLanguages() {
        return favoriteLanguages;
    }

    public void setFavoriteLanguages(List<String> favoriteLanguages) {
        this.favoriteLanguages = favoriteLanguages;
    }






    public String getFavoriteLanguage() {
        return favoriteLanguage;
    }

    public void setFavoriteLanguage(String favoriteLanguage) {
        this.favoriteLanguage = favoriteLanguage;
    }



    private List<String> country2;

    public List<String> getCountry2() {
        return country2;
    }

    public void setCountry2(List<String> country2) {
        this.country2 = country2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

 /*   public StudentController(){

        country2 = new ArrayList<>();
        country2.add("Spain");
        country2.add("England");
    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
