/*
 * Copyright 2017 NKI/AvL
 *
 * This file is part of PALGAProtocolParser.
 *
 * PALGAProtocolParser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PALGAProtocolParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PALGAProtocolParser. If not, see <http://www.gnu.org/licenses/>
 */

package gui;

import gui.resourcemanagement.ResourceManager;
import codebook.CaptionOverwriter;
import codebook.Codebook;
import codebook.CodebookFactory;
import data.Protocol;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TextAreaAppender;

import java.util.List;

/**
 * Main window
 */
public class MainWindow {
    private static final Logger logger = LogManager.getLogger(MainWindow.class.getName());
    private static final ResourceManager resourceManager = new ResourceManager();

    private static final int sceneWidth = 800;
    private static final int sceneHeight = 500;
    private static final String DEFAULT_LOG_AREA_STRING = "Log area with feedback messages...";

    private TextArea logArea;
    private RunParameters runParameters = new RunParameters();

    /**
     * Create the main window. Adds all the components
     *
     * @param primaryStage the primary stage
     */
    public void createMainWindow(Stage primaryStage) {
        primaryStage.setTitle("Codebook Generator");

        // create the components
        Node topPane = setupTopPane();
        Node centerPane = setupCenterPane();
        Node bottomPane = setupBottomPane();

        // add them to a borderpane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topPane);
        borderPane.setCenter(centerPane);
        borderPane.setBottom(bottomPane);

        // create the scene, set the scene
        Scene scene = new Scene(borderPane, sceneWidth, sceneHeight);
        primaryStage.setScene(scene);

        // create an icon
        primaryStage.getIcons().add(resourceManager.getResourceImage("antonie_icon.png"));
        // add the stylesheet
        scene.getStylesheets().add(resourceManager.getResourceStyleSheet("style.css"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Create top pane
     *
     * @return the Node which will be added to the borderpane
     */
    private Node setupTopPane(){
        // create first image for the left side
        ImageView antonieImv = new ImageView();
        antonieImv.setFitHeight(110);
        antonieImv.setFitWidth(110);
        antonieImv.setImage(resourceManager.getResourceImage("antonie.png"));

        // create the title
        Label sceneTitle = new Label("Codebook Generator");
        sceneTitle.setId("title");
        sceneTitle.setPadding(new Insets(30,0,0,0));

        // create second image for the right side
        ImageView palgaImv = new ImageView();
        palgaImv.setFitHeight(100);
        palgaImv.setFitWidth(140);
        palgaImv.setImage(resourceManager.getResourceImage("palga.png"));

        // add all three to an hbox
        HBox hb = new HBox();
        hb.setPadding(new Insets(25,15,25,15));
        hb.setSpacing(75);
        hb.getChildren().addAll(antonieImv, sceneTitle, palgaImv);

        // give the hbox an id and add styleclass
        hb.setId("topPane");
        hb.getStyleClass().add("fillBackground");

        return hb;
    }

    /**
     * Create center pane which contains the log area
     *
     * @return the Node which will be added to the borderpane
     */
    private Node setupCenterPane(){
        // center pane currently contains only logarea, which we add to an hbox an return
        HBox hBox = new HBox();
        hBox.getStyleClass().add("fillBackground");
        createLogArea();
        hBox.getChildren().addAll(logArea);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /**
     * create the log area
     */
    private void createLogArea(){
        logArea = new TextArea();
        logArea.setPadding(new Insets(5, 5, 5, 5));
        logArea.setPrefWidth(sceneWidth);
        logArea.setEditable(false);
        logArea.setId("logArea");
        logArea.setText(DEFAULT_LOG_AREA_STRING);
        logArea.setWrapText(true);
        TextAreaAppender.setTextArea(logArea);
    }

    /**
     * Create the bottom pane, which contains buttons to e.g. run and exit the program
     *
     * @return the Node which will be added to the borderpane
     */
    private Node setupBottomPane(){
        HBox hBox = new HBox();
        hBox.getStyleClass().add("fillBackground");

        hBox.setPadding(new Insets(20, 12, 20, 12));
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.BASELINE_RIGHT);

        Button buttonClear = new Button("Clear");
        buttonClear.setPrefSize(100, 20);
        buttonClear.setOnAction(event -> logArea.setText(DEFAULT_LOG_AREA_STRING));

        // add some buttons and tell what to do when the button is clicked
        Button buttonRun = new Button("Run");
        buttonRun.setPrefSize(100, 20);
        buttonRun.setOnAction(event -> runConversion());

        Button buttonExit = new Button("Exit");
        buttonExit.setPrefSize(100, 20);
        buttonExit.setOnAction(event -> System.exit(0));

        // Give Help and About their own hbox, which we align to the right
        HBox rightBox = new HBox();
        rightBox.setAlignment(Pos.BASELINE_RIGHT);

        Hyperlink aboutHyperlink = new Hyperlink("About");
        aboutHyperlink.getStyleClass().add("hyperlink");
        aboutHyperlink.setOnAction(event -> logArea.setText(getAboutText()));

        Hyperlink helpHyperlink = new Hyperlink("Help");
        helpHyperlink.getStyleClass().add("hyperlink");
        helpHyperlink.setOnAction(event -> logArea.setText(getHelpText()));

        // add to boxes
        rightBox.getChildren().addAll(helpHyperlink, aboutHyperlink);
        hBox.getChildren().addAll(buttonClear, buttonRun, buttonExit, rightBox);

        // give the right button a margin to push it to the center of the page
        HBox.setMargin(buttonExit, new Insets(0,150,0,0));

        return hBox;
    }

    private String getAboutText(){
        return "This program was created by Sander de Ridder at the NKI in a collaboration between\n" +
               "NKI / AvL and PALGA.\n" +
               "Testers: Sander de Ridder (NKI), Rinus Voorham (PALGA) and Rick Spaan (PALGA)\n" +
               "-------------------------------------------------------------------------------------------------------------------\n\n"+
               "Copyright 2017 NKI / AvL\n" +
               "\n" +
               "PALGAProtocolParser is free software: you can redistribute it and/or modify\n" +
               "it under the terms of the GNU General Public License as published by\n" +
               "the Free Software Foundation, either version 3 of the License, or\n" +
               "(at your option) any later version.\n" +
               "\n" +
               "PALGAProtocolParser is distributed in the hope that it will be useful,\n" +
               "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
               "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
               "GNU General Public License for more details.\n" +
               "\n" +
               "You should have received a copy of the GNU General Public License\n" +
               "along with PALGAProtocolParser. If not, see <http://www.gnu.org/licenses/>\n";
    }

    /**
     * returns the help text after the Help link is clicked
     * @return the help text
     */
    private String getHelpText(){
        return "This program aims to parse the PALGA protocols.\n\n" +
                "Required:\n" +
                "* a file which contains the protocols (protocolname<tab>projectname as specified in the database on each line)\n" +
                "* a database file\n" +
                "* output directory, whence the output will be stored\n\n" +
                "Optional:\n" +
                "* a file which contains overwrites. This file MUST start with #<protocolname>. Each subsequent line must have: conceptname<TAB>description";
    }

    /**
     *
     * Called after the runbutton is clicked.
     * Starts a Thread to do the actual work.
     */
    private void runConversion(){
        logArea.clear();
        ProtocolWizard protocolWizard = new ProtocolWizard();
        try {
            if(protocolWizard.startWizard(runParameters)) {
                runParameters = protocolWizard.getRunParameters();
                new Thread(new PalgaTask()).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class PalgaTask extends Task{

        /**
         * creates the captionoverwriter, generates the codebook items, creates the codebook,
         * saves the codebook and write the conflicting captions to a file
         * @return null
         */
        @Override
        public Void call() {
            try {
                logger.log(Level.INFO, "Configuring CaptionOverwriter...");
                CaptionOverwriter captionOverwriter = new CaptionOverwriter(runParameters.getProtocolName());
                captionOverwriter.readCaptionOverwriteFile(runParameters.getOverwriteFileName());

                logger.log(Level.INFO, "Reading the protocol data from the database...");
                Protocol protocol = runParameters.getProtocol();
                protocol.generateCodebookItems();


                logger.log(Level.INFO, "Setting up the codebooks...");
                // give the runparameters and let the factory give the store in separate sheets to the constructor
                // this allows us to overwrite the write to separate sheets when the "PALGA & NKI" is selected
                // also means we have to update the interface to writeToExcel, for which we will set the default
                // to check the boolean value writeToSheets and then calls the abstract methods for the writing?
                List<Codebook> codebookList = CodebookFactory.createCodebook(runParameters.getCodebookType(), protocol, captionOverwriter, runParameters.getStoreOptionsInSeparateSheets());

                for(Codebook codebook:codebookList){
                    logger.log(Level.INFO, "Writing "+runParameters.getCodebookType()+" codebook(s)...");
                    codebook.writeToExcel(runParameters.getOutputDir());
                    logger.log(Level.INFO, "Done");
                }

                logger.log(Level.INFO, "Writing the conflicting captions...");
                captionOverwriter.writeConflictingCaptions(runParameters.getOutputDir());

                logger.log(Level.INFO, "Finished!");
            } catch (Exception e){
                logger.log(Level.INFO, "A fatal error occurred:\n"+e.getMessage());
            }
            return null;
        }
    }
}

