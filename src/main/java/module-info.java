module ph.edu.dlsu.lbycpds.socialnetwithgraph {
    requires javafx.controls;
    requires javafx.fxml;


    opens ph.edu.dlsu.lbycpds.socialnetwithgraph to javafx.fxml;
    exports ph.edu.dlsu.lbycpds.socialnetwithgraph;
}