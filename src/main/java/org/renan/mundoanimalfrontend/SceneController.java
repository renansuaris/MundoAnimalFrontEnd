package org.renan.mundoanimalfrontend;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.renan.mundoanimalfrontend.model.UserPet;


public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtLoginPageCpf;

    @FXML
    private PasswordField txtLoginPagePassword;

    @FXML
    private void loginUser(ActionEvent event) {
        String cpf = txtLoginPageCpf.getText();
        String password = txtLoginPagePassword.getText();

        if (cpf.isEmpty() || password.isEmpty()) {
            showAlertForLogin("Erro", "Por favor, preencha todos os campos.", Alert.AlertType.ERROR);
            return;
        }

        // Validação no banco de dados
        EntityManager em = JPAutil.getEntityManager();
        try {
            // Consultar o usuário pelo CPF
            TypedQuery<UserPet> query = em.createQuery("SELECT u FROM UserPet u WHERE u.cpf = :cpf AND u.password = :password", UserPet.class);
            query.setParameter("cpf", cpf);
            query.setParameter("password", password);
            UserPet userPet = query.getSingleResult();

            if (userPet != null) {
                // Sucesso no login
                showAlertForLogin("Sucesso", "Login realizado com sucesso!", Alert.AlertType.INFORMATION);

                // Redireciona para a página do Dashboard
                goToDashboard(event);  // Adiciona esta linha para navegar para o dashboard

            }
        } catch (Exception e) {
            // Caso o usuário não seja encontrado ou ocorra outro erro
            showAlertForLogin("Erro", "CPF ou senha incorretos.", Alert.AlertType.ERROR);
        } finally {
            em.close();
        }
    }

    public void registerUser(ActionEvent event) {
        String name = txtName.getText();
        String cpf = txtCpf.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String password = txtPassword.getText();

        if (name.isEmpty() || cpf.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            // Exibe um alerta de erro se algum campo estiver vazio
            showAlert(Alert.AlertType.WARNING, "Campos Incompletos", "Preencha todos os campos!");
            return;
        }

        UserPet userPet = new UserPet();
        userPet.setName(name);
        userPet.setCpf(cpf);
        userPet.setEmail(email);
        userPet.setPhoneNumber(phone);
        userPet.setPassword(password);

        EntityManager em = JPAutil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(userPet);
            em.getTransaction().commit();

            // Exibe um alerta de sucesso
            showAlert(Alert.AlertType.INFORMATION, "Cadastro Realizado", "Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            // Exibe um alerta de erro se algo der errado
            showAlert(Alert.AlertType.ERROR, "Erro no Cadastro", "Erro ao salvar o usuário: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Método para mostrar alertas
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlertForLogin(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void goToLogin(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToRecover(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("recover-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToRegister(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("register-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToDashboard(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("dashboard-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}