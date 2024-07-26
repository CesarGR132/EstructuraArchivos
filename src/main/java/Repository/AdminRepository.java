package Repository;

public interface AdminRepository {
    //Student operations
    void deleteStudent(String name);
    void addNewStudent();
    void updateStudentInformation(String name);
    void updateStudentGrade(String name);
    void updateStudentSemester(String name);
    void seeStudentDetails(String matricula);

    //Teacher operations
    void deleteTeacher(String name);
    void addNewTeacher();
    void updateTeacherInformation(String name);
    String[] seeTeacherInformation(String matricula);
    String generateMatricula(String name, String lastName, String registerYear, String key);
    void showTeacherDetails(String matricula);

    void showSubjectDetails(String subject);

    //Admin operations
    void addNewAdmin();
    void updateAdminInformation(String name);
}
