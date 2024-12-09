package hu.petrik.lotto;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


public class HelloController {

    @FXML
    private Label sorsoltSzamok;
    @FXML
    private Label soroloSzam;
    @FXML
    private Button sorsolButton;

    private int sorsoltSzam;
    private final List<Integer> szamok = new ArrayList<>();

    private static final Random RANDOM = new Random();


    @FXML
    public void sorsolasClick() {

        if (sorsolButton.getText().equals("Rendez")) {
            //sorsol();
            rendez();

        }
        else {
            sorsol();
        }

    }

    private void sorsol(){
        Timer myTimer = new Timer();
        LocalDateTime inditas = LocalDateTime.now();
        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int szam = RANDOM.nextInt(1,91);
                Platform.runLater(() -> {
                    soroloSzam.setText(String.valueOf(szam));
                });
                Duration eltelt_ido = Duration.between(inditas, LocalDateTime.now());
                if (eltelt_ido.get(ChronoUnit.SECONDS)>=2) {
                    myTimer.cancel();
                    int sorsoltSzam = RANDOM.nextInt(1, 91);
               ;
                    System.out.println(szam);
                    System.out.println(sorsoltSzam);
                    while (szamok.contains(szam)) {
                        sorsoltSzam = RANDOM.nextInt(1, 91);
                    }

                    szamok.add(szam);
                    if (szamok.size() == 5) {
                        Platform.runLater(() -> {
                            sorsolButton.setText("Rendez");
                        });
                    }

                    Platform.runLater(() -> {
                        soroloSzam.setText(String.valueOf(szam));
                    });
                    Platform.runLater(() -> renderList());
                }

            }
        },0,50);
    }
    private void renderList(){
       String ff = szamok.stream().map(String::valueOf).collect(Collectors.joining(" "));
         sorsoltSzamok.setText(ff);
    }
    private  void rendez(){
        Collections.sort(szamok);
        renderList();
        szamok.clear();
        sorsolButton.setText("Sorsol");
    }
}