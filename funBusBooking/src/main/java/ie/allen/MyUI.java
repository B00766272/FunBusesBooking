package ie.allen;

import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Connection connection = null;
    
        String connectionString = "jdbc:sqlserver://myclassdb.database.windows.net:1433;" + 
			  "database={myclassdb};" + 
			  "user=allen@myclassdb;" + 
			  "password=@llenV1974;" + 
			  "encrypt=true;" + 
			  "trustServerCertificate=false;" + 
			  "hostNameInCertificate=*.database.windows.net;" +
              "loginTimeout=30;";
        //content of Cell1          
         Label logo = new Label("<H1>Fun Bus Booking</H1> <p/> <h3>Please enter the details below and click Book</h3>", ContentMode.HTML);

         final VerticalLayout layout = new VerticalLayout();//masterLayout
         HorizontalLayout cell1 = new HorizontalLayout();
         HorizontalLayout cell2 = new HorizontalLayout();
         HorizontalLayout gridlayout = new HorizontalLayout();
         gridlayout.setSizeFull();
         VerticalLayout v = new VerticalLayout();
         VerticalLayout v1= new VerticalLayout();

         //content of cell2
         final TextField nameOfParty = new TextField();
         nameOfParty.setCaption("Name of Party");
      

         Slider s = new Slider("How many people are attending to this party?", 1, 300);
         s.setValue(300.0);
         s.setWidth("500px");
         

        ComboBox<String> accessible = new ComboBox<String>("accessibility needs?");
        accessible.setItems("yes", "no");

        //vertical layout content

        Button bookButton = new Button("Book");

        Label message = new Label("Your group is not booked yet", ContentMode.HTML);
        Label footer = new Label ("B00766272");

        try 
        {
        // Connect with JDBC driver to a database
        connection = DriverManager.getConnection(connectionString);
        // Add a label to the web app with the message and name of the database we connected to 
        //to check the database run the layout below
         //layout.addComponent(new Label("Connected to database: " + connection.getCatalog()));
        //query below - as loop:
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM PartyRooms;");
        // Convert the resultset that comes back into a List - we need a Java class to represent the data 
        List<FunBus> buses = new ArrayList<FunBus>();
        // While there are more records in the resultset
        while(rs.next())
        {   
            // Add a new Customer instantiated with the fields from the record (that we want, we might not want all the fields, note how I skip the id)
            buses.add(new FunBus(rs.getString("busDestination"), 
                        rs.getInt("capacity"), 
                        rs.getString("feature"),
                        rs.getString("accessibility")));
    
        }
            // Add my component, grid is templated with Customer
            Grid<FunBus> myGrid = new Grid<FunBus>();
            // Set the items (List)
             myGrid.setItems(buses);
             myGrid.setSizeFull();
            // Configure the order and the caption of the grid
             myGrid.addColumn(FunBus::getBusDestination).setCaption("Destination");
             myGrid.addColumn(FunBus::getCapacity).setCaption("Capacity");
             myGrid.addColumn(FunBus::getFeature).setCaption("Feature");
             myGrid.addColumn(FunBus::getAccessiblity).setCaption("Accessible");
             myGrid.setSelectionMode(SelectionMode.MULTI);
            
             // Add the grid to the layout
             gridlayout.addComponent(myGrid);//horizontal grid layout 

             MultiSelect<FunBus> select = myGrid.asMultiSelect();
             myGrid.addSelectionListener(event -> {

             Notification.show(select.getValue().stream().map(FunBus::getBusDestination).collect(Collectors.joining(","))
                    + " were selected");

        });

        bookButton.addClickListener(e -> {

            String compare = select.getValue().stream().map(FunBus::getAccessiblity).collect(Collectors.joining(","));
            int cap = select.getValue().stream().mapToInt(FunBus::getCapacity).sum();
            message.setValue(String.valueOf(cap));
            String match = "true";

                if(myGrid.getSelectedItems().size() == 0){
                    message.setValue("<strong>Please select at least one bus!</strong>");
                    return;
                }
        
                   if (nameOfParty.getValue().length()==0){
                       message.setValue("<strong>Please enter group name.</strong>");
                    return;
             
                       }
                       if(!accessible.getSelectedItem().isPresent()){
                        message.setValue("<strong>Please confirm if you need an accessible bus</strong");
                        return;   
                       }
                         //
                       if ((accessible.getValue() == "Yes") && (compare.equalsIgnoreCase(match))) {
                        message.setValue(
                                "<strong>You cannot select a non-accessible bus.</strong>");
                       }


                       if (s.getValue().intValue() > cap) {
                        message.setValue("<strong>You have selected Bus with a max capacity of " + cap
                                + " which is not enough to hold </strong>" + s.getValue().intValue());

                       }

                     else {
                        message.setValue("<h3>Success! The group is booked now</h3>");
                    }


                s.addValueChangeListener(event -> {
                int value = event.getValue().intValue();
                message.setValue(String.valueOf(value));
                });
                  
                        }); 
        
        
       }
       catch (Exception e) 
       {
        // This will show an error message if something went wrong
       gridlayout.addComponent(new Label(e.getMessage()));
       }


       cell1.addComponent(logo);
       cell2.addComponents( nameOfParty,s,accessible);
       v1.addComponent(message);    
       v.addComponents( cell1,cell2,bookButton,v1);
       layout.addComponents(v,gridlayout,footer);  //masterlayout



       setContent(layout);

    
                  
        
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
