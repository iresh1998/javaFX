package com.devstack.lms.controller;

import com.devstack.lms.business.BoFactory;
import com.devstack.lms.business.custom.CourseBo;
//import com.devstack.lms.db.DatabaseAccessCode;
import com.devstack.lms.dto.CourseDto;
import com.devstack.lms.entity.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.UUID;

public class CourseFormController {
    public AnchorPane context;
    public TableColumn colCourseName;
    public TableColumn colCourseFee;
    public TableColumn colOption;
    public TableView tblCources;
    public Button btnSave;
    public TextField txtSearch;
    public TextField txtCourseFee;
    public TextField txtCourseName;

    private final CourseBo courseBo = BoFactory.getBo(BoFactory.BoType.COURSE);

    public void saveCourseOnAction(ActionEvent actionEvent) {
        try {
            CourseDto course = new CourseDto(
                    UUID.randomUUID().toString(),
                    txtCourseName.getText().trim(),
                    Double.parseDouble(txtCourseFee.getText().trim())
            );

//            DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
            boolean isSaved = courseBo.create(course);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Course has been Saved...", ButtonType.CLOSE).show();
                clearFields();
                btnSave.setText("Save Course");
            } else {
                new Alert(Alert.AlertType.WARNING, "Try again...", ButtonType.CLOSE).show();
            }

        } catch (SQLException | ClassNotFoundException e) {

            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE).show();
        }
    }

    private void clearFields() {
        txtCourseName.clear();
        txtCourseFee.clear();
    }

    public void btnNewCourseOnAction(ActionEvent actionEvent) throws IOException {


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
