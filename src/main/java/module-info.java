module org.renan.mundoanimalfrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens org.renan.mundoanimalfrontend to javafx.fxml;
    exports org.renan.mundoanimalfrontend;

    // Ajuste aqui para abrir o pacote model ao Hibernate
    opens org.renan.mundoanimalfrontend.model to org.hibernate.orm.core, jakarta.persistence;
    exports org.renan.mundoanimalfrontend.model;
}
