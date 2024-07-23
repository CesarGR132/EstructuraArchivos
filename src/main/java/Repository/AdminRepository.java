package Repository;

public interface AdminRepository {
    //Student operations
    void deleteStudent(String name);
    void addNewStudent();
    void updateStudentInformation(String name);
    void updateStudentGrade(String name);
    void updateStudentSemester(String name);

    //Teacher operations
    void deleteTeacher(String name);
    void addNewTeacher();
    void updateTeacherInformation(String name);
    String[] seeTeacherInformation(String matricula);

    //Admin operations
    void addNewAdmin();
    void updateAdminInformation(String name);
}
