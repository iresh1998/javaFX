package com.devstack.lms.controller;

import com.devstack.lms.db.DatabaseAccessCode;
import com.devstack.lms.entity.Student;
import com.devstack.lms.view.tm.StudentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StudentFormController {

    public TextField txtage;
    public TextField txtemail;
    public TextField txtname;
    public TextField txtaddress;


    public TableView<StudentTM> tblStudent;
    public TableColumn<StudentTM, String> colName;
    public TableColumn<StudentTM, String> colAddress;
    public TableColumn<StudentTM, Integer> colAge;
    public TableColumn<StudentTM, String> colEmail;
    public TableColumn<StudentTM, ButtonBar> colOption;
    public TextField txtSearch;
    public Button btnSave;
    public AnchorPane context;


    private String searchText = "";
    private Student selectedStudent = null;


    public void initialize() {

        colName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("bar"));

        loadAllStudents();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            loadAllStudents();
        });

    }

    private void loadAllStudents() {

        ObservableList<StudentTM> tmObservableList = FXCollections.observableArrayList();

        try {

            DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
            List<Student> allStudents =
                    databaseAccessCode.findAllStudents(searchText);

            for (Student s : allStudents) {

                ButtonBar bar = new ButtonBar();
                Button deleteButton = new Button("Delete");
                Button updateButton = new Button("Update");
                bar.getButtons().addAll(deleteButton, updateButton);

                StudentTM tm = new StudentTM(
                        s.getStudentId(),
                        s.getStudentName(),
                        s.getAddress(),
                        s.getEmail(),
                        s.getAge(),
                        bar
                );


                deleteButton.setOnAction(event -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to delete..?",
                            ButtonType.NO, ButtonType.YES);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get() == ButtonType.YES) {

                        try {

                            DatabaseAccessCode dbAccessCode = new DatabaseAccessCode();
                            boolean isDeleted = dbAccessCode.deleteStudent(tm.getStudentId());

                            if (isDeleted) {

                                new Alert(Alert.AlertType.INFORMATION, "Student has benn deleted...", ButtonType.CLOSE).show();
                                loadAllStudents();
                            } else {

                                new Alert(Alert.AlertType.INFORMATION, "Try Again...", ButtonType.CLOSE).show();
                            }

                        } catch (SQLException | ClassNotFoundException e) {

                            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE).show();
                        }

                    }

                });
                updateButton.setOnAction(event -> {

                    btnSave.setText("Update Student");
                    selectedStudent = s;

                    txtname.setText(s.getStudentName());
                    txtage.setText(String.valueOf(s.getAge()));
                    txtemail.setText(s.getEmail());
                    txtaddress.setText(s.getAddress());


                });

                tmObservableList.add(tm);
            }

            tblStudent.setItems(tmObservableList);


        } catch (SQLException | ClassNotFoundException e) {

            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE).show();
        }
    }

    public void saveStudentOnAction(ActionEvent actionEvent) {

        if (btnSave.getText().equalsIgnoreCase("Save Student")) {
            try {
                Student student = new Student(
                        UUID.randomUUID().toString(),
                        txtname.getText().trim(),
                        txtaddress.getText().trim(),
                        txtemail.getText().toLowerCase().trim(), Integer.parseInt(txtage.getText())
                );

                DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
                boolean isSaved = databaseAccessCode.saveStudent(student);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Student has been Saved...", ButtonType.CLOSE).show();
                    clearFields();
                    loadAllStudents();
                    btnSave.setText("Save Student");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try again...", ButtonType.CLOSE).show();
                }

            } catch (SQLException | ClassNotFoundException e) {

                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE).show();
            }
        } else {


            if (selectedStudent != null) {

                try {
                    Student student = new Student(
                            selectedStudent.getStudentId(),
                            txtname.getText().trim(),
                            txtaddress.getText().trim(),
                            txtemail.getText().toLowerCase().trim(),Integer.parseInt(txtage.getText())
                    );

                    DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
                    boolean isSaved = databaseAccessCode.updateStudent(student);
                    if (isSaved){
                        new Alert(Alert.AlertType.INFORMATION,"Student has been Modified...", ButtonType.CLOSE).show();
                        clearFields();
                        loadAllStudents();
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Try again...",ButtonType.CLOSE).show();
                    }

                }catch (SQLException | ClassNotFoundException e){

                    new Alert(Alert.AlertType.ERROR,e.getMessage(),ButtonType.CLOSE).show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"Please Select a Student..",ButtonType.CLOSE).show();
            }
        }


    }

    private void clearFields() {
        txtname.clear();
        txtaddress.clear();
        txtage.clear();
        txtemail.clear();


    } 



    public void btnNewStudentOnAction(ActionEvent actionEvent) {

        btnSave.setText("Save Student");
        clearFields();
        selectedStudent = null;
    }



    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {

        setUi("DashboardForm");
    }
    private void setUi(String location) throws IOException {
        URL resource = getClass().getResource("../view/"+location+".fxml");
        Stage stage =(Stage) context.getScene().getWindow();
        stage.centerOnScreen();
        stage.setScene(new Scene(FXMLLoader.load(resource)));
        stage.setTitle(location);
    }


}
