package com.example.naloga03uv;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public class HelloController implements Initializable {
    public ComboBox znamkaBox;
    public ComboBox gorivoBox;
    public ComboBox mocBox;
    public ComboBox prosotrninaBox;
    public ComboBox vrstaVozilaBox;
    public ComboBox stVratBox;
    public ComboBox stSedezevBox;
    public TextField imeInput;
    public TextField priimekInput;
    public TextField ulicaInput;
    public TextField hisnaStevilkaInput;
    public TextField krajInput;
    public ComboBox letoPrveRegistracije;
    public CheckBox zavPrakiriscaCheck;
    public RadioButton zavAOPlus;
    public RadioButton zavAO;
    public CheckBox zavarovanjeSteklCheck;
    public CheckBox zavPotCheck;
    public CheckBox zavTrejteOsebCheck;
    public CheckBox zavarovanjeGumCheck;
    public CheckBox zavKrajeCheck;
    public CheckBox zavTociCheck;
    public CheckBox zavSrnjadiCheck;
    public RadioButton kaskoPolno;
    public RadioButton kaskoOdbita;
    public RadioButton kaskoBrez;
    public ComboBox mesecRegistracijeBox;
    public ComboBox danRegistracijeBox;
    public TextField registerskaOznacbaInput;
    public TextField krajRegistracijeInput;
    public TextField tipInput;
    public ComboBox danBox;
    public ComboBox mesecBox;
    public ComboBox letoBox;
    public TextField postaInput;
    public TextField modelInput;
    public Label status;

    private int[] days;
    private String[] months = {"Januar", "Februar", "Marec", "April", "Maj", "Junij", "Julij", "Avgust", "September", "Oktober", "November", "December"};
    private int[] years;
    private String[] carBrands = {
            "Abarth",
            "Alfa Romeo",
            "Aston Martin",
            "Audi",
            "Bentley",
            "BMW",
            "Bugatti",
            "Cadillac",
            "Chevrolet",
            "Chrysler",
            "Citroën",
            "Dacia",
            "Daewoo",
            "Daihatsu",
            "Dodge",
            "Donkervoort",
            "DS",
            "Ferrari",
            "Fiat",
            "Fisker",
            "Ford",
            "Honda",
            "Hummer",
            "Hyundai",
            "Infiniti",
            "Iveco",
            "Jaguar",
            "Jeep",
            "Kia",
            "KTM",
            "Lada",
            "Lamborghini",
            "Lancia",
            "Land Rover",
            "Landwind",
            "Lexus",
            "Lotus",
            "Maserati",
            "Maybach",
            "Mazda",
            "McLaren",
            "Mercedes-Benz",
            "MG",
            "Mini",
            "Mitsubishi",
            "Morgan",
            "Nissan",
            "Opel",
            "Peugeot",
            "Porsche",
            "Renault",
            "Rolls-Royce",
            "Rover",
            "Saab",
            "Seat",
            "Skoda",
            "Smart",
            "SsangYong",
            "Subaru",
            "Suzuki",
            "Tesla",
            "Toyota",
            "Volkswagen",
            "Volvo"
    };
    private String gorivo[] = {"Disel", "Bencin", "Plin", "Hibridni pogon", "E-pogon"};
    private int[] prostornina = {1000, 1350, 1800, 2500, 3000, 4000};
    private int[] mocKw;
    private int[] mocKwBikes = {4, 8, 11, 14, 26, 35, 44, 72};
    private int[] tovornaVozilaKw = {44, 55, 66, 74, 85, 96, 110, 147, 184, 222, 298, 373};
    private String[] stVratCars = {"2/3", "4/5", "6/7"};
    public String[] stSedezev = {"2 sedeža", "3 - 5 sedežev", "6 ali več sedežev"};
    private String[] vrstaVozil = {"Osebni avto", "Tovornjak", "Traktor", "Avtobus", "Motor"};
    private boolean car;
    private boolean bike;
    private boolean drugo;
    private int[] yearsReg;

    //izpoljneni podatki
    private HashMap<String, String> valuseOfVozila = new HashMap<>();
    private HashMap<String, String> valuesOfZavarovanca = new HashMap<>();
    private HashMap<String, String> valuseOfZavarovanja = new HashMap<>();
    private HashMap<String, String> valuseOfRegistracije = new HashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vrstaVozilaBox.getItems().addAll(vrstaVozil);

        vrstaVozilaBox.setOnAction(event -> getVrstaVozila());

        // grupiram radio gumbe
        ToggleGroup radioButtonOsnovnoZav = new ToggleGroup();
        ToggleGroup radioButtonKasko = new ToggleGroup();
        zavAOPlus.setToggleGroup(radioButtonOsnovnoZav);
        zavAO.setToggleGroup(radioButtonOsnovnoZav);
        kaskoBrez.setToggleGroup(radioButtonKasko);
        kaskoOdbita.setToggleGroup(radioButtonKasko);
        kaskoPolno.setToggleGroup(radioButtonKasko);


        // napolnim
        inputDaysAndYears();
        inputYearsRegistracija();
        for (int i = 0; i < days.length; i++) {
            danBox.getItems().add(days[i]);
            danRegistracijeBox.getItems().add(days[i]);
        }

        mesecBox.getItems().addAll(months);
        mesecRegistracijeBox.getItems().addAll(months);

        for (int i = 0; i < years.length; i++) {
            letoBox.getItems().add(years[i]);
        }
        znamkaBox.getItems().addAll(carBrands);

        gorivoBox.getItems().addAll(gorivo);
        for (int i = 0; i < prostornina.length; i++) {
            prosotrninaBox.getItems().add(prostornina[i]);
        }
        stVratBox.getItems().addAll(stVratCars);
        stSedezevBox.getItems().addAll(stSedezev);

        for (int i = 0; i < yearsReg.length; i++) {
            letoPrveRegistracije.getItems().add(yearsReg[i]);
        }

    }


    public void getVrstaVozila() {
        String vrstaVozila = vrstaVozilaBox.getValue().toString();
        if (vrstaVozila.equals("Osebni avto")) {
            mocBox.getItems().removeAll(mocBox.getItems());
            for (int i = 0; i < mocKw.length; i++) {
                int kw = mocKw[i];
                int HP = (int) (mocKw[i] * 1.36);
                String addthis = kw + "kW (" + HP + " KM)";
                mocBox.getItems().add(addthis);
            }
        } else if (vrstaVozila.equals("Motor")) {
            mocBox.getItems().removeAll(mocBox.getItems());
            for (int i = 0; i < mocKwBikes.length; i++) {
                int kw = mocKwBikes[i];
                int HP = (int) (kw * 1.36);
                String addthis = kw + "kW (" + HP + " KM)";
                mocBox.getItems().add(addthis);
            }
        } else {
            mocBox.getItems().removeAll(mocBox.getItems());
            for (int i = 0; i < tovornaVozilaKw.length; i++) {
                int kw = tovornaVozilaKw[i];
                int HP = (int) (kw * 1.36);
                String addthis = kw + "kW (" + HP + " KM)";
                mocBox.getItems().add(addthis);
            }
        }
    }


    public void inputDaysAndYears() {
        days = new int[31];
        int j = 0;
        for (int i = 1; i < 32; i++) {
            days[j++] = i;
        }
        int start = 1934;
        years = new int[71];
        for (int i = 0; i < 71; i++) {
            years[i] = start++;
        }
        //moc kw for cars
        mocKw = new int[15];
        int startKw = 30;
        for (int i = 0; i < 15; i++) {
            mocKw[i] = startKw;
            startKw += 10;
        }
    }

    public void inputYearsRegistracija() {
        yearsReg = new int[52];
        int startYear = 1970;
        for (int i = 0; i < yearsReg.length; i++) {
            yearsReg[i] = startYear++;
        }
    }


    public void submitValues(ActionEvent actionEvent) {
        // preglej ce je vrsta vozila selektana
        getDataOfVozila();
        // poglej data od zavarovanca
        getDataOfZavarovanca();
        getDataOfZavarovanja();
        getDataOfRegitracije();
        if (!valuseOfRegistracije.isEmpty() && !valuseOfVozila.isEmpty() && !valuseOfZavarovanja.isEmpty() && !valuesOfZavarovanca.isEmpty()){
            Alert save = new Alert(Alert.AlertType.CONFIRMATION);
            save.setTitle("Shrani");
            save.setHeaderText("Ali želite shraniti podatke?");
            Optional<ButtonType> result = save.showAndWait();
            if (result.get() == ButtonType.OK){
                System.out.println("Shranjeno");
                //save data
                crateDataBaze();
            }else{
                System.out.println("Ni shranjeno");
            }
        }else {
            Alert izpolni = new Alert(Alert.AlertType.WARNING, "Prosim izpolni obrazec");
            izpolni.show();
        }
    }


    private void crateDataBaze(){
        try {
            File baza = new File("bazapodatkov.txt");
            if (baza.exists()){
                System.out.println("Obstaja");
                System.out.println();
                writeToDataBase(String.valueOf(baza.getAbsoluteFile()));
            }
            else if (baza.createNewFile()) {
                writeToDataBase(baza.getName());
            }
        } catch (IOException e) {
            Alert cantSaveToDataBaze = new Alert(Alert.AlertType.ERROR, "Shranjevanje v bazo podatkov je spodletelo!");
             cantSaveToDataBaze.show();
        }
    }

    private void writeToDataBase(String fileName){
        try{
            FileWriter writer = new FileWriter(fileName, true);
            //write
            writer.write(getDataToWrite().toString());
            //close
            writer.close();
        } catch (IOException e) {
            Alert cantWrite = new Alert(Alert.AlertType.ERROR, "Podatke nemorem vpisati!");
        }
    }

    private StringBuilder getDataToWrite(){
        StringBuilder data = new StringBuilder();

        // napisemo podatke o vozilu
        data.append("Podatki o vozilu\n");
        for (String i : valuseOfVozila.keySet()) {
            data.append(i+" "+valuseOfVozila.get(i)+"\n");
        }
        // podatki o zavarovancu
        data.append("Podatki o zavarovancu\n");
        for (String i : valuesOfZavarovanca.keySet()) {
            data.append(i+ " "+valuesOfZavarovanca.get(i)+"\n");
        }
        // podatki o zavarovanju
        data.append("Podatki o zavarovanju\n");
        for (String i : valuseOfZavarovanja.keySet()) {
            data.append(i+" "+valuseOfZavarovanja.get(i)+"\n");
        }
        // podatki o registraciji
        data.append("Podatki o registraciji\n");
        for (String i : valuseOfRegistracije.keySet()) {
            data.append(i+" "+valuseOfRegistracije.get(i)+"\n");
        }
        data.append("\n");
        return data;
    }



    public void deleteObrazec(ActionEvent actionEvent) {
        modelInput.setText("");
        tipInput.setText("");
        imeInput.setText("");
        priimekInput.setText("");
        ulicaInput.setText("");
        hisnaStevilkaInput.setText("");
        postaInput.setText("");
        krajInput.setText("");
        registerskaOznacbaInput.setText("");
        krajRegistracijeInput.setText("");

        //combobox restore

        //radio buttons
        zavAO.setSelected(false);
        zavAOPlus.setSelected(false);
        kaskoOdbita.setSelected(false);
        kaskoPolno.setSelected(false);
        kaskoBrez.setSelected(false);
        //checkbox
        zavTociCheck.setSelected(false);
        zavSrnjadiCheck.setSelected(false);
        zavTrejteOsebCheck.setSelected(false);
        zavarovanjeSteklCheck.setSelected(false);
        zavKrajeCheck.setSelected(false);
        zavarovanjeGumCheck.setSelected(false);
        zavPrakiriscaCheck.setSelected(false);
        zavPotCheck.setSelected(false);



    }


    private void getDataOfVozila() {
        //vsrta vozila
        String vrstaVozila = (String) vrstaVozilaBox.getValue();
        if (vrstaVozila != null) {
            valuseOfVozila.put("Vrsta vozila:", vrstaVozila);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi vrsto vozila!");
            a.show();
            //empty valuesOfVozila
            valuseOfVozila.clear();
            return;
        }
        // znamka
        String znamka = (String) znamkaBox.getValue();
        if (znamka != null) {
            valuseOfVozila.put("Znamka:", znamka);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi znamko vozila!");
            a.show();
            //empty valuesOfVozila
            valuseOfVozila.clear();
            return;
        }
        //model
        String model = modelInput.getText();
        if (!model.equals("")) {
            valuseOfVozila.put("Model:", model);

        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi model vozila!");
            a.show();
            //empty valuesOfVozila
            valuseOfVozila.clear();
            return;
        }
        //tip
        String tip = tipInput.getText();
        if (!tip.equals("")) {
            valuseOfVozila.put("Tip:", tip);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi tip vozila!");
            a.show();
            //empty valuesOfVozila
            valuseOfVozila.clear();
            return;
        }
        //moc
        String moc = (String) mocBox.getValue();
        if (moc != null) {
            valuseOfVozila.put("Moc:", moc);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi moc vozila!");
            a.show();
            //empty valuesOfVozila
            valuseOfVozila.clear();
            return;
        }
        //stvrat
        String stVrat = (String) stVratBox.getValue();
        if (stVrat != null) {
            valuseOfVozila.put("Stevilo vrat:", stVrat);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi stevilo vrat vozila!");
            a.show();
            //empty valuesOfVozila
            valuseOfVozila.clear();
            return;
        }
        //gorivo
        String gorivo = (String) gorivoBox.getValue();
        if (gorivo != null) {
            valuseOfVozila.put("Gorivo:", gorivo);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi gorivo vozila!");
            a.show();
            //empty valuesOfVozila
            valuseOfVozila.clear();
            return;
        }
        //prostornina
        String prostornina = String.valueOf(prosotrninaBox.getValue());
        if (prostornina != null) {
            valuseOfVozila.put("Prostornina motorja:", prostornina);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi prostornino motorja vozila!");
            a.show();
            //empty valuesOfVozila
            valuseOfVozila.clear();
            return;
        }
        //st sedezev
        String stSedezev = (String) stSedezevBox.getValue();
        if (stSedezev != null) {
            valuseOfVozila.put("Stevilo sedezev:", stSedezev);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi stevilo sedezev v vozilu!");
            a.show();
            //empty valuesOfVozila
            valuseOfVozila.clear();
        }
    }

    private void getDataOfZavarovanca() {
        //ime
        String ime = imeInput.getText();
        System.out.println("Ime:" + ime);
        if (!ime.equals("")) {
            valuesOfZavarovanca.put("Ime:", ime);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim vnesi ime!");
            a.show();
            //emtyp podatke zavarovnca
            valuesOfZavarovanca.clear();
            return;
        }
        //priimek
        String priimek = priimekInput.getText();
        if (!priimek.equals("")) {
            valuesOfZavarovanca.put("Priimek:", priimek);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim vnesi priimek!");
            a.show();
            //emtyp podatke zavarovnca
            valuesOfZavarovanca.clear();
            return;
        }
        //ulica
        String ulica = ulicaInput.getText();
        if (!ulica.equals("")) {
            valuesOfZavarovanca.put("Ulica:", ulica);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim vnesi ulico!");
            a.show();
            //emtyp podatke zavarovnca
            valuesOfZavarovanca.clear();
            return;
        }
        //hisna st
        String hisnaStevilka = hisnaStevilkaInput.getText();
        if (!hisnaStevilka.equals("")) {
            valuesOfZavarovanca.put("Hisna stevilka:", hisnaStevilka);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim vnesi hisno stevilko!");
            a.show();
            //emtyp podatke zavarovnca
            valuesOfZavarovanca.clear();
            return;
        }
        //posta
        String posta = postaInput.getText();
        if (!posta.equals("")) {
            if (checkPostaValid(posta)){
                valuesOfZavarovanca.put("Posta:", posta);
            }else{
                Alert a = new Alert(Alert.AlertType.ERROR, "Prosim vpiši veljavno poštno številko!");
                a.show();
                valuesOfZavarovanca.clear();
                return;
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim vnesi posto!");
            a.show();
            //emtyp podatke zavarovnca
            valuesOfZavarovanca.clear();
            return;
        }
        //kraj
        String kraj = krajInput.getText();
        if (!kraj.equals("")) {
            valuesOfZavarovanca.put("Kraj:", kraj);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim vnesi kraj!");
            a.show();
            //emtyp podatke zavarovnca
            valuesOfZavarovanca.clear();
            return;
        }
        //danrojstva
        //mesec rojstva
        //leto rojstva
        String danRojstva = String.valueOf(danBox.getValue());
        String mesecRojsva = (String) mesecBox.getValue();
        String letoRojstva = String.valueOf(letoBox.getValue());
        if (danRojstva != null && mesecRojsva != null && letoRojstva != null) {
            //check for true date
            if (checkValidDate(danRojstva, mesecRojsva, letoRojstva)) {
                valuesOfZavarovanca.put("Datum rojstva:", danRojstva + "/" + mesecRojsva + "/" + letoRojstva);
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi pravilen datum!");
                a.show();
                //emtyp data
                valuesOfZavarovanca.clear();

            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim vnesi datum rojstva!");
            a.show();
            //emtyp podatke zavarovnca
            valuesOfZavarovanca.clear();

        }

    }

    private void getDataOfZavarovanja() {
        //osnovno zavarovanje
        if (zavAO.isSelected()) {
            valuseOfZavarovanja.put("Osnovno zavarovanje:", "AO");
        } else if (zavAOPlus.isSelected()) {
            valuseOfZavarovanja.put("Osnovno zavarovanje:", "AO+");
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi osnovno zavarovnaje!");
            a.show();
            //emtyp podatke zavarovnca
            valuseOfZavarovanja.clear();
            return;
        }

        //kasko
        if (kaskoBrez.isSelected()) {
            valuseOfZavarovanja.put("Kasko zavarovanje:", "Brez");
        } else if (kaskoPolno.isSelected()) {
            valuseOfZavarovanja.put("Kasko zavarovanje:", "Polno");
        } else if (kaskoOdbita.isSelected()) {
            valuseOfZavarovanja.put("Kasko zavarovanje:", "ODbita fransiza");
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi kasko zavarovnaje!");
            a.show();
            //emtyp podatke zavarovnca
            valuseOfZavarovanja.clear();
            return;
        }
        //dodatno zavarovanje
        if (zavTociCheck.isSelected()) {
            valuseOfZavarovanja.put("Zavarovanje za točo:", "Da");
        } else {
            valuseOfZavarovanja.put("Zavarovanje za točo:", "Ne");
        }
        if (zavSrnjadiCheck.isSelected()) {
            valuseOfZavarovanja.put("Zavarovanje za srnjad:", "Da");
        } else {
            valuseOfZavarovanja.put("Zavarovanje za srnjad:", "Ne");
        }
        if (zavTrejteOsebCheck.isSelected()) {
            valuseOfZavarovanja.put("Zavarovanje za tretjo osebo:", "Da");
        } else {
            valuseOfZavarovanja.put("Zavarovanje za tretjo osebo:", "Ne");
        }
        if (zavarovanjeSteklCheck.isSelected()) {
            valuseOfZavarovanja.put("Zavarovanje za stekla:", "Da");
        } else {
            valuseOfZavarovanja.put("Zavarovanje za stekla:", "Ne");
        }
        if (zavKrajeCheck.isSelected()) {
            valuseOfZavarovanja.put("Zavarovanje za krajo:", "Da");
        } else {
            valuseOfZavarovanja.put("Zavarovanje za krajo:", "Ne");
        }
        if (zavarovanjeGumCheck.isSelected()) {
            valuseOfZavarovanja.put("Zavarovanje za gume:", "Da");
        } else {
            valuseOfZavarovanja.put("Zavarovanje za gume:", "Ne");
        }
        if (zavPrakiriscaCheck.isSelected()) {
            valuseOfZavarovanja.put("Zavarovanje za parkirišče:", "Da");
        } else {
            valuseOfZavarovanja.put("Zavarovanje za parkirišče:", "Ne");
        }
        if (zavPotCheck.isSelected()) {
            valuseOfZavarovanja.put("Zavarovanje za potnike:", "Da");
        } else {
            valuseOfZavarovanja.put("Zavarovanje za potnike:", "Ne");
        }

    }

    private void getDataOfRegitracije() {
        //dan, mesec, leto
        String danRegistracije = String.valueOf(danRegistracijeBox.getValue());
        String mesecRegistracije = (String) mesecRegistracijeBox.getValue();
        String letoRegistracije = String.valueOf(letoPrveRegistracije.getValue());
        if (danRegistracije != null && mesecRegistracije != null && letoRegistracije != null) {
            if (checkValidDate(danRegistracije, mesecRegistracije, letoRegistracije)) {
                String datum = danRegistracije + "/" + mesecRegistracije + "/" + letoRegistracije;
                valuseOfRegistracije.put("Datum registracije:", datum);
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi pravilen datum!");
                a.show();
                //em[ty data
                valuseOfRegistracije.clear();
                return;
            }

        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim izberi datum registracije!");
            a.show();
            //emtyp podatke zavarovnca
            valuseOfRegistracije.clear();
            return;
        }
        //reg oznacba
        String registracija = registerskaOznacbaInput.getText();
        if (!registracija.equals("")) {
            if (checkValidRegisterska(registracija)){
                valuseOfRegistracije.put("Registerska oznacba:", registracija);
            }else{
                Alert a = new Alert(Alert.AlertType.WARNING, "Prosim vpisi pravilno registracijisko oznacbo!");
                a.show();
                // empty data
                valuseOfRegistracije.clear();
                return;
            }

        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim vpisi registracijisko oznacbo!");
            a.show();
            //emtyp podatke zavarovnca
            valuseOfRegistracije.clear();
            return;
        }
        //kraj reg
        String krajRegistracije = krajRegistracijeInput.getText();
        if (!krajRegistracije.equals("")) {
            valuseOfRegistracije.put("Kraj registracije:", krajRegistracije);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Prosim vpisi kraj registracije!");
            a.show();
            //emtyp podatke zavarovnca
            valuseOfRegistracije.clear();
        }
    }


    private boolean checkValidDate (String day, String month, String year){
        String dateValid = getMonthNumber(month) + "/" + day + "/" + year;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateValid);
            return true;
        } catch (ParseException e) {
            return false;
        }

    }

    private String getMonthNumber (String monthName){
        int month = 0;
        for (int i = 0; i < months.length; i++) {
            if (monthName.equals(months[i])) {
                month = i;
                month++; //povecam ker se indeks zacne z 0
            }
        }
        String returnMesec;
        if (month > 10) {
            returnMesec = "0" + Integer.toString(month);
        } else {
            returnMesec = Integer.toString(month);
        }
        return returnMesec;
    }

    private boolean checkValidRegisterska(String numberPlate){
        return Pattern.matches("[a-zA-Z0-9]{7}", numberPlate);
    }

    private boolean checkPostaValid(String postaNumber){
        return Pattern.matches("[0-9]{4}", postaNumber);
    }


    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void clearObrazec(ActionEvent actionEvent) {
        deleteObrazec(actionEvent);
    }

    public void pomoc(ActionEvent actionEvent) {
        Alert help = new Alert(Alert.AlertType.INFORMATION);
        help.setTitle("Pomoč uporabniku!");
        help.setContentText("Aplikacija shranjuje podatke v bazo, ob nepravilnem vpisu vas aplikacija to opomni z dialogom!");
        help.show();
    }
}