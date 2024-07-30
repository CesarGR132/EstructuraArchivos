package Repository;

public interface AdminRepository {
    //Student operations
    void deleteStudent();
    void addNewStudent();
    void updateStudentInformation(String name);
    void updateStudentGrade(String name);
    void updateStudentSemester(String name);
    void seeStudentDetails(String matricula);

    //Teacher operations
    void deleteTeacher();
    void addNewTeacher();
    void updateTeacherInformation();
    String[] seeTeacherInformation(String matricula);
    String generateMatricula(String name, String lastName, String registerYear, String key);
    void showTeacherDetails(String matricula);

    void showSubjectDetails(String subject);

    //Subject operations
    void addNewSubject();

}
